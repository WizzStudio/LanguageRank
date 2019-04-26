package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.dto.ClazzDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.service.ClazzService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @PostMapping("/createclazz")
    public ResponseEntity createClazz(@RequestBody CreateClazzDTO createClazzDTO) {
        try {
            Clazz clazz = clazzService.createClazz(createClazzDTO);

            log.info("创建班级" + clazz.getClazzId() + "成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("创建班级失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @GetMapping("/getclazzlist")
    public ResponseEntity getClazzList() {
        try {
            List<ClazzDTO> clazzList = clazzService.getClazzList();

            log.info("获取班级列表成功");
            return ResultUtil.success(clazzList);
        } catch (Exception e) {
            log.error("获取班级列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/joinclazz")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity joinClazz(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            clazzService.joinClazz(userId, clazzId);
        } catch (Exception e) {
            log.error(userId + "号用户加入班级" + clazzId + "失败");
            e.printStackTrace();
            return ResultUtil.error();
        }

        log.info(userId + "号用户加入班级" + clazzId + "成功");
        return ResultUtil.success();
    }
}
