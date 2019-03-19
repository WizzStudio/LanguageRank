package com.wizzstudio.languagerank.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

// 语言热度榜
// 该表由爬虫爬取，因数据更新频率不同，第一版小程序暂不保存过期数据
@Entity
@Data
public class FixedRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private Double TIOBEExponent;

    @NotNull
    private Double IEEEExponent;

    @NotNull
    private Double GoogleExponent;
}
