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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        User user =  userService.findUserByOpenId(openId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setMyLanguage(user.getMyLanguage());
            userDTO.setJoinedNumber(languageService.findJoinedNumberByLanguage());
            userDTO.setJoinedToday(languageService.findJoinedTodayByLanguage());

            // 当用户已完成学习计划时返回false，反之返回true及具体学习计划
            if (user.getStudyPlanDay().equals(StudyPlanDayEnum.ACCOMPLISHED.getStudyPlanDay())) {
                userDTO.setIsStudyPlan(false);
                userDTO.setStudyPlan(null);
            } else {
                userDTO.setIsStudyPlan(true);
                userDTO.setStudyPlan(studyPlanService.findStudyPlanByStudyPlanDay(user.getStudyPlanDay()));
            }
            return ResultUtil.success(userDTO);
        } else return ResultUtil.error();
    }
}
