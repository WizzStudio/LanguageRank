package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.FinalRankExponentDAO;
import com.wizzstudio.languagerank.service.FinalRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalRankServiceImpl implements FinalRankService {

    @Autowired
    FinalRankExponentDAO finalRankExponentDAO;

    //    TIOBE指数*100*6*0.4+IEEE指数*0.5+百度指数/100*0.1
    @Override
    public Double findFixedFinalExponent(String languageName){
        return finalRankExponentDAO.findByLanguageName(languageName).getTIOBEExponent()*240
                + finalRankExponentDAO.findByLanguageName(languageName).getIEEEExponent()*0.5
                + finalRankExponentDAO.findByLanguageName(languageName).getBaiduExponent()*0.001;
    }

}
