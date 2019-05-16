package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClazzStudyPlanDAO extends JpaRepository<ClazzStudyPlan, Integer> {
    @Query("select c.briefIntroduction from ClazzStudyPlan c where c.clazzId = :clazzId order by c.studyPlanDay ASC")
    List<String> getAllBriefIntroduction(@Param("clazzId") Integer clazzId);

    List<ClazzStudyPlan> findByClazzIdAndStudyPlanDayLessThanEqualOrderByStudyPlanDayAsc(Integer clazzId, Integer studyPlanDay);

    @Query("select c.qrCode from ClazzStudyPlan c where c.clazzId = :clazzId and c.studyPlanDay = :studyPlanDay")
    String findQRCodeToday(@Param("clazzId") Integer clazzId, @Param("studyPlanDay")Integer studyPlanDay);
}
