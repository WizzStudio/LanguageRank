package com.wizzstudio.languagerank.domain;

/*
Created by Ben Wen on 2019/3/20.
*/

import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserStudyedLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String studyedLanguage;

    @Enumerated(value = EnumType.STRING)
    private StudyPlanDayEnum studyPlanDay;

    private Boolean isStudyedToday;
}
