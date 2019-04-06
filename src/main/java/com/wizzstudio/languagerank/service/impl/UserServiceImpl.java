package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wizzstudio.languagerank.dao.StudyPlanDAO;
import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.dao.UserStudyedLanguageDAO;
import com.wizzstudio.languagerank.dao.UserTranspondDAO;
import com.wizzstudio.languagerank.domain.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    UserTranspondDAO userTranspondDAO;

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
//        // 如果用户已完成该种语言全部计划的学习，将其加入用户已完成语言表
//        if (studyPlanDayEnumByInteger == 8) {
//            UserStudyedLanguage u = new UserStudyedLanguage();
//            u.setUserId(user.getUserId());
//            u.setStudyedLanguage(user.getMyLanguage());
//            userStudyedLanguageDAO.save(u);
//        }
    }

    /**
     * 将用户正在学的语言与所选语言分开思考，当正在学的语言以前学过时，将该语言的学习进度更新，反之将该语言添加至
     * 用户学过的语言表；当用户所选语言以前学过时，将该语言以前的进度取出来更新用户信息，反之则将用户信息初始化，并将该语言添加至UserTranspond表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMyLanguage(User user, String chosenLanguage) {
        List<UserStudyedLanguage> userStudyedLanguageList = userStudyedLanguageDAO.findStudyedLanguageByUserId(user.getUserId());

        // 用户曾经是否学过目前正在学的语言
        Boolean isStudyedStudyingLanguage = false;
        // 用户是否学过所选语言
        Boolean isStudyedChosenLanguage = false;

        for (UserStudyedLanguage userStudyedLanguage : userStudyedLanguageList){
            String myLanguage = user.getMyLanguage();
            if (userStudyedLanguage.getStudyedLanguage().equals(myLanguage)) {
                isStudyedStudyingLanguage = true;
                // 如果用户曾经学过目前正在学的语言，则将该语言学习进度更新
                userStudyedLanguageDAO.updateStudyedLanguageStudyPlanDay(user.getStudyPlanDay(), user.getMyLanguage());
                break;
            }
        }
        // 如果用户没有学过目前正在学的语言，则将该语言添加至UserStudyedLanguage表
        if (!isStudyedStudyingLanguage) {
            UserStudyedLanguage userStudyedLanguage = new UserStudyedLanguage();
            userStudyedLanguage.setStudyedLanguage(user.getMyLanguage());
            userStudyedLanguage.setStudyPlanDay(user.getStudyPlanDay());
            userStudyedLanguage.setUserId(user.getUserId());
            userStudyedLanguageDAO.save(userStudyedLanguage);
        }
        // 更新用户所选语言
        userDAO.updateMyLanguage(chosenLanguage, user.getUserId());



        for (UserStudyedLanguage userStudyedLanguage : userStudyedLanguageList) {
            // 如果用户曾经选择过所选语言，则将该语言的学习进度更新至user表
            if (userStudyedLanguage.getStudyedLanguage().equals(chosenLanguage)) {
                isStudyedChosenLanguage = true;
                userDAO.updateStudyPlanDay(userStudyedLanguage.getStudyPlanDay(), user.getUserId());
                break;
            }
        }
        // 如果用户没有选择过所选语言，则将用户的学习进度初始化，并将该语言添加至UserTranspond表
        if (!isStudyedChosenLanguage) {
            userDAO.updateStudyPlanDay(StudyPlanDayEnum.FIRST_DAY, user.getUserId());

            UserTranspond userTranspond = new UserTranspond();
            userTranspond.setUserId(user.getUserId());
            userTranspond.setLanguageName(chosenLanguage);
            userTranspond.setIsTranspondTheFirstDay(false);
            userTranspond.setIsTranspondTheSecondDay(false);
            userTranspond.setIsTranspondTheThirdDay(false);
            userTranspond.setIsTranspondTheFourthDay(false);
            userTranspond.setIsTranspondTheFifthDay(false);
            userTranspond.setIsTranspondTheSixthDay(false);
            userTranspond.setIsTranspondTheSeventhDay(false);
            userTranspondDAO.save(userTranspond);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserTranspondTable(User user, Integer studyPlanDay) {
        String languageName = user.getMyLanguage();
        UserTranspond userTranspond = userTranspondDAO.findByLanguageName(languageName);
       switch (studyPlanDay) {
           case 1:
               userTranspond.setIsTranspondTheFirstDay(true);
               break;
           case 2:
               userTranspond.setIsTranspondTheSecondDay(true);
               break;
           case 3:
               userTranspond.setIsTranspondTheThirdDay(true);
               break;
           case 4:
               userTranspond.setIsTranspondTheFourthDay(true);
               break;
           case 5:
               userTranspond.setIsTranspondTheFifthDay(true);
               break;
           case 6:
               userTranspond.setIsTranspondTheSixthDay(true);
               break;
           case 7:
               userTranspond.setIsTranspondTheSeventhDay(true);
               break;
       }
    }

    @Override
    public Map<Integer, Boolean> getUseTranspond(String languageName) {
        Map<Integer, Boolean> userTranspondMap = new HashMap<>();
        UserTranspond userTranspond = userTranspondDAO.findByLanguageName(languageName);
        userTranspondMap.put(1, userTranspond.getIsTranspondTheFirstDay());
        userTranspondMap.put(2, userTranspond.getIsTranspondTheSecondDay());
        userTranspondMap.put(3, userTranspond.getIsTranspondTheThirdDay());
        userTranspondMap.put(4, userTranspond.getIsTranspondTheFourthDay());
        userTranspondMap.put(5, userTranspond.getIsTranspondTheFifthDay());
        userTranspondMap.put(6, userTranspond.getIsTranspondTheSixthDay());
        userTranspondMap.put(7, userTranspond.getIsTranspondTheSeventhDay());

        return userTranspondMap;
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
    public List<StudyPlan> findStudyedLanguageByUserId(User user) {
        List<UserStudyedLanguage> list =  userStudyedLanguageDAO.findStudyedLanguageByUserId(user.getUserId());
        List<StudyPlan> studyedLanguage = new ArrayList<>();
        for (UserStudyedLanguage u : list) {
            // 用户正在学的语言的奖励不在这里处理
            if (u.getStudyedLanguage().equals(user.getMyLanguage())) {
                continue;
            }
            StudyPlan studyPlan = studyPlanDAO.findByLanguageNameAndStudyPlanDay(u.getStudyedLanguage(), StudyPlanDayEnum.ACCOMPLISHED);
            if (u.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)) {
                studyPlan.setIsViewed(true);
            } else {
                studyPlan.setIsViewed(false);
            }
            studyedLanguage.add(studyPlan);
        }

        return studyedLanguage;
    }
}
