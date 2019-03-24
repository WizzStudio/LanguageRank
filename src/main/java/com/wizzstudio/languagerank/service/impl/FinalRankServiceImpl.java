package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.fixedrankDAO.FinalRankExponentDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.service.FinalRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FinalRankServiceImpl implements FinalRankService {

    @Autowired
    FinalRankExponentDAO finalRankExponentDAO;
    @Autowired
    FixedFinalExponentDAO fixedRankLanguageNameDAO;

    double exponent = 0.0;

    //    TIOBE指数*100*6*0.4+IEEE指数*0.5+百度指数/100*0.1
    @Override
    public Double getFixedFinalExponent(String languageName){

        exponent = finalRankExponentDAO.findByLanguageName(languageName).getTIOBEExponent()*240
                + finalRankExponentDAO.findByLanguageName(languageName).getIEEEExponent()*0.5
                + finalRankExponentDAO.findByLanguageName(languageName).getBaiduExponent()*0.001;;
        return exponent;
    }

    public FixedFinalExponent saveExponent(String languageName){

        Date time = new Date();

        FixedFinalExponent fixedFinalExponent = new FixedFinalExponent();
        fixedFinalExponent.setLanguageName(languageName);
        fixedFinalExponent.setFixedFinalExponent(exponent);
        fixedFinalExponent.setUpdateTime(time);

        return fixedRankLanguageNameDAO.save(fixedFinalExponent);
    }


}
