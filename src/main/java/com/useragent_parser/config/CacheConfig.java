package com.useragent_parser.config;

import com.useragent_parser.model.UserAgentInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class CacheConfig {

    @Bean
    public ReactiveRedisTemplate<String, UserAgentInfo> reactiveRedisTemplate(RedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<UserAgentInfo> serializer = new Jackson2JsonRedisSerializer<>(UserAgentInfo.class);
        RedisSerializationContext<String, UserAgentInfo> context = RedisSerializationContext
                .<String, UserAgentInfo>newSerializationContext(serializer)
                .key(StringRedisSerializer.UTF_8)
                .build();

        return new ReactiveRedisTemplate<>((ReactiveRedisConnectionFactory) connectionFactory, context);
    }
}
