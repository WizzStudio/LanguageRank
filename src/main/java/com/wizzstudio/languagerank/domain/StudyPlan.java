package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class StudyPlan {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Integer studyPlanDay;

    @NotNull
    private String contentOne;

    @NotNull
    private String contentTwo;
}