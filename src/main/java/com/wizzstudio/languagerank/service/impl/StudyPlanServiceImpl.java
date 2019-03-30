package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.dao.StudyPlanDAO;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyPlanServiceImpl implements StudyPlanService {

    @Autowired
    private StudyPlanDAO studyPlanDAO;

    @Override
    public StudyPlan findStudyPlanByLanguageNameAndStudyPlanDay(String languageName ,StudyPlanDayEnum studyPlanDay) {
        return studyPlanDAO.findByLanguageNameAndStudyPlanDay(languageName, studyPlanDay);
    }

    @Override
    public List<StudyPlan> getAllStudyPlanDay(String languageName, Integer studyPlanDay) {
        return studyPlanDAO.getAllStudyPlanDay(languageName, studyPlanDay);
    }

    @Override
    public List<StudyPlan> getAllStudyPlanDay(String languageName) {
        return studyPlanDAO.getAllStudyPlanDay(languageName);
    }
}
