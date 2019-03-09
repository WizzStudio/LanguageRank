package com.wizzstudio.languagerank.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class LanguagePost {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String languageName;

    // 相关语言岗位
    @NotNull
    private String languagePost;

}
