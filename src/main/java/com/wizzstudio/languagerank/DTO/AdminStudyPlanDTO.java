package com.wizzstudio.languagerank.DTO;

import lombok.Data;

@Data
public class AdminStudyPlanDTO {

    private String languageName;
//    今日加入学习计划人数
    private Integer increaseNumber;
//    学习计划总人数
    private Integer number;

}
