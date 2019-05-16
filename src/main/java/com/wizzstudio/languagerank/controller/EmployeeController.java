package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/3/22.
*/

import com.wizzstudio.languagerank.DAO.LanguageDAO;
import com.wizzstudio.languagerank.domain.employeerank.CompanyPost;
import com.wizzstudio.languagerank.domain.employeerank.CompanySalary;
import com.wizzstudio.languagerank.domain.employeerank.LanguageCity;
import com.wizzstudio.languagerank.VO.LanguagePostVO;
import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.service.EmployeeService;
import com.wizzstudio.languagerank.service.PosterService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRankService employeeRankService;
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    PosterService posterService;

    @GetMapping("/{languageName}/post")
    public ResponseEntity getLanguagePost(@PathVariable("languageName")String languageName) {
        LanguagePostVO languagePostVO = new LanguagePostVO();
        try {
            languagePostVO.setLanguagePostList(employeeService.getLanguagePost(languageName));
            languagePostVO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
        } catch (Exception e) {
            log.error("获取与"+ languageName + "相关的热门岗位排行失败");
            return ResultUtil.error("获取与"+ languageName + "相关的热门岗位排行失败");
        }
            log.info("获取与"+ languageName + "相关的热门岗位排行成功");
            return ResultUtil.success("获取与"+ languageName + "相关的热门岗位排行成功", languagePostVO);
    }

    @GetMapping("/{languageName}/salary")
    public ResponseEntity getCompanySalary(@PathVariable("languageName")String languageName) {
        List<CompanySalary> list = employeeService.getCompanySalary(languageName);
        if (list != null) {
            log.info("获取使用"+ languageName +"的公司薪资排行成功");
            return ResultUtil.success("获取使用"+ languageName +"的公司薪资排行成功", list);
        } else {
            log.error("获取使用"+ languageName +"的公司薪资排行失败");
            return ResultUtil.error("获取使用"+ languageName +"的公司薪资排行失败");
        }
    }

    @GetMapping("/{languageName}/companypost")
    public ResponseEntity getCompanyPost(@PathVariable("languageName")String languageName) {
        List<CompanyPost> list = employeeService.getCompanyPost(languageName);
        if (list != null) {
            log.info("获取使用"+ languageName +"的公司岗位需求量排行成功");
            return ResultUtil.success("获取使用"+ languageName +"的公司岗位需求量排行成功", list);
        } else {
            log.error("获取使用"+ languageName +"的公司岗位需求量排行失败");
            return ResultUtil.error("获取使用"+ languageName +"的公司岗位需求量排行失败");
        }
    }

    @GetMapping("/{languageName}/languagecity")
    public ResponseEntity getLanguageCity(@PathVariable("languageName")String languageName) {
        List<LanguageCity> list = employeeService.getLanguageCity(languageName);
        if (list != null) {
            log.info("查询几大城市对"+ languageName +"的需求量成功");
            return ResultUtil.success("查询几大城市对"+ languageName +"的需求量成功", list);
        } else {
            log.error("查询几大城市对"+ languageName +"的需求量失败");
            return ResultUtil.error("查询几大城市对"+ languageName +"的需求量失败");
        }
    }

    @GetMapping("/employerdemandrank")
    public ResponseEntity getEmployeeRank(){
        log.info("获取雇主需求榜榜单页成功");
        return ResultUtil.success(employeeRankService.getEmployeeRank());
    }

    // 生成雇主需求详情页海报
    @GetMapping("/poster/{languageName}")
    public String a(@PathVariable("languageName")String languageName) throws Exception{
        return posterService.createPoster(languageName);
    }
}
