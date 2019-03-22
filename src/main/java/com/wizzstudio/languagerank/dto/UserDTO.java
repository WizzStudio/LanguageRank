package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import lombok.Data;

// 获取用户信息接口返回的数据
@Data
public class UserDTO {

    private String myLanguage;

    private Boolean isViewedStudyPlan;

    private StudyPlan studyPlan;

    private Integer joinedNumber;

    private Integer joinedToday;
}
