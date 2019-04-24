package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;


/*
Created by Ben Wen on 2019/3/16.
*/

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void saveUserTest() {
//        assertNotNull(userService.saveUser("abcdefg"));
    }

    @Test
    public void getUser() {
        System.out.println(redisUtil.getUser(25));
    }
}