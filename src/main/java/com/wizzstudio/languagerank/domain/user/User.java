package com.wizzstudio.languagerank.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.clazz.ClazzComment;
import com.wizzstudio.languagerank.domain.employeerank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.fixedrank.FixedRankComment;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 用户可用积分
     */
    @NotNull
    private Integer availableScore;

    /**
     * 用户通过打卡获得的总积分
     */
    @NotNull
    private Integer totalPunchCardScore;

    /**
     * 用户通过被膜拜获得的总积分
     */
    @NotNull
    private Integer totalWorshipScore;

    /**
     * 用户今日获得总积分
     */
    @NotNull
    private Integer todayScore;

    /**
     * 用户今日打卡获得积分
     */
    @NotNull
    private Integer todayPunchCardScore;

    /**
     * 用户今日被膜拜获得积分
     */
    @NotNull
    private Integer todayWorshipScore;

    /**
     * 用户打卡总天数
     */
    @NotNull
    private Integer totalPunchCardDay;

    /**
     * 用户今日是否打过卡
     */
    @NotNull
    private Boolean isPunchCardToday;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date logInTime;

    /**
     * 用户上次登录时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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


    @NotNull
    private Boolean isLogInToday;
}
