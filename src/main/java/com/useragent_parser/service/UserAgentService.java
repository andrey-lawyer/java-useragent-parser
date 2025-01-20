package com.useragent_parser.service;


import com.useragent_parser.exception.UserAgentParsingException;
import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.util.UserAgentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserAgentService {

    private static final String KEY = "userAgents";              // Redis key used for storing user agent information
    private static final Duration TTL = Duration.ofMinutes(30);  // Time-to-live (TTL) for cache entries in Redis

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
        return hashOperations.put(KEY, userAgentInfo.getUserAgent(), userAgentInfo)  // Put the user agent data in Redis Hash
                .then(redisTemplate.expire(KEY, TTL))                                // Set the TTL (Time-to-Live) for the cache key
                .thenReturn(userAgentInfo);                                          // Return the user agent info after updating the cache
    }

    public Mono<UserAgentInfo> parseUserAgent(String userAgent) {
        // Check Redis cache first
        return hashOperations.get(KEY, userAgent)
                .switchIfEmpty(
                        // If not found, parse the user agent and store in Redis
                        userAgentParser.parseUserAgent(userAgent)
                                .flatMap(this::updateRedisCache) // Save the parsed result to Redis
                                .onErrorResume(e -> Mono.error(new UserAgentParsingException("UserAgent parsing error for UserAgent:: " + userAgent, e))) // Handle parsing error
                );
    }
}
