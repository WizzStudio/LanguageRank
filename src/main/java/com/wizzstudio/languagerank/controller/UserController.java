package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.VO.UserRelationshipRankVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.constants.Errors;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.service.*;
import com.wizzstudio.languagerank.util.RedisUtil;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController implements Constant {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取用户主页的接口
     */
    @PostMapping("/userinfo")
    public ResponseEntity getUserInfo(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            User user = redisUtil.getUser(userId);
            if (user.getIsLogInToday().equals(false)) {
                userService.updateIsLogInToday(userId);
                user.setIsLogInToday(true);
                redisUtil.setUser(userId, user);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("avatarUrl", user.getAvatarUrl());
            map.put("nickName", user.getNickName());
            map.put("totalScore", userService.findUserTotalScore(userId));

            log.info("获取"+ userId + "号用户信息成功");
            return ResultUtil.success(map);
        } catch (Exception e) {
            log.error("获取"+ userId + "号用户信息失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取积分商城的接口
     */
    @PostMapping("/scorestore")
    public ResponseEntity scoreStore(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            User user = redisUtil.getUser(userId);
            Map<String, Object> scoreStoreMap = userService.getScoreStore(user);

            log.info(userId + "号用户获取积分商城成功");
            return ResultUtil.success(scoreStoreMap);
        } catch (Exception e) {
            log.error(userId + "号用户获取积分商城失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 兑换奖品的接口
     */
    @PostMapping("/exchangedaward")
    public ResponseEntity exchangedAward(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer awardId = jsonObject.getInteger("awardId");

        try {
            User user = redisUtil.getUser(userId);
            if (userService.exchangedAward(user, awardId)) {
                log.info(userId + "号用户兑换" + awardId + "号奖品成功");
                return ResultUtil.success();
            } else {
                log.error(userId + "号用户兑换" + awardId + "号奖品积分不足");
                return ResultUtil.error(Errors.NOT_ENOUGH_SCORE);
            }
        } catch (Exception e) {
            log.error(userId + "号用户兑换" + awardId + "号奖品失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 关闭加入我的小程序弹窗接口
     */
    @PostMapping("/updateisviewedjoinmyapplet")
    public ResponseEntity updateIsViewedJoinMyApplet(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        userService.updateIsViewedJoinMyApplet(userId);
        User user = redisUtil.getUser(userId);
        user.setIsViewedJoinMyApplet(false);
        redisUtil.setUser(userId, user);

        log.info(userId + "号用户加入我的小程序弹窗不再弹出");
        return ResultUtil.success();
    }

    /**
     * 新增好友关系接口
     */
    @PostMapping("/updateuserrelationship")
    public ResponseEntity updateUserRelationship(@RequestBody JSONObject jsonObject) {
        Integer userOne = jsonObject.getInteger("userOne");
        Integer userTwo = jsonObject.getInteger("userTwo");

        try {
            redisUtil.setUserRelationship(userOne, userTwo);
        } catch (Exception e) {
            log.error(userOne + "号用户与"+ userTwo + "号用户新增好友关系失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
        return ResultUtil.success();
    }

    /**
     * 获取好友关系接口
     */
    @PostMapping("/getuserrelationship")
    public ResponseEntity getUserRelationship(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<Integer> friendList = redisUtil.getUserRelationship(userId);

            log.info("获取"+ userId + "号用户好友关系成功");
            return ResultUtil.success(friendList);
        } catch (Exception e) {
            log.error("获取"+ userId + "号用户好友关系失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取好友排行接口
     */
    @PostMapping("/getuserrelationshiprank")
    public ResponseEntity getUserRelationshipRank(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<UserRelationshipRankVO> userRelationshipRank = userService.getUserRelationshipRank(userId);

            log.info("获取"+ userId + "号用户好友排行成功");
            return ResultUtil.success(userRelationshipRank);
        } catch (Exception e) {
            log.error("获取"+ userId + "号用户好友排行失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }
}
