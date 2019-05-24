package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/5/22.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class GithubPopularProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目语言标签
     */
    @NotNull
    private String projectTag;

    /**
     * 项目图标
     */
    @NotNull
    private String projectImage;

    /**
     * 项目名
     */
    @NotNull
    private String projectName;

    /**
     * 项目简介
     */
    @NotNull
    private String projectBriefIntroduction;

    /**
     * 项目链接
     */
    @NotNull
    private String projectLink;
}
