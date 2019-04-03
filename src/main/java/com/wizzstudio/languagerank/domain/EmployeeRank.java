package com.wizzstudio.languagerank.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Double employeeFinalExponent;

//    后台修改指数，可以为null
    private Double eArtificialExponent;

    // 更新时间
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;
}
