package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.dto.AdminStudyPlanDTO;
import com.wizzstudio.languagerank.service.AdminStudyPlanService;
import com.wizzstudio.languagerank.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminStudyPlanServiceImpl implements AdminStudyPlanService {

    @Autowired
    LanguageService languageService;
    @Autowired
    EmployeeRankLanguageNameDAO employeeRankLanguageNameDAO;
    List<AdminStudyPlanDTO> adminStudyPlanDTOS = new ArrayList<>();


    @Override
    public List<AdminStudyPlanDTO> getAdminStudyPlan() {

        List<EmployeeRank> employeeRanks = employeeRankLanguageNameDAO.languageEmployeeRank();
        for (EmployeeRank employeeRank : employeeRanks){
            AdminStudyPlanDTO adminStudyPlanDTO  =  new AdminStudyPlanDTO();
            String languageNa = employeeRank.getLanguageName();

            adminStudyPlanDTO.setLanguageName(languageNa);
            adminStudyPlanDTO.setIncreasenumber(languageService.findJoinedTodayByLanguage(languageNa));
            adminStudyPlanDTO.setNumber(languageService.findJoinedNumberByLanguage(languageNa));

            adminStudyPlanDTOS.add(adminStudyPlanDTO);
        }

        return adminStudyPlanDTOS;
    }
}
