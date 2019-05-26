package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/3/16.
*/


import com.wizzstudio.languagerank.DAO.clazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserRelationshipDAO;
import com.wizzstudio.languagerank.domain.clazz.UserClazz;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.domain.user.UserRelationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RedisUtil {
    @Autowired
    RedisTemplate<String, User> userCacheRedisTemplate;
    @Autowired
//    @Qualifier("userRelationshipAndUserClazzRedisTemplate")
    RedisTemplate<String, String> userRelationshipAndUserClazzRedisTemplate;
//    @Autowired
//    @Qualifier("clazzMemberRedisTemplate")
//    RedisTemplate<String, String> clazzMemberRedisTemplate;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRelationshipDAO userRelationshipDAO;
    @Autowired
    UserClazzDAO userClazzDAO;

    private static String userRelationshipPrefix = "user";
    private static String clazzMemberPrefix = "clazz";

//     以userId值为key，user对象为value存入redis中，30分钟(1800秒)后过期，时间单位为秒
    // 如果redis中没有该用户则新增，有该用户则更新数据
    @Transactional(rollbackFor = Exception.class)
    public void setUser(Integer userId, User user) {
//        userCacheRedisTemplate.opsForValue().set(Integer.toString(userId), user, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
        userCacheRedisTemplate.opsForValue().set(Integer.toString(userId), user);
        userDAO.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUserOnlyInRedis(Integer userId, User user) {
        userCacheRedisTemplate.opsForValue().set(Integer.toString(userId), user, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
    }

    // 如果redis中没有该用户，则查询mysql并存入redis中，如果有则直接返回
    @Transactional(rollbackFor = Exception.class)
    public User getUser(Integer userId) {
        User user = userCacheRedisTemplate.opsForValue().get(Integer.toString(userId));
        if (user == null) {
            user = userDAO.findByUserId(userId);
            user.setLogInLastTime(new Date());
            setUser(userId, user);
//            userDAO.updateLogInLastTime(new Date(), userId);
        }
        return user;
}

    // 清空redis中的key
    @Transactional(rollbackFor = Exception.class)
    public void flushUserCacheRedis() {
        userCacheRedisTemplate.delete(userCacheRedisTemplate.keys("*"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUserRelationship(Integer userOne, Integer userTwo) {
        // 互相加入对方好友集合中，用set集合可以不用考虑重复的问题
        userRelationshipAndUserClazzRedisTemplate.opsForSet().add(userRelationshipPrefix + Integer.toString(userOne), Integer.toString(userTwo));
        long isAddSuccess = userRelationshipAndUserClazzRedisTemplate.opsForSet().add(userRelationshipPrefix + Integer.toString(userTwo), Integer.toString(userOne));
        // 若出现新的好友关系，写入MySQL数据库
        if (isAddSuccess == 1) {
            UserRelationship userRelationship = new UserRelationship();
            userRelationship.setUserOne(userOne);
            userRelationship.setUserTwo(userTwo);
            userRelationship.setJoinedTime(new Date());
            userRelationshipDAO.save(userRelationship);
            log.info(userOne + "号用户与"+ userTwo + "号用户新增好友关系成功");
        }
    }

    // Java8 stream流式计算
    public List<Integer> getUserRelationship(Integer userId) {
        List<String> stringList = new ArrayList<>(userRelationshipAndUserClazzRedisTemplate.opsForSet().members(userRelationshipPrefix + Integer.toString(userId)));
        return stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void joinClazz(Integer userId, Integer clazzId) {
        long isJoinedClazz =  userRelationshipAndUserClazzRedisTemplate.opsForSet().add(clazzMemberPrefix + Integer.toString(clazzId), Integer.toString(userId));
        if (isJoinedClazz == 1) {
            UserClazz userClazz = new UserClazz();
            userClazz.setUserId(userId);
            userClazz.setClazzId(clazzId);
            userClazz.setJoinedTime(new Date());
            userClazz.setAllStudyPlanDay(0);
            // 还有问题，当用户一天重复加入某一班级时;
            userClazz.setUninterruptedStudyPlanDay(0);
            userClazz.setStudyTime(null);
            userClazz.setIsStudyToday(false);

            userClazzDAO.save(userClazz);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void quitClazz(Integer userId, Integer clazzId) {
        userRelationshipAndUserClazzRedisTemplate.opsForSet().move(clazzMemberPrefix + Integer.toString(clazzId), Integer.toString(userId), "quitClazzPerson");
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(clazzId, userId);
        userClazzDAO.delete(userClazz);
    }

    public List<Integer> getClazzMember(Integer clazzId) {
        List<String> stringList = new ArrayList<>(userRelationshipAndUserClazzRedisTemplate.opsForSet().members(clazzMemberPrefix + Integer.toString(clazzId)));
        return stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<Integer> getUserRelationshipInClazz(Integer userId, Integer clazzId) {
        List<String> stringList = new ArrayList<>(userRelationshipAndUserClazzRedisTemplate.opsForSet().intersect
                (userRelationshipPrefix + Integer.toString(userId),
                        clazzMemberPrefix + Integer.toString(clazzId)));
        return stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public Long getTheNumberOfStudents(Integer clazzId) {
        return userRelationshipAndUserClazzRedisTemplate.opsForSet().size(clazzMemberPrefix + Integer.toString(clazzId));
    }
}
