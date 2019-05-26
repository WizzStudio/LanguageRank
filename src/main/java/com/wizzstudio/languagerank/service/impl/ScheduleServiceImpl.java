package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/17.
*/

import com.wizzstudio.languagerank.DAO.clazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
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
    UserClazzDAO userClazzDAO;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    FixedRankService fixedRankService;
    @Autowired
    LanguageHomePageService languageHomePageService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRankService employeeRankService;
    @Autowired
    PushMessageService pushMessageService;

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateAllIsLogInToDay() {
        userDAO.resetIsLogInToday();
        userDAO.resetIsPunchCardToday();
        userDAO.resetScoreToday();
        // 将两条语句写在一起会不会有问题？
        userClazzDAO.resetUninterruptedStudyPlanDay();
        userClazzDAO.resetIsStudyToday();
        redisUtil.flushUserCacheRedis();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateNumber() {
        fixedRankService.resetList();
        languageHomePageService.resetMap();
    }

    @Override
    @Scheduled(cron = "0 55 23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveFixedRankExponent() {
        fixedRankService.saveExponent();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * 1")
    @Transactional(rollbackFor = Exception.class)
    public void resetEmployeeList() {
        employeeService.resetList();
        employeeRankService.resetList();
    }

    // 5月27晚更新完后把时间提前一天
    @Override
    @Scheduled(cron = "0 55 23 * * 1")
    @Transactional(rollbackFor = Exception.class)
    public void saveEmployeeRankExponent() {
        employeeRankService.saveExponent();
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void pushMessageAtEight() {
        pushMessageService.sendTemplateMsg(PunchReminderTimeEnum.EIGHT);
    }

    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void pushMessageAtNine() {
        pushMessageService.sendTemplateMsg(PunchReminderTimeEnum.NINE);
    }

    @Override
    @Scheduled(cron = "0 0 10 * * ?")
    public void pushMessageAtTen() {
        pushMessageService.sendTemplateMsg(PunchReminderTimeEnum.TEN);
    }

    @Override
    @Scheduled(cron = "0 0 11 * * ?")
    public void pushMessageAtEleven() {
        pushMessageService.sendTemplateMsg(PunchReminderTimeEnum.ELEVEN);
    }
}
