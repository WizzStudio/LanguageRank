package com.wizzstudio.languagerank.constants;

/*
Created by Ben Wen on 2019/3/16.
*/

public interface Constant {
    // cookie过期时间24小时
    Integer TOKEN_EXPIRED = 24;

    String TOKEN = "token";

    String[] STUDY_PLAN_LANGUAGE = {"Java", "Python", "PHP", "C", "C++", "JavaScript"};

    // code返回值为2表示该语言相关学习计划还未准备好，暂时不可加入学习计划
    Integer NOT_READY_lANGUAGE = 2;

    Integer STUDYING_NOW = 3;
}
