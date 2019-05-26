package com.wizzstudio.languagerank.domain.employeerank;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 雇主需求详情页面第一部分
 */
@Data
@Entity
public class LanguagePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    // 公司名
    @NotNull
    private String companyName;

    // 岗位薪资（区间）
    @NotNull
    private String postSalary;

    // 岗位需求量
    @NotNull
    private Integer postNumber;

    // 该公司相关语言岗位
    @NotNull
    private String languagePost;

}
