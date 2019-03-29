package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.domain.FixedRank;
import com.wizzstudio.languagerank.domain.Language;
import com.wizzstudio.languagerank.dto.FinalRankDTO;
import com.wizzstudio.languagerank.service.FinalRankService;
import com.wizzstudio.languagerank.service.FixedRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FixedRankServiceImpl implements FixedRankService {

    @Autowired
    FinalRankService finalRankService;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    FixedFinalExponentDAO fixedFinalExponentDAO;
    @Autowired
    LanguageDAO languageDAO;

    private List<FinalRankDTO> finalRankDTOList = new ArrayList<>();

    @Override
    public List<FinalRankDTO> getFinalRank() {

        List<FixedFinalExponent> fixedFinalExponents = fixedFinalExponentDAO.languageFixedRank();
        for (FixedFinalExponent fixedFinalExponent : fixedFinalExponents){

            FinalRankDTO finalRankDTO = new FinalRankDTO();
//            获取前十的语言名称
            String languageName = fixedFinalExponent.getLanguageName();

//            语言热度榜四个字段
            finalRankDTO.setLanguageName(languageName);
            finalRankDTO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
            finalRankDTO.setFixedFinalExponent(finalRankService.getFixedFinalExponent(languageName));
            finalRankDTO.setLanguageTend(languageTendService.findFixedLanguageTendNumber(languageName));

            finalRankDTOList.add(finalRankDTO);
        }
        return finalRankDTOList;
    }
}
