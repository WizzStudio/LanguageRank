package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/4/6.
*/

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserTranspond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private String languageName;

    private Boolean isTranspondTheFirstDay;
    private Boolean isTranspondTheSecondDay;
    private Boolean isTranspondTheThirdDay;
    private Boolean isTranspondTheFourthDay;
    private Boolean isTranspondTheFifthDay;
    private Boolean isTranspondTheSixthDay;
    private Boolean isTranspondTheSeventhDay;
}
