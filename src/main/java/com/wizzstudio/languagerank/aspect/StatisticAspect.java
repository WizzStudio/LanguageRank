package com.wizzstudio.languagerank.aspect;


import com.wizzstudio.languagerank.dao.LanguageCountDAO;
import com.wizzstudio.languagerank.domain.LanguageCount;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class StatisticAspect {

    @Autowired
    private LanguageCountDAO languageCountDAO;

//    @Pointcut("execution(public * com.wizzstudio.languagerank.controller.(..))")
//    未写完 controller...
    public void newLanguageNumber() {
    }

//    @AfterReturning("newLanguageNumber()")
    @Transactional(rollbackFor = Exception.class)
    public void addLanguageNumber(JoinPoint joinPoint){

        LanguageCount languageCount = languageCountDAO.findByLanguageName((String) joinPoint.getArgs()[0]);
        // increaseNumber加1
        languageCountDAO.updateIncreaseNumber(languageCount.getIncreaseNumber()+1, languageCount.getLanguageName());

    }
}
