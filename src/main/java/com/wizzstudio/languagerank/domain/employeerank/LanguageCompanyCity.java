package com.wizzstudio.languagerank.domain.employeerank;

/*
Created by Ben Wen on 2019/4/7.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// 爬虫所需临时数据表，不需要后端进行操作
@Data
@Entity
public class LanguageCompanyCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String companyName;

    @NotNull
    private String cityName;

    @NotNull
    private Integer languagePostNumber;
}
