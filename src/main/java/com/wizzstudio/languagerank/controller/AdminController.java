package com.wizzstudio.languagerank.controller;

import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.service.FixedRankService;
import com.wizzstudio.languagerank.service.StudyPlanService;
import com.wizzstudio.languagerank.service.impl.AdminStudyPlanServiceImpl;
import com.wizzstudio.languagerank.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms")
public class AdminController {

    @Autowired
    FixedRankService fixedRankService;
    @Autowired
    EmployeeRankService employeeRankService;
    @Autowired
    StudyPlanService studyPlanService;
    @Autowired
    AdminStudyPlanServiceImpl adminStudyPlanService;

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



}
