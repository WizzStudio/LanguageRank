package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.wizzstudio.languagerank.DAO.userDAO.UserExchangedAwardDAO;
import com.wizzstudio.languagerank.VO.UserRelationshipRankVO;
import com.wizzstudio.languagerank.VO.WxLogInVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.DAO.*;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.domain.AwardStore;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.DTO.WxInfoDTO;
import com.wizzstudio.languagerank.domain.user.UserExchangedAward;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService, Constant {
    @Autowired
    WxMaService wxService;
    @Autowired
    UserDAO userDAO;
    @Autowired
    AwardStoreDAO awardStoreDAO;
    @Autowired
    UserExchangedAwardDAO userExchangedAwardDAO;
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

        redisUtil.setUserOnlyInRedis(user.getUserId(), user);
        return wxLogInVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(String openId, String nickName, String avatarUrl) {
        User user = new User();
        Date date = new Date();
        user.setOpenId(openId);
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setTotalScore(0);
        user.setAvailableScore(0);
        user.setTotalWorshipScore(0);
        user.setTotalPunchCardScore(0);
        user.setTodayScore(0);
        user.setTodayPunchCardScore(0);
        user.setTodayWorshipScore(0);
        user.setWorship(0);
        user.setTotalPunchCardDay(0);
        user.setIsPunchCardToday(false);
        // 默认10点提醒用户打卡
        user.setReminderTime(PunchReminderTimeEnum.TEN);
        user.setIsLogInToday(true);
        user.setIsViewedJoinMyApplet(true);
        user.setLogInTime(date);
        user.setLogInLastTime(date);

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

    @Override
    public Integer findUserTotalScore(Integer userId) {
        return userDAO.findUserTotalScore(userId);
    }

    @Override
    public Map<String, Object> getScoreStore(User user) {
        Map<String, Object> scoreStoreMap = new HashMap<>();
        scoreStoreMap.put("myTotalScore", user.getTotalScore());
        scoreStoreMap.put("myAvailableScore", user.getAvailableScore());

        List<AwardStore> awardStoreList = new ArrayList<>();
        List<Integer> userAwardIdList = userExchangedAwardDAO.findAwardIdByUserId(user.getUserId());

        ListIterator listIterator = awardStoreDAO.findAll().listIterator();
        while (listIterator.hasNext()) {
            AwardStore awardStore = (AwardStore)listIterator.next();
            if (userAwardIdList.contains(awardStore.getAwardId())) {
                awardStore.setIsExchanged(true);
            } else {
                awardStore.setIsExchanged(false);
            }

            awardStoreList.add(awardStore);
        }

        scoreStoreMap.put("awardStoreList", awardStoreList);
        return scoreStoreMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean exchangedAward(User user, Integer awardId) {
        AwardStore awardStore = awardStoreDAO.findByAwardId(awardId);
        if (awardStore.getScore() > user.getAvailableScore()) {
            return false;
        }
        user.setAvailableScore(user.getAvailableScore() - awardStore.getScore());
        redisUtil.setUser(user.getUserId(), user);

        UserExchangedAward userExchangedAward = new UserExchangedAward();
        userExchangedAward.setUserId(user.getUserId());
        userExchangedAward.setAwardId(awardId);
        userExchangedAward.setExchangedTime(new Date());
        userExchangedAwardDAO.save(userExchangedAward);

        return true;
    }

    @Override
    public List<UserRelationshipRankVO> getUserRelationshipRank(Integer userId) {
        List<Integer> friendList = redisUtil.getUserRelationship(userId);
        // 用户本人也要参与排行
        friendList.add(userId);

        return userDAO.findUserRelationshipRank(friendList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIsLogInToday(Integer userId) {
        userDAO.updateIsLogInToday(userId);
    }

    @Override
    public void updateIsViewedJoinMyApplet(Integer userId) {
        userDAO.updateIsViewedJoinMyApplet(userId);
    }
}
