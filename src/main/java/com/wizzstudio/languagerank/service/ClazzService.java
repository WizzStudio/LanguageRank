package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.DTO.CreateClazzDTO;
import com.wizzstudio.languagerank.DTO.UserClazzDTO;
import com.wizzstudio.languagerank.VO.AllClazzVO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.VO.CollectionVO;
import com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;

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
     * 刷新用户已加入班级列表
     */
    List<Integer> refreshUserClazzList(Integer userId);

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
     *  获取某班级课程详情
     */
    Map<String, Object> getClazzStudyPlanBriefIntroduction(Integer clazzId);

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
     * 获取用户打卡提醒时间
     */
    Integer getPunchCardReminderTime(Integer userId);

    /**
     * 更新用户打卡提醒时间
     */
    void updatePunchCardReminderTime(Integer userId, PunchReminderTimeEnum punchReminderTime);

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

    /**
     * 膜拜
     */
    Boolean worship(Integer worshippingUser,Integer worshippedUser);

    /**
     * 收藏
     */
    Boolean collect(Integer userId,Integer clazzId, String clazzName, Integer studyPlanDay);

    /**
     * 取消收藏
     */
    void cancelCollection(Integer userId,Integer clazzId,Integer studyPlanDay);

    /**
     * 获取我的收藏
     */
    List<CollectionVO> getCollection(Integer userId);

    /**
     * 获取微信小程序码
     */
    String getQrCode(Integer userId,Integer clazzId) throws Exception;
}
