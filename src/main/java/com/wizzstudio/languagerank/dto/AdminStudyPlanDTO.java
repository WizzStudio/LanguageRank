package com.wizzstudio.languagerank.dto;

import lombok.Data;

@Data
public class AdminStudyPlanDTO {

    private String languageName;
//    今日加入学习计划人数
    private Integer increasenumber;
//    学习计划总人数
    private Integer number;

}
