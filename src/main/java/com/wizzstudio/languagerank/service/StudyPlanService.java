package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;

import java.util.List;

public interface StudyPlanService {
    /**
     * 查找某一日的学习计划
     */
    StudyPlan findStudyPlanByLanguageNameAndStudyPlanDay(String languageName ,StudyPlanDayEnum studyPlanDay);

    /**
     * 获取已学习的全部学习计划
     */
    List<StudyPlan> getAllStudyPlanDay(String languageName ,Integer studyPlanDay);
}
