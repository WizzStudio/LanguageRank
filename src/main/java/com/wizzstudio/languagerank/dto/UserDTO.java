package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import lombok.Data;

import java.util.List;
import java.util.Map;

// 获取用户信息接口返回的数据
@Data
public class UserDTO {

    private String myLanguage;

    private Boolean isViewedStudyPlan;

    private List<StudyPlan> studyPlan;

    private Integer joinedNumber;

    private Integer joinedToday;

    // 用户已经完成了几天的学习计划
    private Integer studyPlanDay;

    private List<Boolean> isTranspondedList;
}
