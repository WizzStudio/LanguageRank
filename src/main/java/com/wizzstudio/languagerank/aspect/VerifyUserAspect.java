//package com.wizzstudio.languagerank.aspect;
//
//import com.wizzstudio.languagerank.domain.User;
//import com.wizzstudio.languagerank.util.CookieUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletRequest;
//
///*
//Created by Ben Wen on 2019/3/21.
//*/
//@Configuration
//@Aspect
//public class VerifyUserAspect {
//
//    @Autowired
//    RedisTemplate<String, User> redisTemplate;
//
//    @Pointcut("execution(public * com.wizzstudio.languagerank.controller.UserController.getMyAward())" +
//            "&& execution(public * com.wizzstudio.languagerank.controller.UserController.updateLanguage())")
//    private void label(){}
//
//    @Before("label()")
//    @Transactional(rollbackFor = Exception.class)
//    public void verifyUser(JoinPoint joinPoint){
//        if (redisTemplate.opsForValue().get(CookieUtil.getCookie((HttpServletRequest)joinPoint.getArgs()[0])) == null) {
//
//        }
//    }
//}
