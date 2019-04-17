package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/17.
*/

import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.dao.UserStudyedLanguageDAO;
import com.wizzstudio.languagerank.service.*;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserStudyedLanguageDAO userStudyedLanguageDAO;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LanguageCountService languageCountService;
    @Autowired
    FixedRankService fixedRankService;
    @Autowired
    LanguageHomePageService languageHomePageService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRankService employeeRankService;

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateAllIsLogInToDay() {
        userDAO.resetIsLogInToday();
        userStudyedLanguageDAO.resetIsStudyedToday();
        redisUtil.flushRedis();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateNumber() {
        languageCountService.updateNumber();
        fixedRankService.resetList();
        languageHomePageService.resetMap();
    }

    @Override
    @Scheduled(cron = "0 55 23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveFixedRankExponent() {
        fixedRankService.saveExponent();
    }

//    @Override
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Transactional(rollbackFor = Exception.class)
//    public void resetFixedRankList() {
//
//    }
//
//    @Override
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Transactional(rollbackFor = Exception.class)
//    public void resetLanguageHomePageMap() {
//
//    }

    @Override
    @Scheduled(cron = "0 0 0 * * 1")
    @Transactional(rollbackFor = Exception.class)
    public void resetEmployeeList() {
        employeeService.resetList();
        employeeRankService.resetList();
    }

    @Override
    @Scheduled(cron = "0 55 23 * * 1")
    @Transactional(rollbackFor = Exception.class)
    public void saveEmployeeRankExponent() {
        employeeRankService.saveExponent();
    }

//    @Override
//    @Scheduled(cron = "0 0 0 * * 1")
//    @Transactional(rollbackFor = Exception.class)
//    public void resetEmployeeRankList() {
//
//    }
}
