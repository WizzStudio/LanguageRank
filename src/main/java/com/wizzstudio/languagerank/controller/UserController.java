package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.UserDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.LanguageService;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.CookieUtil;
import com.wizzstudio.languagerank.util.RedisUtil;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public ResponseEntity getUserInfo(@RequestBody Integer userId ,HttpServletRequest request) {
        User user =  userService.findByUserId(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setMyLanguage(user.getMyLanguage());
            userDTO.setJoinedNumber(languageService.findJoinedNumberByLanguage(user.getMyLanguage()));
            userDTO.setJoinedToday(languageService.findJoinedTodayByLanguage(user.getMyLanguage()));

            // 当用户已完成所有学习计划或当天计划时返回false，否则返回true及具体学习计划
            if (user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED)
                    || user.getIsLogInToday() == true) {
                userDTO.setIsViewedStudyPlan(false);
                userDTO.setStudyPlan(null);
            } else {
                userDTO.setIsViewedStudyPlan(true);
                userDTO.setStudyPlan(studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(),
                        user.getStudyPlanDay()));

                // 用户今天已登录

                //计划将这个接口拆成两个
                userService.updateIsLogInToday(userId);
                userService.updateStudyPlanDay();
            }
            return ResultUtil.success(userDTO);
        } else return ResultUtil.error();
    }

    // 还没有实现将用户以前学完过的语言也传给前端的功能
    @PostMapping("/myaward")
    public ResponseEntity getMyAward(@RequestBody Integer userId ,HttpServletRequest request) {
//        User user = redisTemplate.opsForValue().get(CookieUtil.getCookie(request));

        User user = userService.findByUserId(userId);
        Map<String, Object> myAward = null;
        Map<String, String> mySpecificAward = null;
        try {
            myAward = new HashMap<>();
            myAward.put("isViewed", user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED));

            mySpecificAward.put("imageOne", studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(), StudyPlanDayEnum.ACCOMPLISHED).getImageOne());
            mySpecificAward.put("contentOne", studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(), StudyPlanDayEnum.ACCOMPLISHED).getContentOne());
            myAward.put("awardOne", mySpecificAward);

            mySpecificAward = null;
            mySpecificAward.put("imageTwo", studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(), StudyPlanDayEnum.ACCOMPLISHED).getImageOne());
            mySpecificAward.put("contentTwo", studyPlanService.findStudyPlanByLanguageNameAndStudyPlanDay(user.getMyLanguage(), StudyPlanDayEnum.ACCOMPLISHED).getContentOne());
            myAward.put("awardTwo", mySpecificAward);
        } catch (NullPointerException e) {
            log.error("获取我的奖励失败");
            e.printStackTrace();
            return ResultUtil.error("获取我的奖励失败");
        }
        log.info("获取我的奖励成功");
        return ResultUtil.success("获取我的奖励成功", myAward);
    }

    @PostMapping("/updatelanguage")
    public void updateLanguage(@RequestBody String languageName,@RequestBody Integer userId, HttpServletRequest request){
//        User user = redisTemplate.opsForValue().get(CookieUtil.getCookie(request));
        User user = userService.findByUserId(userId);
        userService.updateMyLanguage(user, languageName);
        userService.resetStudyPlanDay(user);
    }

    @PostMapping("/studyplan")
    public ResponseEntity getStudyAllPlan(@RequestBody Integer userId ,HttpServletRequest request){
        User user =  userService.findByUserId(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            String languageName = user.getMyLanguage();
            Integer studyPlanDay = user.getStudyPlanDay().getStudyPlanDay();

            return ResultUtil.success(studyPlanService.getAllStudyPlanDay(languageName,studyPlanDay));
        }else
            return ResultUtil.error();
    }

}
