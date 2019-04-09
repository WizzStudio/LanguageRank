package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/12.
*/

import com.wizzstudio.languagerank.dao.StudyPlanDAO;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.dto.StudyPlanImageDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<StudyPlan> getStudyedStudyPlanDay(String languageName, Integer studyPlanDay) {
        List<StudyPlan> studyPlanList = studyPlanDAO.getAllStudyPlanDay(languageName);
        List<StudyPlan> returnStudyPlanList = new ArrayList<>();
        for (StudyPlan studyPlan: studyPlanList) {
            if (studyPlan.getStudyPlanDay().getStudyPlanDay() <= studyPlanDay) {
                returnStudyPlanList.add(studyPlan);
            }
        }
        return returnStudyPlanList;
    }

    @Override
    public List<StudyPlan> getAllStudyPlanDay(String languageName) {
        return studyPlanDAO.getAllStudyPlanDay(languageName);
    }

    @Override
    public void saveStudyPlan(String filePath, StudyPlanImageDTO studyPlanImageDTO) {
        String languageName = studyPlanImageDTO.getLanguageName();
        StudyPlanDayEnum studyPlanDay = StudyPlanDayEnum.getStudyPlanDayByInteger(studyPlanImageDTO.getStudyPlanDay());

        StudyPlan studyPlan =  studyPlanDAO.findByLanguageNameAndStudyPlanDay(languageName, studyPlanDay);

       // studyPlan为null表示该语言该日的学习计划还未写入数据库
       if (studyPlan == null) {
           studyPlan = new StudyPlan();
           studyPlan.setLanguageName(studyPlanImageDTO.getLanguageName());
           studyPlan.setStudyPlanDay(StudyPlanDayEnum.getStudyPlanDayByInteger(studyPlanImageDTO.getStudyPlanDay()));
           // 当修改的是奖励相关信息时
//           if (studyPlanDay == StudyPlanDayEnum.ACCOMPLISHED) {
//               if (studyPlanImageDTO.getImageNumber() == 1) {
//                   studyPlan.setImageOne(filePath);
//                   studyPlan.setContentOne(studyPlanImageDTO.getContent());
//                   studyPlan.setLinkOne(studyPlanImageDTO.getLink());
//                   studyPlan.setImageTwo("无");
//                   studyPlan.setContentTwo("无");
//                   studyPlan.setLinkTwo("无");
//               } else {
//                   studyPlan.setImageOne("无");
//                   studyPlan.setContentOne("无");
//                   studyPlan.setLinkOne("无");
//                   studyPlan.setImageTwo(filePath);
//                   studyPlan.setContentTwo(studyPlanImageDTO.getContent());
//                   studyPlan.setLinkTwo(studyPlanImageDTO.getLink());
//               }
//           } else {
               // 当修改的是学习计划相关信息时
               if (studyPlanImageDTO.getImageNumber() == 1) {
                   studyPlan.setImageOne(filePath);
                   studyPlan.setImageTwo("无");
               } else {
                   studyPlan.setImageTwo(filePath);
                   studyPlan.setImageOne("无");
               }
//           }
       } else {
//           if (studyPlanDay == StudyPlanDayEnum.ACCOMPLISHED) {
//               if (studyPlanImageDTO.getImageNumber() == 1) {
//                   studyPlan.setImageOne(filePath);
//                   studyPlan.setContentOne(studyPlanImageDTO.getContent());
//                   studyPlan.setLinkOne(studyPlanImageDTO.getLink());
//               } else {
//                   studyPlan.setImageTwo(filePath);
//                   studyPlan.setContentTwo(studyPlanImageDTO.getContent());
//                   studyPlan.setLinkTwo(studyPlanImageDTO.getLink());
//               }
//           } else {
               if (studyPlanImageDTO.getImageNumber() == 1) {
                   studyPlan.setImageOne(filePath);
               } else {
                   studyPlan.setImageTwo(filePath);
               }
//           }
       }
    }
}
