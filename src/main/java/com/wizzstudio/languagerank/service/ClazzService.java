package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.VO.AllClazzVO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.DTO.*;

import java.util.List;
import java.util.Map;

public interface ClazzService {
    /**
     *  创建班级
     */
    Clazz createClazz(CreateClazzDTO createClazzDTO);

    /**
     *  获取用户已加入班级列表
     */
    List<UserClazzDTO> getUserClazzList(Integer userId);

    /**
     * 返回用户已加入班级的班级号列表，用户加入班级时判断是否曾经加入
     */
    List<Integer> findUserJoinedClazz(Integer userId);

    /**
     * 获取班级列表
     */
    List<AllClazzVO> getAllClazzList();

    /**
     * 加入班级
     */
    void joinClazz(Integer userId, Integer clazzId);

    /**
     * 退出班级
     */
    void quitClazz(Integer userId, Integer clazzId);

    /**
     * 获取班级基本信息
     */
    ClazzMessageVO getClazzMessage(Integer userId, Integer clazzId);

    /**
     *  获取某班级课程简介
     */
    List<String> getClazzStudyPlan(Integer clazzId);

    /**
     * 获取用户在某班级的学习计划
     */
    List<ClazzStudyPlan> getUserClazzStudyPlan(Integer userId, Integer clazzId);

    /**
     * 查询用户在某班级今日学习计划的百度网盘小程序码图片
     */
    String getUserClazzStudyPlanToday(Integer userId, Integer clazzId);

    /**
     * 获取班级特殊成员(班长和好友)的昵称与头像
     */
    Map<String, Object> getSpecialClazzMember(Integer userId, Integer clazzId);

    /**
     *  获取班级成员列表
     */
    Map<String, Object> getClazzMember(Integer clazzId, Integer pageIndex);

    /**
     * 打卡
     */
    void punchCard(Integer userId, Integer clazzId, String formId);

    /**
     * 获取用户在某班级今日打卡信息
     */
    UserPunchCardMessageTodayVO getUserPunchCardMessageToday(Integer userId, Integer clazzId);

    /**
     * 获取某班级勤奋排行榜
     */
    Map<String, Object> getHardWorkingRank(Integer userId, Integer clazzId, Integer pageIndex);

    /**
     * 获取某班级人气排行榜
     */
    Map<String, Object> getPopularityRank(Integer userId, Integer clazzId, Integer pageIndex);
}
