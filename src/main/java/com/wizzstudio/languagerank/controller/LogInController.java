package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import com.wizzstudio.languagerank.dto.WxInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogInController {

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody WxInfo wxInfo, HttpServletRequest request, HttpServletResponse response) {

    }
}
