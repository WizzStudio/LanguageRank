package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wizzstudio.languagerank.VO.WxLogInVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.DAO.*;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserStudyedLanguageDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserTranspondDAO;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.DTO.WxInfoDTO;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, Constant {
    @Autowired
    WxMaService wxService;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserStudyedLanguageDAO userStudyedLanguageDAO;
    @Autowired
    StudyPlanDAO studyPlanDAO;
    @Autowired
    UserTranspondDAO userTranspondDAO;
    @Autowired
    StudyPlanService studyPlanService;
    @Autowired
    AwardDAO awardDAO;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public WxLogInVO userLogin(WxInfoDTO loginData) throws WxErrorException {

        // 通过code获取用户openId与session_key
        WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
        // 通过openId在数据库中寻找是否存在该用户，不存在则写入数据库
        User user = findByOpenId(sessionResult.getOpenid());
        if (user == null) {
            WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionResult.getSessionKey(), loginData.getEncryptedData(), loginData.getIv());
            user = saveUser(sessionResult.getOpenid(), wxUserInfo.getNickName(), wxUserInfo.getAvatarUrl());
        }

        WxLogInVO wxLogInVO = new WxLogInVO();
        wxLogInVO.setOpenId(user.getOpenId());
        wxLogInVO.setUserId(user.getUserId());
        wxLogInVO.setNickName(user.getNickName());
        wxLogInVO.setAvatarUrl(user.getAvatarUrl());

        redisUtil.setUser(user.getUserId(), user);
        return wxLogInVO;
    }

    // 只通过openId新增用户，myLanguage默认为空，studyPlanDay默认为FIRST_DAY
    // 只在login中调用，调用时表示正在注册，isLoginToday设置为true
    @Override
    public User saveUser(String openId, String nickName, String avatarUrl) {
        User user = new User();
        Date date = new Date();
        user.setOpenId(openId);
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
//        user.setStudyPlanDay(StudyPlanDayEnum.NULL);
//        user.setMyLanguage("未加入");
        user.setTotalScore(0);
        user.setTotalWorshipScore(0);
        user.setTotalPunchCardScore(0);
        user.setTodayScore(0);
        user.setTodayPunchCardScore(0);
        user.setTodayWorshipScore(0);
        user.setTotalPunchCardDay(0);
        user.setIsPunchCardToday(false);
        // 默认10点提醒用户打卡
        user.setReminderTime(PunchReminderTimeEnum.TEN);
        user.setIsLogInToday(true);
        user.setIsViewedJoinMyApplet(true);
        user.setLogInTime(date);
        user.setLogInLastTime(date);
//        // 默认优先显示最新的评论
//        user.setCommentDisplayMode(CommentDisplayModeEnum.NEW_COMMENT_PRIORITIZED);

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
//    public StudyPlanDayEnum updateStudyPlanDay(user user) {
//        StudyPlanDayEnum studyPlanDayEnum = StudyPlanDayEnum.getStudyPlanDayByInteger(user.getStudyPlanDay().getStudyPlanDay() + 1);
//        userDAO.updateStudyPlanDay(studyPlanDayEnum, user.getUserId());
//        return studyPlanDayEnum;
//    }
//
//    /**
//     * 将用户正在学的语言与所选语言分开思考，当正在学的语言以前学过时，将该语言的学习进度更新，反之将该语言添加至
//     * 用户学过的语言表；当用户所选语言以前学过时，将该语言以前的进度取出来更新用户信息，反之则将用户信息初始化，并将所选语言添加至UserTranspond表
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateMyLanguage(user user, String chosenLanguage) {
//        // 用户曾经是否学过目前正在学的语言
//        Boolean isStudyedStudyingLanguage = false;
//        // 用户是否学过所选语言
//        Boolean isStudyedChosenLanguage = false;
//
//        StudyPlanDayEnum newStudyPlanDay = StudyPlanDayEnum.FIRST_DAY;
//
//        Integer userId = user.getUserId();
//
//        List<UserStudyedLanguage> userStudyedLanguageList = userStudyedLanguageDAO.findStudyedLanguageByUserId(userId);
//        // 如果用户没有选择过语言，则需单独处理
//        if (!userStudyedLanguageList.isEmpty()) {
//            for (UserStudyedLanguage userStudyedLanguage : userStudyedLanguageList) {
//                String myLanguage = user.getMyLanguage();
//
//                // 如果用户曾经学过目前正在学的语言，则将该语言学习进度更新，同时设置isStudyToday为true
//                if (userStudyedLanguage.getStudyedLanguage().equals(myLanguage)) {
//                    isStudyedStudyingLanguage = true;
//                    userStudyedLanguageDAO.updateStudyPlanDayByLanguageNameAndUserId(user.getStudyPlanDay(),myLanguage, userId);
//                    userStudyedLanguageDAO.updateIsStudyedTodayByLanguageNameAndUserId(myLanguage, userId);
//                    break;
//                }
//            }
//            for (UserStudyedLanguage userStudyedLanguage : userStudyedLanguageList) {
//                // 如果用户曾经选择过所选语言，则将该语言的学习进度更新至user表
//                if (userStudyedLanguage.getStudyedLanguage().equals(chosenLanguage)) {
//                    isStudyedChosenLanguage = true;
//
//                    Integer studyPlanDayEnumByInteger = userStudyedLanguage.getStudyPlanDay().getStudyPlanDay();
//                    Boolean isStudyedToday = userStudyedLanguage.getIsStudyedToday();
//                    // 如果所选语言用户曾经未学完且用户今天没有学过该语言，则将该语言学习进度加1并更新至用户表，同时设置isStudyToday为true
//                    if (!studyPlanDayEnumByInteger.equals(StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay()) && !isStudyedToday) {
//                        studyPlanDayEnumByInteger += 1;
//                        userStudyedLanguageDAO.updateIsStudyedTodayByLanguageNameAndUserId(chosenLanguage, userId);
//                    }
//                    newStudyPlanDay = StudyPlanDayEnum.getStudyPlanDayByInteger(studyPlanDayEnumByInteger);
//                    userDAO.updateStudyPlanDay(newStudyPlanDay, userId);
//                    break;
//                }
//            }
//        }
//        // 如果用户没有学过目前正在学的语言（未加入不算），则将该语言添加至UserStudyedLanguage表
//        if (!isStudyedStudyingLanguage && !"未加入".equals(user.getMyLanguage())) {
//            UserStudyedLanguage userStudyedLanguage = new UserStudyedLanguage();
//            userStudyedLanguage.setStudyedLanguage(user.getMyLanguage());
//            userStudyedLanguage.setStudyPlanDay(user.getStudyPlanDay());
//            userStudyedLanguage.setUserId(user.getUserId());
//            userStudyedLanguage.setIsStudyedToday(true);
//            userStudyedLanguageDAO.save(userStudyedLanguage);
//        }
//        // 更新用户所选语言，必须要在处理完目前正在学的语言的逻辑之后
//        userDAO.updateMyLanguage(chosenLanguage, userId);
//
//        // 如果用户没有选择过所选语言，则将用户的学习进度初始化，并将所选语言添加至UserTranspond表
//        if (!isStudyedChosenLanguage) {
//            userDAO.updateStudyPlanDay(StudyPlanDayEnum.FIRST_DAY, userId);
//            UserTranspond userTranspond = new UserTranspond();
//            userTranspond.setUserId(userId);
//            userTranspond.setLanguageName(chosenLanguage);
//            userTranspond.setIsTranspondTheFirstDay(false);
//            userTranspond.setIsTranspondTheSecondDay(false);
//            userTranspond.setIsTranspondTheThirdDay(false);
//            userTranspond.setIsTranspondTheFourthDay(false);
//            userTranspond.setIsTranspondTheFifthDay(false);
//            userTranspond.setIsTranspondTheSixthDay(false);
//            userTranspond.setIsTranspondTheSeventhDay(false);
//            userTranspondDAO.save(userTranspond);
//        }
//
//        // 修改redis中的数据
//        user.setMyLanguage(chosenLanguage);
//        user.setStudyPlanDay(newStudyPlanDay);
//        redisUtil.setUser(userId, user);
////        System.out.println(user);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateUserTranspondTable(user user, Integer studyPlanDay) {
//        String languageName = user.getMyLanguage();
//        UserTranspond userTranspond = userTranspondDAO.findByLanguageNameAndUserId(languageName, user.getUserId());
//       switch (studyPlanDay) {
//           case 1:
//               userTranspond.setIsTranspondTheFirstDay(true);
//               break;
//           case 2:
//               userTranspond.setIsTranspondTheSecondDay(true);
//               break;
//           case 3:
//               userTranspond.setIsTranspondTheThirdDay(true);
//               break;
//           case 4:
//               userTranspond.setIsTranspondTheFourthDay(true);
//               break;
//           case 5:
//               userTranspond.setIsTranspondTheFifthDay(true);
//               break;
//           case 6:
//               userTranspond.setIsTranspondTheSixthDay(true);
//               break;
//           case 7:
//               userTranspond.setIsTranspondTheSeventhDay(true);
//               break;
//           default: break;
//       }
//    }
//
//    @Override
//    public List<Boolean> getUseTranspond(String languageName, Integer userId) {
//       List<Boolean> userTranspondList = new ArrayList<>();
//        UserTranspond userTranspond = userTranspondDAO.findByLanguageNameAndUserId(languageName, userId);
//        userTranspondList.add(userTranspond.getIsTranspondTheFirstDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheSecondDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheThirdDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheFourthDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheFifthDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheSixthDay());
//        userTranspondList.add(userTranspond.getIsTranspondTheSeventhDay());
//
//        return userTranspondList;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIsLogInToday(Integer userId) {
        userDAO.updateIsLogInToday(userId);
    }

    @Override
    public void updateIsViewedJoinMyApplet(Integer userId) {
        userDAO.updateIsViewedJoinMyApplet(userId);
    }

//    @Override
//    public void updateCommentDisplayMode(CommentDisplayModeEnum commentDisplayMode, Integer userId) {
//        userDAO.updateCommentDisplayMode(commentDisplayMode, userId);
//    }

//    @Override
//    public List<Award> findStudyedLanguageAwardByUserId(user user) {
//        List<UserStudyedLanguage> list =  userStudyedLanguageDAO.findStudyedLanguageByUserId(user.getUserId());
//        if (list.isEmpty()) {
//            return null;
//        }
//
//        List<Award> studyedLanguage = new ArrayList<>();
//        for (UserStudyedLanguage u : list) {
//            // 用户正在学的语言的奖励不在这里处理
//            if (u.getStudyedLanguage().equals(user.getMyLanguage())) {
//                continue;
//            }
//            Award award = awardDAO.findByLanguageName(u.getStudyedLanguage());
//            if (u.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)) {
//                award.setIsViewed(true);
//            } else {
//                award.setIsViewed(false);
//            }
//            studyedLanguage.add(award);
//        }
//
//        return studyedLanguage;
//    }
}
