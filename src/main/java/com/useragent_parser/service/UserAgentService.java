package com.useragent_parser.service;

import com.useragent_parser.exception.UserAgentParsingException;
import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.util.UserAgentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;


@Service
public class UserAgentService {

    private static final String KEY = "userAgents";              // Redis key used for storing user agent information
    private static final Duration TTL = Duration.ofDays(7);      // Time-to-live (TTL) for cache entries in Redis

    private final ReactiveHashOperations<String, String, UserAgentInfo> hashOperations;
    private final ReactiveRedisTemplate<String, UserAgentInfo> redisTemplate;
    private final UserAgentParser userAgentParser;

    @Autowired
    public UserAgentService(ReactiveRedisTemplate<String, UserAgentInfo> redisTemplate,
                            UserAgentParser userAgentParser) {
        this.hashOperations = redisTemplate.opsForHash();   // Initialize hash operations for Redis
        this.redisTemplate = redisTemplate;                 // Set the Redis template
        this.userAgentParser = userAgentParser;             // Set the user agent parser
    }

    private Mono<UserAgentInfo> updateRedisCache(UserAgentInfo userAgentInfo) {
        String normalizedUserAgent = normalizeUserAgent(userAgentInfo.getUserAgent());     // Normalize
        userAgentInfo.setUserAgent(normalizedUserAgent);                                   // Set normalized value
        return hashOperations.put(KEY, hashUserAgent(normalizedUserAgent), userAgentInfo)  // Save with normalized hash
                .then(redisTemplate.expire(KEY, TTL))                                      // Setting TTL
                .thenReturn(userAgentInfo);
    }

    private String hashUserAgent(String userAgent) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(userAgent.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing User-Agent string", e);
        }
    }
    private String normalizeUserAgent(String userAgent) {
        if (userAgent == null) {
            return "";
        }
        return userAgent.trim().replaceAll("\\s+", " ");
    }

    public Mono<UserAgentInfo> parseUserAgent(String userAgent) {
        String hashedUserAgent = hashUserAgent(userAgent);   // Hash User-Agent

        // Validating Redis using a hashed key
        return hashOperations.get(KEY, hashedUserAgent)
                .switchIfEmpty(
                        //  If not found in the cache, parse User-Agent
                        userAgentParser.parseUserAgent(userAgent)
                                .flatMap(parsedInfo -> {
                                    parsedInfo.setUserAgent(normalizeUserAgent(userAgent)); // Save the original User-Agent in the object
                                    return updateRedisCache(parsedInfo);                    // Save with hashed key
                                })
                                .onErrorResume(e -> Mono.error(new UserAgentParsingException(
                                        "UserAgent parsing error for UserAgent:: " + userAgent, e)))
                );
    }


    // Method for model processing using Client Hints
    public Mono<UserAgentInfo> updateUserAgentInfoWithClientHints(UserAgentInfo userAgentInfo, HttpHeaders headers) {
        // Check for headers and update only if they are present

        // Platform
        String uaPlatform = headers.getFirst("Sec-CH-UA-Platform");
        if (uaPlatform != null) {
            if (userAgentInfo.getOs() == null) {
                userAgentInfo.setOs(new UserAgentInfo.Os());
            }
            userAgentInfo.getOs().setName(removeEscapedQuotes(uaPlatform));  // Updating the platform
        }

        // Platform Version
        String uaPlatformVersion = headers.getFirst("Sec-CH-UA-Platform-Version");
        if (uaPlatformVersion != null) {
            if (userAgentInfo.getOs() == null) {
                userAgentInfo.setOs(new UserAgentInfo.Os());
            }
            userAgentInfo.getOs().setVersion(removeEscapedQuotes(uaPlatformVersion));

            String majorVersion = userAgentParser.getMajorVersion(uaPlatformVersion);
            userAgentInfo.getOs().setName(majorVersion);
        }

        // Architecture
        String uaArch = headers.getFirst("Sec-CH-UA-Arch");
        if (uaArch != null) {
            if (userAgentInfo.getCpu() == null) {
                userAgentInfo.setCpu(new UserAgentInfo.Cpu());
            }
            userAgentInfo.getCpu().setArchitecture(removeEscapedQuotes(uaArch));  // Updating the architecture
        }

        // Mobile
        String uaMobile = headers.getFirst("Sec-CH-UA-Mobile");
        if (uaMobile != null) {
            if (userAgentInfo.getDevice() == null) {
                userAgentInfo.setDevice(new UserAgentInfo.Device());
            }
            userAgentInfo.getDevice().setType(uaMobile.equals("?1") ? "mobile" : "desktop");  // Updating the device type
        }

        // Model
        String uaModel = headers.getFirst("Sec-CH-UA-Model");
        if (uaModel != null) {
            if (userAgentInfo.getDevice() == null) {
                userAgentInfo.setDevice(new UserAgentInfo.Device());
            }
            userAgentInfo.getDevice().setModel(removeEscapedQuotes(uaModel));  // Update the device model
        }


        // Return an updated model if at least one header was found and changed
        return Mono.just(userAgentInfo);
    }

    private String removeEscapedQuotes(String value) {
        if (value != null) {
            return value.replaceAll("^\"|\"$", "");
        }
        return value;
    }

}