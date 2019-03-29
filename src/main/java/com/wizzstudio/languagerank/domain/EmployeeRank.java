package com.wizzstudio.languagerank.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

// 雇主需求榜
@Entity
@Data
public class EmployeeRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    // 薪资指数
    @NotNull
    private Double salaryExponent;

    // 语言需求量指数
    @NotNull
    private Double languagePostExponent;

    // 城市需求量指数
    @NotNull
    private Double cityPostExponent;

    // 最终指数
    @NotNull
    private Double EmployeeFinalExponent;

//    后台修改指数，可以为null
    private Double EArtificialExponent;

    // 更新时间
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;
}
