package com.wizzstudio.languagerank.controller;

import com.wizzstudio.languagerank.service.EmployeeRankService;
import com.wizzstudio.languagerank.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRankController {

    @Autowired
    EmployeeRankService employeeRankService;

    @PostMapping("/employerdemandrank")
    public ResponseEntity getEmployeeRank(){

        return ResultUtil.success(employeeRankService.getEmployeeRank());
    }

}
