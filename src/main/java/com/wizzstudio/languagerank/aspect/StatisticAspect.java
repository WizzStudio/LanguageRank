package com.wizzstudio.languagerank.aspect;


import com.wizzstudio.languagerank.domain.LanguageCount;
import com.wizzstudio.languagerank.service.LanguageService;
import com.wizzstudio.languagerank.service.impl.LanguageServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Aspect
@Component
public class StatisticAspect {

    @Autowired
    private LanguageServiceImpl languageService;

    @Pointcut("execution(public * com.wizzstudio.languagerank.controller.(..))")
//    未写完 controller...
    public void newLanguageNumber() {
    }

    @AfterReturning("newLanguageNumber()")
    public void addLanguageNumber(JoinPoint joinPoint){

        LanguageCount languageCount = languageService.findByLanguageName((String) joinPoint.getArgs()[0]);
        languageService.update(languageCount.getNumber()+1, languageCount.getLanguageName());

    }

}
