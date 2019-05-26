package com.wizzstudio.languagerank.constants;

/*
Created by Ben Wen on 2019/5/17.
*/

public interface Errors {
    /**
     * 该语言相关学习计划还未准备好，暂时不可加入学习计划
     */
    Integer NOT_READY_LANGUAGE = 2;

    /**
     * 该语言正在学习中，不可重复加入
     */
    Integer STUDYING_NOW = 3;

    /**
     * 班级标签非法
     */
    Integer ILLEGAL_CLAZZ_TAG = 4;

    /**
     * 用户已加入该班级，不可重复加入
     */
    Integer JOINED_CLAZZ = 5;

    /**
     * 用户今日已膜拜过该用户
     */
    Integer WORSHIPPED_TODAY = 6;

    /**
     * 打卡提醒时间参数有误
     */
    Integer ILLEGAL_ARGUMENT_IN_UPDATE_PUNCHCARD_REMINDER_TIME = 7;

    /**
     * 用户兑换奖品积分不足
     */
    Integer NOT_ENOUGH_SCORE = 8;

    /**
     * 用户已收藏过该学习计划，不可重复收藏
     */
    Integer COLLECTED_STUDY_PLAN = 9;
}
