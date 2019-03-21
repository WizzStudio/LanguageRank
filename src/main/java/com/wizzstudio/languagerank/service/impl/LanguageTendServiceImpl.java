package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.LanguageTendDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.dto.FinalRankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageTendServiceImpl {

    @Autowired
    LanguageTendDAO languageTendDAO;

//    @Override
    public void findLanguageTendNumber(String languageName){
        List<FixedFinalExponent> fixedFinalExponents = languageTendDAO.findByLanguageName(languageName);
        int size = fixedFinalExponents.size();
        int size2 = size - 1;
        Double languageTendNumber = fixedFinalExponents.get(size).getFixedFinalExponent()
                - fixedFinalExponents.get(size2).getFixedFinalExponent();
//        if (languageTendNumber > 0)
//
//        if (languageTendNumber < 0)
//
//        else

    }

}
