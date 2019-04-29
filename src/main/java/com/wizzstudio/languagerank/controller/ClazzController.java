package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.dto.AllClazzListDTO;
import com.wizzstudio.languagerank.dto.ClazzMemberDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.dto.UserClazzListDTO;
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
import java.util.Map;

@RestController
@Slf4j
public class ClazzController implements Constant {
    @Autowired
    ClazzService clazzService;

    @PostMapping("/createclazz")
    public ResponseEntity createClazz(@RequestBody CreateClazzDTO createClazzDTO) {
        try {
            Boolean isLegalClazzTag = false;
            for (String clazzTag : Constant.CLAZZ_TAG) {
                if (clazzTag.equals(createClazzDTO.getClazzTag())) {
                    isLegalClazzTag = true;
                    break;
                }
            }
            if (!isLegalClazzTag) {
                return ResultUtil.error(Constant.ILLEGAL_CLAZZ_TAG);
            }

            Clazz clazz = clazzService.createClazz(createClazzDTO);

            log.info("创建班级" + clazz.getClazzId() + "成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("创建班级失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping()
    public ResponseEntity getUserClazzList(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<UserClazzListDTO> clazzList = clazzService.getUserClazzList(userId);

            log.info("获取用户已加入班级列表成功");
            return ResultUtil.success(clazzList);
        } catch (Exception e) {
            log.error("获取用户已加入班级列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @GetMapping("/getclazzlist")
    public ResponseEntity getClazzList() {
        try {
            List<AllClazzListDTO> clazzList = clazzService.getAllClazzList();

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

            log.info(userId + "号用户加入班级" + clazzId + "成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error(userId + "号用户加入班级" + clazzId + "失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/quitclazz")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity quitClazz(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            clazzService.quitClazz(userId, clazzId);

            log.info(userId + "号用户退出班级" + clazzId + "成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error(userId + "号用户退出班级" + clazzId + "失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/getclazzstudyplan")
    public ResponseEntity getClazzStudyPlan(@RequestBody JSONObject jsonObject) {
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            List<String> clazzStudyPlanList = clazzService.getClazzStudyPlan(clazzId);

            log.info("获取" + clazzId + "号班级课程详情成功");
            return ResultUtil.success(clazzStudyPlanList);
        } catch (Exception e) {
            log.error("获取" + clazzId + "号班级课程详情失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/getspecialclazzmember")
    public ResponseEntity getSpecialClazzMember(@RequestBody JSONObject jsonObject) {
        try {
            Map<String, Object> specialClazzMember = clazzService.getSpecialClazzMember(jsonObject);

            log.info("获取班级特殊成员成功");
            return ResultUtil.success(specialClazzMember);
        } catch (Exception e) {
            log.error("获取班级特殊成员失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/getclazzmember")
    public ResponseEntity getClazzMember(@RequestBody JSONObject jsonObject) {
        try {
            List<ClazzMemberDTO> clazzMember = clazzService.getClazzMember(jsonObject);

            log.info("获取班级成员列表成功");
            return ResultUtil.success(clazzMember);
        } catch (Exception e) {
            log.error("获取班级成员列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }
}
