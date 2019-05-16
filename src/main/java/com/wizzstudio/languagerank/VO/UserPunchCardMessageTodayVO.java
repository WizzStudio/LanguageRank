package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/15.
*/

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户在某班级今日打卡信息接口的返回类型
 */
@Data
@NoArgsConstructor
public class UserPunchCardMessageTodayVO {
    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户在该班级连续打卡天数
     */
    private Integer uninterruptedStudyPlanDay;

    /**
     * 用户总积分
     */
    private Integer totalScore;

    /**
     * 用户总打卡天数
     */
    private Integer totalPunchCardDay;

    /**
     * 用户今日获得的总积分
     */
    private Integer todayScore;

    public UserPunchCardMessageTodayVO(String avatarUrl, Integer totalScore, Integer totalPunchCardDay, Integer todayScore) {
        this.avatarUrl = avatarUrl;
        this.totalScore = totalScore;
        this.totalPunchCardDay = totalPunchCardDay;
        this.todayScore = todayScore;
    }
}
