package com.wizzstudio.languagerank.domain.User;

import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
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

    @NotNull
    private Boolean isLogInToday;

    @NotNull
    private Boolean isViewedJoinMyApplet;

    @NotNull
    private Date logInTime;

    @NotNull
    private Date logInLastTime;

//    // 用户评论显示顺序
//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    private CommentDisplayModeEnum commentDisplayMode;
}
