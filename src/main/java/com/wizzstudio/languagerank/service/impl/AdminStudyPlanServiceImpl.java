package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.dto.AdminStudyPlanDTO;
import com.wizzstudio.languagerank.service.AdminStudyPlanService;
import com.wizzstudio.languagerank.service.LanguageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminStudyPlanServiceImpl implements AdminStudyPlanService {

    @Autowired
    LanguageCountService languageCountService;
    @Autowired
    EmployeeRankDAO employeeRankDAO;

    private List<AdminStudyPlanDTO> adminStudyPlanDTOList = new ArrayList<>();

    @Override
    public List<AdminStudyPlanDTO> getAdminStudyPlan() {

        List<EmployeeRank> employeeRanks = employeeRankDAO.findTopTenLanguage();
        for (EmployeeRank employeeRank : employeeRanks){
            AdminStudyPlanDTO adminStudyPlanDTO  =  new AdminStudyPlanDTO();
            String languageNa = employeeRank.getLanguageName();

            adminStudyPlanDTO.setLanguageName(languageNa);
            adminStudyPlanDTO.setIncreaseNumber(languageCountService.findJoinedTodayByLanguage(languageNa));
            adminStudyPlanDTO.setNumber(languageCountService.findJoinedNumberByLanguage(languageNa));

            adminStudyPlanDTOList.add(adminStudyPlanDTO);
        }

        return adminStudyPlanDTOList;
    }
}
