package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.domain.Award;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.UserDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.*;
import com.wizzstudio.languagerank.util.RedisUtil;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController implements Constant {

    @Autowired
    UserService userService;
    @Autowired
    LanguageCountService languageCountService;
    @Autowired
    StudyPlanService studyPlanService;
    @Autowired
    ShareDimensionCodeService shareDimensionCodeService;
    @Autowired
    AwardService awardService;
    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/userinfo")
    public ResponseEntity getUserInfo(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
        Integer userId = jsonObject.getInteger("userId");

//        User user =  userService.findByUserId(userId);
        User user = redisUtil.getUser(userId);
//        System.out.println(user);
        String myLanguage = user.getMyLanguage();
        UserDTO userDTO = new UserDTO();

        // 先新增用户学习计划天数，核心思想是数据库中存储的学习计划天数是用户可见的天数
        if (!myLanguage.equals("未加入")) {
            if (!user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED) && user.getIsLogInToday().equals(false)) {
                StudyPlanDayEnum newStudyPlanDay =  userService.updateStudyPlanDay(user);
                userDTO.setIsViewedStudyPlan(true);

                // 当用户今天登录后studyPlanDay与isLogInToday要变化，修改后存入redis(isLogInToday要手动修改)
                user.setStudyPlanDay(newStudyPlanDay);
            } else {
                userDTO.setIsViewedStudyPlan(false);
            }

            userDTO.setMyLanguage(myLanguage);
            userDTO.setJoinedNumber(languageCountService.findJoinedNumberByLanguage(myLanguage));
            userDTO.setJoinedToday(languageCountService.findJoinedTodayByLanguage(myLanguage));
            userDTO.setStudyPlanDay(user.getStudyPlanDay().getStudyPlanDay());
            userDTO.setIsViewedJoinMyApplet(user.getIsViewedJoinMyApplet());
        }
        // 用户今天已登录
        if (user.getIsLogInToday().equals(false)) {
            userService.updateIsLogInToday(userId);
            user.setIsLogInToday(true);
        }

        redisUtil.setUser(userId, user);
        log.info("获取用户信息成功");
        return ResultUtil.success(userDTO);
    }

    @PostMapping("/myaward")
    public ResponseEntity getMyAward(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Integer userId = jsonObject.getInteger("userId");

//        User user =  userService.findByUserId(userId);
        User user = redisUtil.getUser(userId);
        Map<String, Object> myAward = new HashMap<>();

        Award studyingLanguage = awardService.findAwardByLanguageName(user.getMyLanguage());
        // 将该语言的奖励返回，但只有完成了学习计划才能显示
        if (user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)) {
            studyingLanguage.setIsViewed(true);
        } else {
            studyingLanguage.setIsViewed(false);
        }

        List<Award> studyedLanguage = userService.findStudyedLanguageAwardByUserId(user);

        try {
            myAward.put("studyingLanguage", studyingLanguage);
            myAward.put("studyedLanguage", studyedLanguage);
        } catch (NullPointerException e) {
            log.error("获取我的奖励失败");
            e.printStackTrace();
            return ResultUtil.error("获取我的奖励失败");
        }
        log.info("获取我的奖励成功");
        return ResultUtil.success("获取我的奖励成功", myAward);
    }

    @PostMapping("/updatelanguage")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateLanguage(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        Integer userId = jsonObject.getInteger("userId");
        String languageName = jsonObject.getString("languageName");

        Boolean isInStudyPlanLanguage = false;
        for (String language : Constant.STUDY_PLAN_LANGUAGE) {
            if (language.equals(languageName)) {
                isInStudyPlanLanguage = true;
                break;
            }
        }
        if (!isInStudyPlanLanguage) {
            return ResultUtil.error(Constant.NOT_READY_lANGUAGE);
        }

//        User user = userService.findByUserId(userId);
        User user = redisUtil.getUser(userId);
        if (languageName.equals(user.getMyLanguage())) {
            return ResultUtil.error(Constant.STUDYING_NOW);
        }

        try {
            userService.updateMyLanguage(user, languageName);
        } catch (Exception e) {
            log.error("更新语言失败");
            e.printStackTrace();
        }
        log.info("更新语言成功");
        return ResultUtil.success();
    }

    @PostMapping("/studyplan")
    public ResponseEntity getStudyPlan(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        Integer userId = jsonObject.getInteger("userId");

//        User user =  userService.findByUserId(userId);
        User user = redisUtil.getUser(userId);
        if (user != null) {
            String languageName = user.getMyLanguage();
            Integer studyPlanDay = user.getStudyPlanDay().getStudyPlanDay();

            Map<String, Object> map = new HashMap<>();
            map.put("studyPlan", studyPlanService.getStudyedStudyPlanDay(languageName,studyPlanDay));
            map.put("isTranspondedList", userService.getUseTranspond(languageName, userId));

            log.info("获取用户学习计划成功");
            return ResultUtil.success(map);
        }else {
            log.error("获取用户学习计划失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/updatetranspond")
    public ResponseEntity updateUserTranspond(@RequestBody JSONObject jsonObject) {
        Integer studyPlanDay = jsonObject.getInteger("studyPlanDay");
        Integer userId = jsonObject.getInteger("userId");

        try {
//            User user =  userService.findByUserId(userId);
            User user = redisUtil.getUser(userId);
            userService.updateUserTranspondTable(user, studyPlanDay);
        } catch (Exception e) {
            log.error("更新用户转发表失败");
            return ResultUtil.error();
        }
        log.info("更新用户转发表成功");
        return ResultUtil.success();
    }

    @PostMapping("/updateisviewedjoinmyapplet")
    public ResponseEntity updateIsViewedJoinMyApplet(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        userService.updateIsViewedJoinMyApplet(userId);
        User user = redisUtil.getUser(userId);
        user.setIsViewedJoinMyApplet(false);
        redisUtil.setUser(userId, user);

        log.info("加入我的小程序弹窗不再弹出");
        return ResultUtil.success();
    }

////      获得分享的二维码图片
//    @GetMapping("/dimensioncode")
//    public ResponseEntity shareDimensionCode(){
//        log.info("获取小程序码成功");
//        return ResultUtil.success(shareDimensionCodeService.getDimensionCode());
//    }

//    @PostMapping("/test")
//    public ResponseEntity testSetUser(@RequestBody JSONObject jsonObject) {
//        Integer userId = jsonObject.getInteger("userId");
//        User user = userService.findByUserId(userId);
//        User user = redisUtil.getUser(userId);
//        redisUtil.setUser(userId, user);
//        return ResultUtil.success(redisUtil.getUser(userId));
//    }
}
