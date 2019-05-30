package com.wizzstudio.languagerank.domain.clazz;

/*
Created by Ben Wen on 2019/4/25.
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class UserClazz implements Serializable {
    private static final long serialVersionUID = 6437690844117084236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer clazzId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    /**
     * 用户今天是否学习
     */
    @NotNull
    private Boolean isStudyToday;

    /**
     * 用户打卡时间(从未打卡时打卡时间为空)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date studyTime;
}
