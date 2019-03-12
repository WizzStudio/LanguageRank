package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.ResultUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogInController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody WxInfo loginData, HttpServletRequest request, HttpServletResponse response) {
        try {
           WxLogInDTO wxLogInDTO = userService.userLogin(loginData);
           return ResultUtil.success(wxLogInDTO);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }
}