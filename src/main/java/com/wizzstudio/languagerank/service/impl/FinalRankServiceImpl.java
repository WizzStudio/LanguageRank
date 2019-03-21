package com.wizzstudio.languagerank.service.impl;


import com.wizzstudio.languagerank.dao.FinalRankExponentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalRankServiceImpl {

    @Autowired
    FinalRankExponentDAO finalRankExponentDAO;

    //    TIOBE指数*100*6*0.4+IEEE指数*0.5+谷歌搜索指数/1000*0.1
//    @Override
    public Double findFixedFinalExponent(String languageName){
        return finalRankExponentDAO.findByLanguageName(languageName).getTIOBEExponent()*240
                + finalRankExponentDAO.findByLanguageName(languageName).getIEEEExponent()*0.5
                + finalRankExponentDAO.findByLanguageName(languageName).getGoogleExponent()*0.1;
    }

}
