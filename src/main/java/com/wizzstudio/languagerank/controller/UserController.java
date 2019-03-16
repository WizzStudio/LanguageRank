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
import com.wizzstudio.languagerank.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    LanguageService languageService;
    @Autowired
    StudyPlanService studyPlanService;

    @PostMapping("/userinfo/{openId}")
    public ResponseEntity getUserInfo(@PathVariable("openId") String openId) {
        User user =  userService.findByOpenId(openId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setMyLanguage(user.getMyLanguage());
            userDTO.setJoinedNumber(languageService.findJoinedNumberByLanguage(user.getMyLanguage()));
            userDTO.setJoinedToday(languageService.findJoinedTodayByLanguage(user.getMyLanguage()));

            // 当用户已完成学习计划时返回false，反之返回true及具体学习计划
            if (user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay())) {
                userDTO.setIsStudyPlan(false);
                userDTO.setStudyPlan(null);
            } else {
                userDTO.setIsStudyPlan(true);
                userDTO.setStudyPlan(studyPlanService.findStudyPlanByStudyPlanDay(user.getMyLanguage(), user.getStudyPlanDay()));
            }
            return ResultUtil.success(userDTO);
        } else return ResultUtil.error();
    }

    @GetMapping("/myaward")
    public ResponseEntity getMyAward() {
        Map<String, Object> myAward = null;
        try {
            myAward = new HashMap<>();
//            myAward.put("isViewed", user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay()));
//            myAward.put("awardOne", studyPlanService.findStudyPlanByStudyPlanDay(languageName, StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay()).getContentOne());
//            myAward.put("awarcTwo", studyPlanService.findStudyPlanByStudyPlanDay(languageName, StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay()).getContentTwo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }

        return ResultUtil.success(myAward);
    }
}
