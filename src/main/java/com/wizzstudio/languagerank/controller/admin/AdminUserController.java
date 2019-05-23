package com.wizzstudio.languagerank.controller.admin;

import com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO;
import com.wizzstudio.languagerank.service.Admin.AdminUserService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/user")
@Slf4j
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;
//      返回值与一般的不同，未解决
    @PostMapping("/getuserinfo")
    public ResponseEntity getUserInfo(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20")int size){
        try {
            log.info("获取用户信息成功");
            return ResultUtil.success(adminUserService.getUserInfoPage(page,size));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取用户信息失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/gettotalnumber")
    public ResponseEntity getTotalNumber(){
        try {
            log.info("获取总人数等成功");
            return ResultUtil.success(adminUserService.getTotalNumber());
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取总人数等失败");
            return ResultUtil.error();
        }
    }
    @PostMapping("/getOneUser")
    public ResponseEntity getOneUser(@RequestParam String name){
        try {
            log.info("获取个人信息成功");
            return ResultUtil.success(adminUserService.getOneUser(name));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取个人信息失败");
            return ResultUtil.error();
        }
    }

}
