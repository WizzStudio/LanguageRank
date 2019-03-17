package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 学习计划第八天的内容即为最终的奖励
    @NotNull
    private Integer studyPlanDay;

    @NotNull
    private String languageName;

    @NotNull
    private String contentOne;

    @NotNull
    private String contentTwo;
}
