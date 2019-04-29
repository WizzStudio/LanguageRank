package com.wizzstudio.languagerank.domain.Clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class UserClazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer clazzId;

    @NotNull
    private Date joinedTime;

    /**
     * 用户在该班级的学习天数
     */
    @NotNull
    private Integer allStudyPlanDay;

    /**
     * 用户连续打卡天数
     */
    @NotNull
    private Integer uninterruptedStudyPlanDay;
}
