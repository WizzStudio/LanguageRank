package com.wizzstudio.languagerank.config;

import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.transaction.Transactional;

import java.util.concurrent.TimeUnit;



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
//
//        Assert.assertEquals("FIRST_DAY",redisTemplate.opsForaVlue().get("aaa").getStudyPlanDay().toString());

        User user = userService.findByUserId(7);
//        redisTemplate.opsForValue().set("3", user);
        redisUtil.setUser(7, user);
        System.out.println(redisTemplate.opsForValue().get("7"));
    }

    @Test
    public void jedisTest() {
        Jedis jedis = new Jedis("47.105.192.87",6666);
        jedis.auth("xdwizzno1");
        jedis.set("benTest","test");
        System.out.println(jedis.get("benTest"));
    }
}