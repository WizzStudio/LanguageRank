package com.wizzstudio.languagerank.aspect;


import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.dao.LanguageCountDAO;
import com.wizzstudio.languagerank.domain.LanguageCount;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.annotation.Transactional;

@Aspect
@Configuration
public class StatisticAspect {

    @Autowired
    private LanguageCountDAO languageCountDAO;

    @Pointcut("execution(public * com.wizzstudio.languagerank.controller.UserController.updateLanguage(..))")
    public void newLanguageNumber() {
    }

    @AfterReturning("newLanguageNumber()")
    @Transactional(rollbackFor = Exception.class)
    public void addLanguageNumber(JoinPoint joinPoint){

        LanguageCount languageCount = languageCountDAO.findByLanguageName(((JSONObject)joinPoint.getArgs()[0]).getString("languageName"));
        // increaseNumber加1
        languageCountDAO.updateIncreaseNumber(languageCount.getIncreaseNumber()+1, languageCount.getLanguageName());
    }
}
