package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClazzStudyPlanDAO extends JpaRepository<ClazzStudyPlan, Integer> {
    @Query("select c.content from ClazzStudyPlan c where c.clazzId = :clazzId order by c.studyPlanDay ASC")
    List<String> getAllContent(@Param("clazzId") Integer clazzId);
}
