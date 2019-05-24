package com.wizzstudio.languagerank.domain.user;

/*
Created by Ben Wen on 2019/5/20.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class UserCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    /**
     * 用户收藏的学习计划所属班级
     */
    @NotNull
    private Integer clazzId;

    /**
     * 班级名(为减少一次数据库查询而设计)
     */
    @NotNull
    private String clazzName;

    /**
     * 学习计划天数
     */
    @NotNull
    private Integer studyPlanDay;
}
