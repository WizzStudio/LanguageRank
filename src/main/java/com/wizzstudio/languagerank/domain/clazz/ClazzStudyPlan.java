package com.wizzstudio.languagerank.domain.clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class ClazzStudyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer clazzId;

    /**
     * 每日学习计划简介
     */
    @NotNull
    private String briefIntroduction;

    /**
     * 每日学习计划的百度网盘链接
     */
    @NotNull
    private String link;

    /**
     * 提取码
     */
    @NotNull
    private String extractedCode;

    /**
     * 每日学习计划的百度网盘小程序码
     */
    @NotNull
    private String qrCode;

    /**
     * 每日学习计划内容
     */
    @NotNull
    private String content;

    /**
     * 学习计划天数
     */
    @NotNull
    private Integer studyPlanDay;
}
