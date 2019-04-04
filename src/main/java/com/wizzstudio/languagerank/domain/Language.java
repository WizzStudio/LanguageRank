package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/12.
*/

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String languageName;

    @NotNull
    private String languageSymbol;

    // 语言难度
    @NotNull
    private Integer languageDifficultyIndex;

    // 语言发明时间
    @NotNull
    private Integer languageBeginTime;

    // 语言发展简史
    @Column(columnDefinition="TEXT",nullable=false)
    private String languageDevelopmentHistory;
}
