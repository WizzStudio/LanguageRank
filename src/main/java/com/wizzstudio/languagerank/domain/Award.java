package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/4/8.
*/

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String imageOne;

    @NotNull
    private String contentOne;

    @NotNull
    private String linkOne;

    @NotNull
    private String imageTwo;

    @NotNull
    private String contentTwo;

    @NotNull
    private String linkTwo;

    // 最终奖励才有，表示用户是否完成该语言学习计划，即我的奖励页面该语言的奖励是否上锁
    @Transient
    private Boolean isViewed;
}
