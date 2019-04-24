package com.wizzstudio.languagerank.constants;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import org.springframework.data.domain.Sort;

public interface Constant {
    // cookie过期时间30分钟(1800秒)
    Integer TOKEN_EXPIRED = 1800;

    String TOKEN = "token";

    String[] STUDY_PLAN_LANGUAGE = {"Java", "Python", "PHP", "C", "C++", "JavaScript"};

    // code返回值为2表示该语言相关学习计划还未准备好，暂时不可加入学习计划
    Integer NOT_READY_lANGUAGE = 2;

    // code返回值为3表示该语言正在学习中，不可重复加入
    Integer STUDYING_NOW = 3;

    // 留言一页显示几条
    Integer PAGE_SIZE = 20;
}
