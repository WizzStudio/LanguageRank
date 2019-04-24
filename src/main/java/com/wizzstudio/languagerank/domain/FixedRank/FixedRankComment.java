package com.wizzstudio.languagerank.domain.FixedRank;

/*
Created by Ben Wen on 2019/4/24.
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
public class FixedRankComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private Integer userId;

    @NotNull
    private String comment;

    @NotNull
    private Date saveTime;
}
