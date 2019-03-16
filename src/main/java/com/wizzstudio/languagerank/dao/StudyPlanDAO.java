package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyPlanDAO extends JpaRepository<StudyPlan, Integer> {
    StudyPlan findByLanguageNameAndStudyPlanDay(String languageName, Integer studyPlanDay);
}
