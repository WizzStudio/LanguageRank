package com.wizzstudio.languagerank.VO;


import lombok.Data;

/**
 * 获取语言热度榜接口的返回列表内的对象类型
 */
@Data
public class FinalRankVO {
    private String languageName;

    private String languageSymbol;

    /**
     * 语言热度指数
     */
    private Double fixedFinalExponent;

    /**
     * 语言热度变化趋势(值在LanguageTendEnum类中列出)
     */
    private Integer languageTend;

}
