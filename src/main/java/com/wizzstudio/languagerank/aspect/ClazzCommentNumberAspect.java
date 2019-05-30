package com.wizzstudio.languagerank.aspect;

/*
Created by Ben Wen on 2019/5/17.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Configuration
public class ClazzCommentNumberAspect {
    @Autowired
    ClazzDAO clazzDAO;
    @Autowired
    RedisUtil redisUtil;

    @Pointcut("execution(public * com.wizzstudio.languagerank.controller.CommentController.updateClazzComment(..))")
    private void newComment() {}

    @AfterReturning("newComment()")
    @Transactional(rollbackFor = Exception.class)
    public void increaseCommentNumber(JoinPoint joinPoint) {
        Integer clazzId = ((JSONObject)joinPoint.getArgs()[0]).getInteger("clazzId");

        Clazz clazz = clazzDAO.findByClazzId(clazzId);
        clazz.setCommentNumber(clazz.getCommentNumber() + 1);
        redisUtil.incrComment(clazzId);
    }
}
