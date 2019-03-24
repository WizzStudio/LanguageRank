package com.wizzstudio.languagerank.service;

public interface FinalRankService {

    /**
     * 计算语言热度榜的最终指数
     * @param languageName
     * @return fixedFinalExponent
     */
    Double getFixedFinalExponent(String languageName);

}
