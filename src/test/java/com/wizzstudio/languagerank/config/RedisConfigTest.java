package com.wizzstudio.languagerank.config;

import com.wizzstudio.languagerank.domain.User;
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
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTemplateTest() {
//        User user = userService.saveUser("abcdefg");
//        redisTemplate.opsForValue().set("aaa", user, Constant.TOKEN_EXPIRED, TimeUnit.HOURS);
//        Assert.assertEquals("FIRST_DAY",redisTemplate.opsForaVlue().get("aaa").getStudyPlanDay().toString());

        User user = userService.findByUserId(3);
        redisUtil.setUser(3, user);
        User user2 = redisTemplate.opsForValue().get(Integer.toString(3));
        System.out.println(user2);
    }

    @Test
    public void flushRedisTest() {
        redisUtil.flushRedis();
    }
//    @Test
//    public void jedisTest() {
//        Jedis jedis = new Jedis("47.105.192.87",6666);
//        jedis.auth("xdwizzno1");
//        jedis.set("benTest","test");
//        System.out.println(jedis.get("benTest"));
//    }
}