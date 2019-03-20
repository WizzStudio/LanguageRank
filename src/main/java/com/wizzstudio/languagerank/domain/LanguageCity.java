package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/19.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// 雇主需求详情页面第四部分
@Data
@Entity
public class LanguageCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String languageCity;

    @NotNull
    private Integer postNumber;
}
