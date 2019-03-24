package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.domain.FixedFinalExponent;

public interface FinalRankService {

    /**
     * 计算语言热度榜的最终指数
     * @param languageName
     * @return fixedFinalExponent
     */
    Double getFixedFinalExponent(String languageName);

    /**
     * 保存语言热度榜的最终指数
     * @param languageName
     * @return fixedRankLanguageNameDAO.save(fixedFinalExponent)
     */
    FixedFinalExponent saveExponent(String languageName);

}
