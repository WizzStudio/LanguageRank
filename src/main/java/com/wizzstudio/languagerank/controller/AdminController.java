package com.wizzstudio.languagerank.controller;

import com.wizzstudio.languagerank.DTO.StudyPlanImageDTO;
import com.wizzstudio.languagerank.service.*;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cms")
@Slf4j
public class AdminController {

    @Autowired
    FixedRankService fixedRankService;
    @Autowired
    EmployeeRankService employeeRankService;
    @Autowired
    StudyPlanService studyPlanService;
    @Autowired
    AdminStudyPlanService adminStudyPlanService;
    @Autowired
    UploadService uploadService;

//    返回给后台语言热度榜
    @PostMapping("/languageRank")
    public ResponseEntity getFixedRank(){
        return ResultUtil.success(fixedRankService.getFinalRank());
    }

//    返回给后台雇主需求榜
    @PostMapping("/employerdemandrank")
    public ResponseEntity getEmployeeRank(){
        return ResultUtil.success(employeeRankService.getEmployeeRank());
    }

//    返回给后台学习计划页面
    @GetMapping("/study")
    public ResponseEntity getStudyPlan(){
        return ResultUtil.success(adminStudyPlanService.getAdminStudyPlan());
    }

//    返回给后台某语言的所有的学习计划
    @GetMapping("/study/{languageName}")
    public ResponseEntity getStudyAllPlan(@PathVariable("languageName")String languageName) {
        return ResultUtil.success(studyPlanService.getAllStudyPlanDay(languageName));
    }

    // 将学习计划/奖励图片上传至七牛云并存储至数据库
    // 返回图片URL路径
    @PostMapping("storestudyplanimage")
    public ResponseEntity storeStudyPlanImage(@RequestBody StudyPlanImageDTO studyPlanImageDTO) throws IOException {
        String filePath = null;
        try {
            filePath = uploadService.uploadImage(studyPlanImageDTO.getMultipartFile());
            log.info("上传文件成功");
        } catch (IOException e) {
            log.error("上传文件失败");
            throw new IOException();
        }
        studyPlanService.saveStudyPlan(filePath, studyPlanImageDTO);

        Map<String, String> map = new HashMap<>();
        map.put("filePath", filePath);
        return ResultUtil.success("上传文件成功", map);
    }
}
