package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import lombok.Data;

import java.util.List;

/**
 * 创建班级接口的接收参数类型
 */
@Data
public class CreateClazzDTO {
    private String clazzName;

    private String clazzTag;

    private String clazzImage;

    private Integer monitor;

    private String clazzBriefIntroduction;

    private List<ClazzStudyPlan> clazzStudyPlanList;
}
