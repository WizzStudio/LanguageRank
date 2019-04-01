package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wizzstudio.languagerank.dao.StudyPlanDAO;
import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.dao.UserStudyedLanguageDAO;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.domain.UserStudyedLanguage;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{

    @Autowired
    private WxMaService wxService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    UserStudyedLanguageDAO userStudyedLanguageDAO;

    @Autowired
    StudyPlanDAO studyPlanDAO;

//    @Autowired
//    RedisUtil redisUtil;
//
//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    private StudyPlanService studyPlanService;

    @Override
    public WxLogInDTO userLogin(WxInfo loginData) throws WxErrorException {

        // 通过code获取用户openId与session_key
        WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
        // 通过openId在数据库中寻找是否存在该用户，不存在则写入数据库
        User user = findByOpenId(sessionResult.getOpenid());
        if (user == null) {
            user = saveUser(sessionResult.getOpenid());
        }
//        // 以cookie值为key，user对象为value存入redis
//        redisUtil.storeNewUser(cookie, user);

        WxLogInDTO wxLogInDTO = new WxLogInDTO();
        wxLogInDTO.setOpenId(sessionResult.getOpenid());
        wxLogInDTO.setSession_key(sessionResult.getSessionKey());
        wxLogInDTO.setUserId(user.getUserId());

        return wxLogInDTO;
    }

    // 只通过openId新增用户，myLanguage默认为空，studyPlanDay默认为FIRST_DAY
    // 只在login中调用，调用时表示正在注册，isLoginToday设置为true
    @Override
    public User saveUser(String openId) {
        User user = new User();
        user.setOpenId(openId);
        user.setStudyPlanDay(StudyPlanDayEnum.FIRST_DAY);
        user.setMyLanguage("???");
        user.setIsLogInToday(true);
        return userDAO.save(user);
    }

    @Override
    public User findByOpenId(String openid) {
        return userDAO.findByOpenId(openid);
    }

    @Override
    public User findByUserId(Integer userId) {
        return userDAO.findByUserId(userId);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateStudyPlanDay(String cookie) throws NullPointerException{
//        StudyPlan studyPlan = studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(
//                redisTemplate.opsForValue().get(cookie).getMyLanguage(),
//                redisTemplate.opsForValue().get(cookie).getStudyPlanDay());
//        // 如果用户已完成所有学习计划
//        if (studyPlan.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)) {
//            log.info("用户已完成"+ redisTemplate.opsForValue().get(cookie).getMyLanguage() + "所有学习计划");
//        } else {
//            userDAO.updateStudyPlanDay(StudyPlanDayEnum.getStudyPlanDayByInteger
//                            (studyPlan.getStudyPlanDay().getStudyPlanDay() + 1),
//                    redisTemplate.opsForValue().get(cookie).getOpenId());
//        }
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudyPlanDay(User user) {
        Integer studyPlanDayEnumByInteger = user.getStudyPlanDay().getStudyPlanDay() + 1;
        userDAO.updateStudyPlanDay(StudyPlanDayEnum.getStudyPlanDayByInteger(studyPlanDayEnumByInteger), user.getUserId());
        // 如果用户已完成该种语言全部计划的学习，将其加入用户已完成语言表
        if (studyPlanDayEnumByInteger == 8) {
            UserStudyedLanguage u = new UserStudyedLanguage();
            u.setUserId(user.getUserId());
            u.setStudyedLanguage(user.getMyLanguage());
            userStudyedLanguageDAO.save(u);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetStudyPlanDay(Integer userId) {
        userDAO.updateStudyPlanDay(StudyPlanDayEnum.FIRST_DAY, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMyLanguage(Integer userId, String myLanguage) {
        userDAO.updateMyLanguage(myLanguage, userId);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateAllIsLogInToDay() {
        userDAO.updateAllIsLogInToday();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIsLogInToday(Integer userId) {
        userDAO.updateIsLogInToday(userId);
    }

    @Override
    public List<StudyPlan> findStudyedLanguageByUserId(Integer userId) {
        List<UserStudyedLanguage> list =  userStudyedLanguageDAO.findStudyedLanguageByUserId(userId);
        List<StudyPlan> studyedLanguage = new ArrayList<>();
        for (UserStudyedLanguage u : list) {
            studyedLanguage.add(studyPlanDAO.findByLanguageNameAndStudyPlanDay(u.getStudyedLanguage(), StudyPlanDayEnum.ACCOMPLISHED));
        }

        return studyedLanguage;
    }
}
