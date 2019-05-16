package com.wizzstudio.languagerank.VO;

import lombok.Data;

/**
 * 获取雇主需求榜接口的返回列表内的对象类型
 */
@Data
public class EmployeeRankVO {
    private String languageName;

    private String languageSymbol;

    /**
     * 雇主热度指数
     */
    private Double employeeFinalExponent;

    /**
     * 语言热度变化趋势(值在LanguageTendEnum类中列出)
     */
    private Integer languageTend;

}
