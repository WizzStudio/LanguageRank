package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/17.
*/

public interface ScheduleService {
    /**
     * 每天零点更新用户今天是否登录与用户今天是否学过某种语言，同时清空redis
     */
    void updateAllIsLogInToDay();

    /**
     * 每日更新每种语言学习的总人数，重置每日新增人数，更新一次FixedRankServiceImpl中的list和LanguageHomePageServiceImpl中的map
     */
    void updateNumber();

    /**
     * 每天零点计算并保存语言热度榜的最终指数
     */
    void saveFixedRankExponent();

    /**
     * 每周更新一次EmployeeServiceImpl和EmployeeRankService中的list（缓存用）
     */
    void resetEmployeeList();

    /**
     * 每周一零点计算并保存雇主需求榜最终指数
     */
    void saveEmployeeRankExponent();

//    /**
//     * 每日八点向部分用户推送消息
//     */
//    void pushMessageAtEight();
//
//    /**
//     * 每日九点向部分用户推送消息
//     */
//    void pushMessageAtNine();
//
//    /**
//     * 每日十点向部分用户推送消息
//     */
//    void pushMessageAtTen();
//
//    /**
//     * 每日十一点向部分用户推送消息
//     */
//    void pushMessageAtEleven();
}
