package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{

    @Autowired
    private WxMaService wxService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private StudyPlanService studyPlanService;

    @Override
    public WxLogInDTO userLogin(WxInfo loginData, String cookie) throws WxErrorException {

        // 通过code获取用户openId与session_key
        WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
        // 通过openId在数据库中寻找是否存在该用户，不存在则写入数据库
        User user = findByOpenId(sessionResult.getOpenid());
        if (user == null) {
            user = saveUser(sessionResult.getOpenid());
        }
        // 以cookie值为key，user对象为value存入redis
        redisUtil.storeNewUser(cookie, user);

        WxLogInDTO wxLogInDTO = new WxLogInDTO();
        wxLogInDTO.setOpenId(sessionResult.getOpenid());
        wxLogInDTO.setSession_key(sessionResult.getSessionKey());

        return wxLogInDTO;
    }

    // 只通过openId新增用户，myLanguage默认为空，studyPlanDay默认为FIRST_DAY
    @Override
    public User saveUser(String openId) {
        User user = new User();
        user.setOpenId(openId);
        user.setStudyPlanDay(StudyPlanDayEnum.FIRST_DAY);
        return userDAO.save(user);
    }

    @Override
    public User findByOpenId(String openid) {
        return userDAO.findByOpenId(openid);
    }

    // 还没有使用过
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudyPlanDay(String cookie) throws NullPointerException{
        StudyPlan studyPlan = studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(
                redisTemplate.opsForValue().get(cookie).getMyLanguage(),
                redisTemplate.opsForValue().get(cookie).getStudyPlanDay());
        // 如果用户已完成所有学习计划
        if (studyPlan.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)) {
            log.info("用户已完成"+ redisTemplate.opsForValue().get(cookie).getMyLanguage() + "所有学习计划");
        } else {
            userDAO.updateStudyPlanDay(StudyPlanDayEnum.getStudyPlanDayByInteger
                            (studyPlan.getStudyPlanDay().getStudyPlanDay() + 1),
                    redisTemplate.opsForValue().get(cookie).getOpenId());
        }
    }

    @Override
    public void resetStudyPlanDay(User user) {
        userDAO.updateStudyPlanDay(StudyPlanDayEnum.FIRST_DAY, user.getOpenId());
    }

    @Override
    public void updateMyLanguage(User user, String myLanguage) {
        userDAO.updateMyLanguage(myLanguage, user.getOpenId());
    }
}
