package com.wizzstudio.languagerank.domain;

import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 6034578093269769052L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    private String openId;

    // 个人主页上学习的语言
    private String myLanguage;

    // 即将进行第几天的计划
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StudyPlanDayEnum studyPlanDay;

//    @Transient
    private Boolean isLogInToday;
}
