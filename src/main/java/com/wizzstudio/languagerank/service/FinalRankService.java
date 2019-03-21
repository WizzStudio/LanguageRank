package com.wizzstudio.languagerank.service;

public interface FinalRankService {

    //    TIOBE指数*100*6*0.4+IEEE指数*0.5+百度搜索指数/1000*0.1
    Double findFixedFinalExponent(String languageName);

}
