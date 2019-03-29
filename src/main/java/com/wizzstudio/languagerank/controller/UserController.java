package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.UserDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.LanguageService;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    LanguageService languageService;
    @Autowired
    StudyPlanService studyPlanService;
//    @Autowired
//    RedisUtil redisUtil;
//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;

    @PostMapping("/userinfo")
    public ResponseEntity getUserInfo(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
        Integer userId = jsonObject.getInteger("userId");

        User user =  userService.findByUserId(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setMyLanguage(user.getMyLanguage());
            userDTO.setJoinedNumber(languageService.findJoinedNumberByLanguage(user.getMyLanguage()));
            userDTO.setJoinedToday(languageService.findJoinedTodayByLanguage(user.getMyLanguage()));

            // 当用户已完成所有学习计划或当天计划时返回false，否则返回true及具体学习计划
            if (user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)
                    || user.getIsLogInToday().equals(true)) {
                userDTO.setIsViewedStudyPlan(false);
                userDTO.setStudyPlan(null);
            } else {
                userDTO.setIsViewedStudyPlan(true);
                userDTO.setStudyPlan(studyPlanService.getAllStudyPlanDay(user.getMyLanguage(), user.getStudyPlanDay().getStudyPlanDay()));

                // 用户今天已登录
                userService.updateIsLogInToday(userId);
                userService.updateStudyPlanDay(user);
            }
            return ResultUtil.success(userDTO);
        } else return ResultUtil.error();
    }

    @PostMapping("/myaward")
    public ResponseEntity getMyAward(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
//        User user = redisTemplate.opsForValue().get(CookieUtil.getCookie(request));

        Integer userId = jsonObject.getInteger("userId");

        User user = userService.findByUserId(userId);
        Map<String, Object> myAward = new HashMap<>();
        StudyPlan studyingLanguage = studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(), StudyPlanDayEnum.ACCOMPLISHED);
        List<StudyPlan> studyedLanguage = userService.findStudyedLanguageByUserId(userId);

        try {
            myAward.put("isViewed", user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED));
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
    public void updateLanguage(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        Integer userId = jsonObject.getInteger("userId");
        String languageName = jsonObject.getString("languageName");

//        User user = redisTemplate.opsForValue().get(CookieUtil.getCookie(request));
//        User user = userService.findByUserId(userId);
        userService.updateMyLanguage(userId, languageName);
        userService.resetStudyPlanDay(userId);
    }

    @PostMapping("/studyplan")
    public ResponseEntity getStudyAllPlan(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        Integer userId = jsonObject.getInteger("userId");

        User user =  userService.findByUserId(userId);
        if (user != null) {
//            UserDTO userDTO = new UserDTO();
            String languageName = user.getMyLanguage();
            Integer studyPlanDay = user.getStudyPlanDay().getStudyPlanDay();

            return ResultUtil.success(studyPlanService.getAllStudyPlanDay(languageName,studyPlanDay));
        }else
            return ResultUtil.error();
    }

}
