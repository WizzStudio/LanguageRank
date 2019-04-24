package com.wizzstudio.languagerank.domain.EmployeeRank;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

// 雇主需求详情页面第二部分
@Entity
@Data
public class CompanySalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String companyName;

    // 平均薪资
    private Integer companyOrdSalary;

    // 最高薪资
    private Integer companyMaxSalary;

    // 薪资最高的岗位
    private String companyMaxSalaryPost;

    // 最低薪资
    private Integer companyMinSalary;

    // 薪资最低的岗位
    private String companyMinSalaryPost;
}
