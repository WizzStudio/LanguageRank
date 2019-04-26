package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzStudyPlanDAO extends JpaRepository<ClazzStudyPlan, Integer> {
}
