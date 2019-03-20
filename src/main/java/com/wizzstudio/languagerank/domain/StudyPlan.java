package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 学习计划第八天的内容即为最终的奖励
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private String studyPlanDay;

    @NotNull
    private String languageName;

    @NotNull
    private String imageOne;

    private String contentOne;

    @NotNull
    private String imageTwo;

    private String contentTwo;
}
