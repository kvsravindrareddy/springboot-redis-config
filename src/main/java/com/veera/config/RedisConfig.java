package com.veera.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisAppConfig redisAppConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean("redisTemplateStandard")
    public RedisTemplate<Object, Object> redisTemplateStandard() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisStandaloneConnectionFactory());
        return template;
    }

    //<TODO - Change the factory based on the sentinel, cluster or standalone>
    @Bean
    public RedisConnectionFactory redisStandaloneConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisAppConfig.getHost());
        redisStandaloneConfiguration.setPort(redisAppConfig.getPort());
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return connectionFactory;
    }

    @Bean("redisTemplateJson")
    public RedisTemplate<String, Object> redisTemplateJson(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Set key serializer to StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());

        // Set value serializer to Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
       // jsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jsonRedisSerializer);

        // Configure other settings if needed
        // template.setHashKeySerializer(new StringRedisSerializer());
        // template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        template.afterPropertiesSet();
        return template;
    }

}
