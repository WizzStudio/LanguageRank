package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.LanguageTendDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.enums.LanguageTendEnum;
import com.wizzstudio.languagerank.service.LanguageTendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageTendServiceImpl implements LanguageTendService {

    @Autowired
    LanguageTendDAO languageTendDAO;

    @Override
    public Integer findLanguageTendNumber(String languageName){

        List<FixedFinalExponent> fixedFinalExponents = languageTendDAO.findByLanguageName(languageName);
        Double languageTendNumber = fixedFinalExponents.get(1).getFixedFinalExponent()
                - fixedFinalExponents.get(2).getFixedFinalExponent();

        if (languageTendNumber > 0){
            return LanguageTendEnum.INCREASE.getLanguageTend();
        }else if (languageTendNumber < 0){
            return LanguageTendEnum.DECREASE.getLanguageTend();
        }else
            return LanguageTendEnum.NO_CHANGE.getLanguageTend();
    }
}
