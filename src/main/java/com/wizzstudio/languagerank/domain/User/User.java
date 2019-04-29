package com.wizzstudio.languagerank.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.Clazz.ClazzComment;
import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.FixedRank.FixedRankComment;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
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

    /**
     * 推送模板消息的Id，用户加入任意班级前为空
     */
    private String formId;

    /**
     * 用户昵称
     */
    @NotNull
    private String nickName;

    /**
     * 用户头像
     */
    @NotNull
    private String avatarUrl;

    /**
     * 用户总积分
     */
    @NotNull
    private Integer totalScore;

    /**
     * 用户今日获得积分
     */
    @NotNull
    private Integer todayScore;

    /**
     * 用户打卡总天数
     */
    @NotNull
    private Integer totalPunch;

    /**
     * 用户被膜拜次数
     */
    @NotNull
    private Integer worship;

    /**
     * 用户打卡提醒时间
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PunchReminderTimeEnum reminderTime;

    /**
     * 是否显示加入小程序弹窗
     */
    @NotNull
    private Boolean isViewedJoinMyApplet;

    /**
     * 用户注册时间
     */
    @NotNull
    private Date logInTime;

    /**
     * 用户上次登录时间
     */
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


    //    @NotNull
//    private String myLanguage;

//    /**
//     *  即将进行第几天的计划
//     */
//    @NotNull
//    @Enumerated(value = EnumType.STRING)
//    private StudyPlanDayEnum studyPlanDay;


//    @NotNull
//    private Boolean isLogInToday;
}
