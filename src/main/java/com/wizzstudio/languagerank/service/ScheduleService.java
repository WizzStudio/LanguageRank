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
     * 每日更新每种语言学习的总人数，并重置每日新增人数
     */
    void updateNumber();

    /**
     * 每天零点计算并保存语言热度榜的最终指数
     */
    void saveFixedRankExponent();

//    /**
//     * 每日更新一次FixedRankServiceImpl中的list（缓存用）
//     */
//    void resetFixedRankList();
//
//    /**
//     * 每日更新一次LanguageHomePageServiceImpl中的map（缓存用）
//     */
//    void resetLanguageHomePageMap();

    /**
     * 每周更新一次EmployeeServiceImpl中的list（缓存用）
     */
    void resetEmployeeList();

    /**
     * 每周一零点计算并保存雇主需求榜最终指数
     */
    void saveEmployeeRankExponent();

//    /**
//     * 每周更新一次list（缓存用）
//     */
//    void resetEmployeeRankList();
}
