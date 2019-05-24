package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/5/18.
*/

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class AwardStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer awardId;

    /**
     * 奖励文字介绍
     */
    @NotNull
    private String content;

    /**
     * 奖品图片
     */
    @NotNull
    private String image;

    /**
     * 奖品链接
     */
    @NotNull
    private String link;

    /**
     * 奖品所需积分
     */
    @NotNull
    private Integer score;

    /**
     * 奖品是否被兑换
     */
    @Transient
    private Boolean isExchanged;
}
