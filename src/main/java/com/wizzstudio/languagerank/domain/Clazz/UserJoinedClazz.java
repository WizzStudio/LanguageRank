package com.wizzstudio.languagerank.domain.Clazz;

/*
Created by Ben Wen on 2019/4/26.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserJoinedClazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer joinedClazzId;

    @NotNull
    private Integer studyPlanDay;

    @NotNull
    private Boolean isJoinedToday;
}
