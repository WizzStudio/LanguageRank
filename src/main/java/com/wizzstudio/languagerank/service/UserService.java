package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import me.chanjar.weixin.common.error.WxErrorException;

public interface UserService {

    /**
     * 用户登录
     * @param loginData 用户登录临时凭证code
     * @param cookie 用户登录时用cookie标识
     * @return 用户openId与session_key
     */
    WxLogInDTO userLogin(WxInfo loginData, String cookie) throws WxErrorException;

    /**
     * 新增用户信息
     *
     * @param openId 用户openId
     */
    User saveUser(String openId);

    /**
     * 通过openId获取用户信息
     *
     * @param openId 用户openId
     */
    User findByOpenId(String openId);

    /**
     * 通过openId获取用户信息
     *
     * @param userId 用户userId
     */
    User findByUserId(Integer userId);

    /**
     * 当用户已完成今天学习计划(已登录过)后更新studyPlanDay
     */
    void updateStudyPlanDay();

    /**
     * 用户更换所学语言后重置用户七日学习计划
     * @param user
     */
    void resetStudyPlanDay(User user);

    /**
     * 更新用户个人主页上的语言
     * @param user
     * @param myLanguage
     */
    void updateMyLanguage(User user, String myLanguage);

    /**
     * 每天零点更新用户今天是否登录
     */
    void updateAllIsLogInToDay();

    /**
     * 更新某用户今日登录情况
     */
    void updateIsLogInToday(Integer userId);
}
