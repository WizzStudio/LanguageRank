package com.wizzstudio.languagerank.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class EmployeeRank {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String languageName;

    // 语言图标的url
    @NotNull
    private String languageSymbol;

    // 语言热度指数
    @NotNull
    private Integer languageNumber;

}
