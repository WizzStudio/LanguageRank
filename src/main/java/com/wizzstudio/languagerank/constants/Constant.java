package com.wizzstudio.languagerank.constants;

/*
Created by Ben Wen on 2019/3/16.
*/

public interface Constant {
    // cookie过期时间30分钟(1800秒)
    Integer TOKEN_EXPIRED = 1800;

    String TOKEN = "token";

    String[] STUDY_PLAN_LANGUAGE = {"Java", "Python", "PHP", "C", "C++", "JavaScript"};

    /**
     * 班级标签
     */
    String[] CLAZZ_TAG = {"Java", "Python", "C", "C++", "JavaScript", "PHP"};

    // code返回值为2表示该语言相关学习计划还未准备好，暂时不可加入学习计划
    Integer NOT_READY_LANGUAGE = 2;

    // code返回值为3表示该语言正在学习中，不可重复加入
    Integer STUDYING_NOW = 3;

    // 班级标签非法
    Integer ILLEGAL_CLAZZ_TAG = 4;

    // 留言一页显示几条
    Integer PAGE_SIZE = 20;

    /**
     * 微信模板消息Id
     */
    String TEMPLATE_ID = "9ttE1_oi6s6s7woxAAuGwWtlY1H0RPG26clRX4i-vek";

    /**
     * 点击微信模板跳转的路径
     */
    String PAGE_PATH = "";
}
