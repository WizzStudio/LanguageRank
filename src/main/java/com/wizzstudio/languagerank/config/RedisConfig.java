package com.wizzstudio.languagerank.config;

/*
Created by Ben Wen on 2019/3/21.
*/

import com.wizzstudio.languagerank.domain.User.User;
import com.wizzstudio.languagerank.util.serializer.RedisObjectSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.database.userCache}")
    private int userCacheDatabase;

    @Value("${spring.redis.database.userRelationship}")
    private int userRelationshipDatabase;

    @Bean
    public RedisTemplate<String, User> cacheRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(userCacheDatabase);

        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(new JedisConnectionFactory(redisStandaloneConfiguration));
        // 设置value的序列化规则和 key的序列化规则
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, String> userRelationshipRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(userRelationshipDatabase);

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(new JedisConnectionFactory(redisStandaloneConfiguration));
        template.setKeySerializer(new StringRedisSerializer());
        // 如果设置为RedisObjectSerializer，redis中会在value前面加上\xac\xed\x00\x05t\x00\x03
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        //最大连接数
//        jedisPoolConfig.setMaxTotal(100);
//        //最小空闲连接数
//        jedisPoolConfig.setMinIdle(20);
//        //当池内没有可用的连接时，最大等待时间
//        jedisPoolConfig.setMaxWaitMillis(10000);
//        //------其他属性根据需要自行添加-------------
//        return jedisPoolConfig;
//    }
}