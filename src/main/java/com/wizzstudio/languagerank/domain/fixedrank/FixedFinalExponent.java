package com.wizzstudio.languagerank.domain.fixedrank;

/*
Created by Ben Wen on 2019/3/18.
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

// 语言热度榜最终指数表
@Data
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class FixedFinalExponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private Double fixedFinalExponent;

    /**
     * 后台修改指数，可以为null
     */
    private Double fArtificialExponent;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;
}
