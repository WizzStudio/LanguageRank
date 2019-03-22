package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/3/16.
*/


import com.alibaba.fastjson.JSON;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    // 以cookie值为key，user对象为value存入redis中，Constant.TOKEN_EXPIRED时间后过期，时间单位为TimeUnit.HOURS
    public void storeNewUser(String cookie, User user) {
        redisTemplate.opsForValue().set(cookie, user, Constant.TOKEN_EXPIRED, TimeUnit.HOURS);
    }

    /**
     * 存储key，value，在expire分钟后过期
     */
    public void store(String key, User user, Integer expire) {
        redisTemplate.opsForValue().set(key, user, expire, TimeUnit.MINUTES);
    }

    /**
     * 存储key，value，在expire（单位待传）时间后过期
     */
    public void store(String key,  User user, Integer expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, user, expire, timeUnit);
    }

    public void increment(String key, Double value) {
        redisTemplate.opsForValue().increment(key, value);
    }

    public User get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

//    public void store(String key, Object value, Integer expire){
//        redisTemplate.opsForValue().set(key, JSON.toJSONString(value),expire,TimeUnit.MINUTES);
//    }
//
//    public Object getObj(String key, Class c){
//       return JSON.parseObject(redisTemplate.opsForValue().get(key),c);
//    }

}
