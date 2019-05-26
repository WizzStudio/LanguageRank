package com.wizzstudio.languagerank.DTO.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProjectStudyPlanDTO {

    /**
     * 每日学习计划简介
     */
    private String briefIntroduction;

    /**
     * 每日学习计划的百度网盘链接
     */
    private String link;

    /**
     * 每日学习计划的百度网盘小程序码
     */
    private String qrCode;

    private String content;

    private Integer studyPlanDay;

}
