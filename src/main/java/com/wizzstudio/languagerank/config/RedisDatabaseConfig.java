package com.wizzstudio.languagerank.config;

/*
Created by Ben Wen on 2019/4/5.
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
//@EnableAutoConfiguration
public class RedisDatabaseConfig {
//    @Value("${redis.database.moreLanguageInformation}")
//    private int moreLanguageInformationDatabase;
//
//    @Value("${redis.host}")
//    private String host;
//
//    @Value("${redis.password}")
//    private String password;
//
//    @Value("${redis.port}")
//    private int port;
//
//    @Value("${redis.timeout}")
//    private int timeout;
//
//    @Value("${redis.jedis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${redis.jedis.pool.min-idle}")
//    private int minIdle;
//
//    @Bean
//    public JedisPoolConfig getJedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
////        jedisPoolConfig.setMaxWaitMillis(maxWait);
//        return jedisPoolConfig;
//    }
//
//
//    @Bean(name = "foreRedisTemplate")
//    public RedisTemplate getForeRedisTemplate(){
//
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setPoolConfig(getJedisPoolConfig());
//        connectionFactory.setDatabase(moreLanguageInformationDatabase);	//此处配置database
//        connectionFactory.setHostName(host);
//        connectionFactory.setPassword(password);
//        connectionFactory.setPort(port);
//        connectionFactory.setTimeout(timeout);
//        connectionFactory.afterPropertiesSet();			//记得添加这行！
//
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
//        return stringRedisTemplate;
//    }
//
////    @Bean(name = "wechatRedisTemplate")
////    public RedisTemplate getWechatRedisTemplate(){
////
////        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
////        connectionFactory.setPoolConfig(getJedisPoolConfig());
////        connectionFactory.setDatabase(wechatDatabase);
////        connectionFactory.setHostName(host);
////        connectionFactory.setPassword(password);
////        connectionFactory.setPort(port);
////        connectionFactory.setTimeout(timeout);
////        connectionFactory.afterPropertiesSet();
////
////        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
////        return stringRedisTemplate;
////    }
}
