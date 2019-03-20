package com.wizzstudio.languagerank.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// 雇主需求详情页面第一部分
@Data
@Entity
public class LanguagePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private Integer postSalary;

    // 相关语言岗位
    @NotNull
    private String languagePost;

}
