package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/6.
*/

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController {

    @PostMapping("/login/{openId}")
    public ResponseEntity logIn(@PathVariable("openId") Integer openId) {

    }
}
