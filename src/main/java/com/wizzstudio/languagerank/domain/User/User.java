package com.wizzstudio.languagerank.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.Clazz.ClazzComment;
import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.FixedRank.FixedRankComment;
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

    @NotNull
    private String nickName;

    @NotNull
    private String avatarUrl;

    // 个人主页上学习的语言
    private String myLanguage;

    // 即将进行第几天的计划
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StudyPlanDayEnum studyPlanDay;

    @NotNull
    private Integer totalScore;

    @NotNull
    private Integer todayScore;

    @NotNull
    private Boolean isLogInToday;

    @NotNull
    private Boolean isViewedJoinMyApplet;

    @NotNull
    private Date logInTime;

    @NotNull
    private Date logInLastTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "user")
    List<EmployeeRankComment> employeeRankCommentList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "user")
    List<FixedRankComment> fixedRankCommentList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "user")
    List<ClazzComment> clazzCommentList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "userList")
    @JoinTable(
            // 连接表的表名
            name = "UserClazz",
            // 当前实体对应表的主键列
            joinColumns = {@JoinColumn(name = "userId")},
            // 当前实体的关联实体对应表的主键列
            inverseJoinColumns = {@JoinColumn(name = "clazzId")}
    )
    private List<Clazz> clazzList;

//    // 用户评论显示顺序
//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    private CommentDisplayModeEnum commentDisplayMode;
}
