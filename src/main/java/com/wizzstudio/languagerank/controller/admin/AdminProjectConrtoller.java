package com.wizzstudio.languagerank.controller.admin;

import com.wizzstudio.languagerank.service.Admin.AdminProjectService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/project")
@Slf4j
public class AdminProjectConrtoller {

    @Autowired
    AdminProjectService adminProjectService;

    @PostMapping("/getAllProject")
    public ResponseEntity getAllProject(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20")int size){
        try {
            log.info("获取项目列表成功");
            return ResultUtil.success(adminProjectService.getAllProject(page,size));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取项目列表失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/getProjectTotalNumber")
    public ResponseEntity getProjectTotalNumber(@RequestBody Integer id){
        try {
            log.info("获取项目人数、讨论数成功");
            return ResultUtil.success(adminProjectService.getProjectTotalNumber(id));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取项目人数、讨论数失败");
            return ResultUtil.error();
        }
    }

}
