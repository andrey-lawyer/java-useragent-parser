package com.useragent_parser.service;

import com.useragent_parser.exception.UserAgentParsingException;
import com.useragent_parser.model.UserAgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.useragent_parser.repositoty.UserAgentRepository;
import com.useragent_parser.util.UserAgentParser;

import java.time.Duration;



@Service
public class UserAgentService {

    private static final String KEY = "userAgents";              // Redis key used for storing user agent information
    private static final Duration TTL = Duration.ofMinutes(30);  // Time-to-live (TTL) for cache entries in Redis

    // Declare Redis operations for Hash (store user agent data by user agent string)
    private final ReactiveHashOperations<String, String, UserAgentInfo> hashOperations;

    // Declare the Redis template to interact with Redis
    private final ReactiveRedisTemplate<String, UserAgentInfo> redisTemplate;

    // Declare repository to access the user agent data from the database
    private final UserAgentRepository userAgentRepository;

    // Declare a parser to parse user agent strings into UserAgentInfo
    private final UserAgentParser userAgentParser;

    // Constructor to inject dependencies via Spring
    @Autowired
    public UserAgentService(ReactiveRedisTemplate<String, UserAgentInfo> redisTemplate,
                            UserAgentRepository userAgentRepository,
                            UserAgentParser userAgentParser) {
        this.hashOperations = redisTemplate.opsForHash();   // Initialize hash operations for Redis
        this.redisTemplate = redisTemplate;                 // Set the Redis template
        this.userAgentRepository = userAgentRepository;     // Set the user agent repository
        this.userAgentParser = userAgentParser;             // Set the user agent parser
    }

    // Update the Redis cache with new or updated user agent information
    private Mono<UserAgentInfo> updateRedisCache(UserAgentInfo userAgentInfo) {
        return hashOperations.put(KEY, userAgentInfo.getUserAgent(), userAgentInfo)  // Put the user agent data in Redis Hash
                .then(redisTemplate.expire(KEY, TTL))                                // Set the TTL (Time-to-Live) for the cache key
                .thenReturn(userAgentInfo);                                          // Return the user agent info after updating the cache
    }


    public Mono<UserAgentInfo> parseUserAgent(String userAgent, boolean cacheResult) {
        // If caching is not needed, try fetching the user agent from the repository
        if (!cacheResult) {
            return userAgentRepository.findByUserAgent(userAgent)
                    .switchIfEmpty(Mono.defer(() ->
                            userAgentParser.parseUserAgent(userAgent) // Parse the UserAgent string
                                    .flatMap(userAgentRepository::save)   // Save the parsed result to the repository
                                    .onErrorResume(e -> Mono.error(new UserAgentParsingException("UserAgent parsing error for UserAgent:: " + userAgent, e))) // Handle parsing error
                    ));
        }

        // If caching is required, first try to get the result from Redis cache
        return hashOperations.get(KEY, userAgent)
                .switchIfEmpty(
                        // If not found in cache, search in the repository
                        userAgentRepository.findByUserAgent(userAgent)
                                .switchIfEmpty(
                                        // If not found in the repository, parse and save it
                                        userAgentParser.parseUserAgent(userAgent)
                                                .flatMap(userAgentRepository::save) // Save the parsed result to the repository
                                                .onErrorResume(e -> Mono.error(new UserAgentParsingException("UserAgent parsing error for UserAgent:: " + userAgent, e))) // Handle parsing error
                                )
                                .flatMap(this::updateRedisCache) // Update Redis cache with the new result
                                .onErrorResume(e -> Mono.error(new UserAgentParsingException("Error working with repository or cache for UserAgent: " + userAgent, e))) // Handle error while working with repository or cache
                );
    }
}