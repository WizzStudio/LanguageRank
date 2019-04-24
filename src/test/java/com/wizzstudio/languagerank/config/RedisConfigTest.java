package com.wizzstudio.languagerank.config;

import com.wizzstudio.languagerank.domain.User.User;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;



/*
Created by Ben Wen on 2019/3/21.
*/

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, User> cacheRedisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setUserTest() {
        User user = userService.findByUserId(3);
        redisUtil.setUser(3, user);
        User user2 = cacheRedisTemplate.opsForValue().get(Integer.toString(3));
        System.out.println(user2);
    }

    @Test
    public void flushCacheRedisTest() {
        redisUtil.flushUserCacheRedis();
    }

    @Test
    public void setUserRelationshipTest() {
        // Test中修改数据库后会自动删除
        redisUtil.setUserRelationship(3, 5);
        redisUtil.setUserRelationship(3, 4);
        redisUtil.setUserRelationship(4, 7);
        redisUtil.setUserRelationship(4, 5);
        redisUtil.setUserRelationship(4, 8);
        redisUtil.setUserRelationship(8, 5);
        redisUtil.setUserRelationship(5, 7);
//        redisUtil.getUserRelationship(3);
//        redisUtil.getUserRelationship(4);
//        redisUtil.getUserRelationship(5);
//        redisUtil.getUserRelationship(7);
//        redisUtil.getUserRelationship(8);
    }

//    @Test
//    public void jedisTest() {
//        Jedis jedis = new Jedis("47.105.192.87",6666);
//        jedis.auth("xdwizzno1");
//        jedis.set("benTest","test");
//        System.out.println(jedis.get("benTest"));
//    }
}