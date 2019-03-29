package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyPlanDAO extends JpaRepository<StudyPlan, Integer> {

//    获取某天的学习计划
    StudyPlan findByLanguageNameAndStudyPlanDay(String languageName, StudyPlanDayEnum studyPlanDay);

    //    获取该用户已学习全部天数的学习计划
    @Query(nativeQuery = true, value = "select * from study_plan where language_name = :languageName" +
            " order by study_plan_day limit :studyPlanDay")
    List<StudyPlan> getAllStudyPlanDay(@Param("languageName") String languageName,
                                       @Param("studyPlanDay")Integer studyPlanDay);

    //    获取某语言的全部学习计划
    @Query(nativeQuery = true, value = "select * from study_plan where language_name = :languageName" +
            " order by study_plan_day")
    List<StudyPlan> getAllStudyPlanDay(@Param("languageName") String languageName);
}
