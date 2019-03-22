package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StudyPlanDAO extends JpaRepository<StudyPlan, Integer> {
    StudyPlan findByLanguageNameAndStudyPlanDay(String languageName, StudyPlanDayEnum studyPlanDay);
}
