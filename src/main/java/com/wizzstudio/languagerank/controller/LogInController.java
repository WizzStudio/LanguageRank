package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
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
    public ResponseEntity logIn(@RequestBody WxInfo loginData, HttpServletRequest request, HttpServletResponse response) {
        try {
           // 生成cookie
//            String cookie = CookieUtil.tokenGenerate();
//            System.out.println(loginData.code);
//            WxLogInDTO wxLogInDTO = userService.userLogin(loginData, cookie);

            WxLogInDTO wxLogInDTO = userService.userLogin(loginData);

//            // 将cookie写入response中返回給前端
//            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            log.info("微信登录成功");
           return ResultUtil.success("微信登录成功", wxLogInDTO);
        } catch (WxErrorException e) {
            log.error("微信登录失败，e={}",e);
            e.printStackTrace();
            return ResultUtil.error("微信登录失败");
        }
    }

//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Cookie cookie = CookieUtil.getCookie(request);
//        if (cookie != null) redisUtil.delete(cookie.getValue());
//        response.sendRedirect("/login/admin");
//        log.info("用户已退出登录");
//    }
}
