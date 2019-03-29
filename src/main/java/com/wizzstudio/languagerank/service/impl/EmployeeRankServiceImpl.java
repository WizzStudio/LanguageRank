package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.domain.Language;
import com.wizzstudio.languagerank.dto.EmployeeRankDTO;
import com.wizzstudio.languagerank.dto.FinalRankDTO;
import com.wizzstudio.languagerank.service.EmployeeFinalExponentService;
import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeRankServiceImpl implements EmployeeRankService {

    @Autowired
    EmployeeFinalExponentService employeeFinalExponentService;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    EmployeeRankLanguageNameDAO employeeRankLanguageNameDAO;
    @Autowired
    LanguageDAO languageDAO;

    private List<EmployeeRankDTO> employeeRankDTOList = new ArrayList<>();

    @Override
    public List<EmployeeRankDTO> getEmployeeRank() {

        List<EmployeeRank> employeeRanks = employeeRankLanguageNameDAO.languageEmployeeRank();
        for (EmployeeRank employeeRank : employeeRanks){

            EmployeeRankDTO employeeRankDTO = new EmployeeRankDTO();
//            获取前十的语言名称
            String languageName = employeeRank.getLanguageName();

//            雇主需求榜四个字段
            employeeRankDTO.setLanguageName(languageName);
            employeeRankDTO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
            employeeRankDTO.setLanguageTend(languageTendService.findEmployeeLanguageTendNumber(languageName));
            employeeRankDTO.setEmployeeFinalExponent(employeeFinalExponentService.getEmployeeFinalExponent(languageName));

            employeeRankDTOList.add(employeeRankDTO);
        }
        return employeeRankDTOList;
    }

}
