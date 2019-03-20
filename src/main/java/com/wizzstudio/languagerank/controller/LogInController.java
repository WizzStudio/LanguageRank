package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.CookieUtil;
import com.wizzstudio.languagerank.util.RedisUtil;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class LogInController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody WxInfo loginData, HttpServletRequest request, HttpServletResponse response) {
        try {
           WxLogInDTO wxLogInDTO = userService.userLogin(loginData);

           // 生成cookie
            String cookie = CookieUtil.tokenGenerate();
            // 以cookie值为key，openId值为value存入redis
            redisUtil.storeNewCookie(cookie, wxLogInDTO.getOpenId());
            // 将cookie写入response中返回給前端
            CookieUtil.setCookie(response, Constant.TOKEN, cookie, Constant.TOKEN_EXPIRED);
            log.info("微信登录成功");
           return ResultUtil.success(wxLogInDTO);
        } catch (WxErrorException e) {
            log.error("微信登录失败，e={}",e);
            e.printStackTrace();
            return ResultUtil.error();
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
