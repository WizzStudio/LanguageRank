package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/3/16.
*/


import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {
    @Autowired
    RedisTemplate<String, User> redisTemplate;
    @Autowired
    UserDAO userDAO;

    // 以userId值为key，user对象为value存入redis中，30分钟(7200秒)后过期，时间单位为秒
    // 如果redis中没有该用户则新增，有该用户则更新数据
    public void setUser(Integer userId, User user) {
        redisTemplate.opsForValue().set(Integer.toString(userId), user, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
    }

    // 如果redis中没有该用户，则查询mysql并存入redis中，如果有则直接返回
    public User getUser(Integer userId) {
        User user = redisTemplate.opsForValue().get(Integer.toString(userId));
        if (user == null) {
            user = userDAO.findByUserId(userId);
            setUser(userId, user);
        }
        return user;
    }

    // 清空redis中的key
    public void flushRedis() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }
//
//    /**
//     * 存储key，value，在expire分钟后过期
//     */
//    public void store(String key, User user, Integer expire) {
//        redisTemplate.opsForValue().set(key, user, expire, TimeUnit.MINUTES);
//    }
//
//    /**
//     * 存储key，value，在expire（单位待传）时间后过期
//     */
//    public void store(String key,  User user, Integer expire, TimeUnit timeUnit) {
//        redisTemplate.opsForValue().set(key, user, expire, timeUnit);
//    }
//
//    public void increment(String key, Double value) {
//        redisTemplate.opsForValue().increment(key, value);
//    }
//
//    public User get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }

//    public void store(String key, Object value, Integer expire){
//        redisTemplate.opsForValue().set(key, JSON.toJSONString(value),expire,TimeUnit.MINUTES);
//    }
//
//    public Object getObj(String key, Class c){
//       return JSON.parseObject(redisTemplate.opsForValue().get(key),c);
//    }

}
