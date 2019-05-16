package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.DTO.StudyPlanImageDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;

import java.util.List;

public interface StudyPlanService {
    /**
     * 查找某一日的学习计划
     */
    StudyPlan findStudyPlanByLanguageNameAndStudyPlanDay(String languageName ,StudyPlanDayEnum studyPlanDay);

    /**
     * 获取所选语言已学习的全部学习计划
     */
    List<StudyPlan> getStudyedStudyPlanDay(String languageName ,Integer studyPlanDay);

    /**
     * 获取某语言的全部的学习计划
     */
    List<StudyPlan> getAllStudyPlanDay(String languageName);

    /**
     * 新增/修改某语言某天的学习计划/奖励
     */
    void saveStudyPlan(String filePath, StudyPlanImageDTO studyPlanImageDTO);
}
