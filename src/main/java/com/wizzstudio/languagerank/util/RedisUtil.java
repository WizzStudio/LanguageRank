package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/3/16.
*/


import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.dao.UserDAO.UserRelationshipDAO;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.domain.UserRelationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class RedisUtil {
    @Autowired
    RedisTemplate<String, User> userCacheRedisTemplate;
    @Autowired
    RedisTemplate<String, String> userRelationshipRedisTemplate;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRelationshipDAO userRelationshipDAO;

    // 以userId值为key，user对象为value存入redis中，30分钟(1800秒)后过期，时间单位为秒
    // 如果redis中没有该用户则新增，有该用户则更新数据
    public void setUser(Integer userId, User user) {
        userCacheRedisTemplate.opsForValue().set(Integer.toString(userId), user, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
    }

    // 如果redis中没有该用户，则查询mysql并存入redis中，如果有则直接返回
    @Transactional(rollbackFor = Exception.class)
    public User getUser(Integer userId) {
        User user = userCacheRedisTemplate.opsForValue().get(Integer.toString(userId));
        if (user == null) {
            user = userDAO.findByUserId(userId);
            setUser(userId, user);
            userDAO.updateLogInLastTime(new Date(), userId);
        }
        return user;
    }

    // 清空redis中的key
    public void flushUserCacheRedis() {
        userCacheRedisTemplate.delete(userCacheRedisTemplate.keys("*"));
    }

    public void setUserRelationship(Integer userOne, Integer userTwo) {
        // 互相加入对方好友集合中，用set集合可以不用考虑重复的问题
        userRelationshipRedisTemplate.opsForSet().add(Integer.toString(userOne), Integer.toString(userTwo));
        long isAddSuccess = userRelationshipRedisTemplate.opsForSet().add(Integer.toString(userTwo), Integer.toString(userOne));
        // 若出现新的好友关系，写入MySQL数据库
        if (isAddSuccess == 1) {
            UserRelationship userRelationship = new UserRelationship();
            userRelationship.setUserOne(userOne);
            userRelationship.setUserTwo(userTwo);
            userRelationshipDAO.save(userRelationship);
            log.info("新增好友关系成功");
        }
    }

    public Set<String> getUserRelationship(Integer userId) {
        return userRelationshipRedisTemplate.opsForSet().members(Integer.toString(userId));
    }
}
