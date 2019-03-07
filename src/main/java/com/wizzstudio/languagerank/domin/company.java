package com.wizzstudio.languagerank.domin;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class company {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String companyName;

//      平均薪资
    private Integer companyOrdSalary;
//      最高薪资
    private Integer companyMaxSalary;
//      最低薪资
    private Integer companyMinSalary;
//      岗位人数
    private Integer companyPostNumber;
//      薪资最高的岗位
    private String companyMaxSalaryPost;
//      薪资最低的岗位
    private String companyMinSalaryPost;

}
