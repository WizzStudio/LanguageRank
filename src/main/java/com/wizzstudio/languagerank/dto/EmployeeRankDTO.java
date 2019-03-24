package com.wizzstudio.languagerank.dto;

import com.wizzstudio.languagerank.domain.Language;
import lombok.Data;

@Data
public class EmployeeRankDTO {

    private Language language;

    // 雇主热度指数
    private Double employeeFinalExponent;

    // 语言热度变化趋势(值在LanguageTendEnum类中列出)
    private Integer languageTend;

}
