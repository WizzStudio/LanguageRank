package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/18.
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

// 语言热度榜最终指数表
@Data
@Entity
public class FixedFinalExponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String LanguageName;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    @NotNull
    private Double FixedFinalExponent;

    //    后台修改指数，可以为null
    private Double FArtificialExponent;

}
