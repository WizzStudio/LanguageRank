package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import com.wizzstudio.languagerank.DTO.WxInfoDTO;
import com.wizzstudio.languagerank.VO.WxLogInVO;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class LogInController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody WxInfoDTO loginData) {
        try {
            WxLogInVO wxLogInVO = userService.userLogin(loginData);

            log.info("微信登录成功");
           return ResultUtil.success("微信登录成功", wxLogInVO);
        } catch (WxErrorException e) {
            log.error("微信登录失败");
            e.printStackTrace();
            return ResultUtil.error("微信登录失败");
        }
    }
}
