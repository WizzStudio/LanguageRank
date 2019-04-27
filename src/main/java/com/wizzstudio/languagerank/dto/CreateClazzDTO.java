package com.wizzstudio.languagerank.dto;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import lombok.Data;

import java.util.List;

@Data
public class CreateClazzDTO {
    private String clazzName;

    private String clazzTag;

    private String clazzImage;

    private Integer monitor;

    private List<ClazzStudyPlan> clazzStudyPlanList;
}
