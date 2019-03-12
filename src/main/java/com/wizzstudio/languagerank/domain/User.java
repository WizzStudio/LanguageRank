package com.wizzstudio.languagerank.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String openId;

    // 个人主页上学习的语言
    private String myLanguage;

    // 即将进行第几天的计划
    @NotNull
    private Integer studyPlanDay;

}
