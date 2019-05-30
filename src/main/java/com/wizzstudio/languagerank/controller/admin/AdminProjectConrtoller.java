package com.wizzstudio.languagerank.controller.admin;

import com.wizzstudio.languagerank.DTO.CreateClazzDTO;
import com.wizzstudio.languagerank.VO.AdminProjectStudyPlanVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.constants.Errors;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.service.Admin.AdminProjectService;
import com.wizzstudio.languagerank.service.ClazzService;
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
    @Autowired
    ClazzService clazzService;

    @PostMapping("/getAllProject")
    public ResponseEntity getAllProject(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20")int size){
        try {
            log.info("获取总项目列表成功");
            return ResultUtil.success(adminProjectService.getAllProject(page,size));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取总项目列表失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/getProjectTotalNumber")
    public ResponseEntity getProjectTotalNumber(@RequestBody Integer id){
        try {
            log.info("获取某一个项目人数、讨论数等成功");
            return ResultUtil.success(adminProjectService.getProjectTotalNumber(id));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取某一个项目人数、讨论数失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/getAdminProjectPlan")
    public ResponseEntity getAdminProjectPlan(@RequestBody Integer id){
        try {
            log.info("获取某一个项目学习计划成功");
            return ResultUtil.success(adminProjectService.getAdminProjectPlan(id));
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取某一个项目项目学习计划失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/updateProjectPlan")
    public ResponseEntity updateAdminProjectPlan(@RequestBody AdminProjectStudyPlanVO adminProjectStudyPlanVO, @RequestBody Integer clazzId){
        try {
            adminProjectService.saveAdminProjectStudyPlanVO(adminProjectStudyPlanVO, clazzId);
            log.info("修改"+ adminProjectStudyPlanVO.getClazzName() +"项目学习计划成功");
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改一个项目项目学习计划失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/createClazz")
    public ResponseEntity createAdminProjectPlan(@RequestBody CreateClazzDTO createClazzDTO){
        try {
            Boolean isLegalClazzTag = false;
            for (String clazzTag : Constant.CLAZZ_TAG) {
                if (clazzTag.equals(createClazzDTO.getClazzTag())) {
                    isLegalClazzTag = true;
                    break;
                }
            }
            if (!isLegalClazzTag) {
                return ResultUtil.error(Errors.ILLEGAL_CLAZZ_TAG);
            }
            Clazz clazz = clazzService.createClazz(createClazzDTO);

            log.info("创建班级" + clazz.getClazzId() + "成功");
            log.info("创建一个班级成功");
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("创建一个班级失败");
            return ResultUtil.error();
        }
    }

    @PostMapping("/deleteAdminClazzAndPlan")
    public ResponseEntity deleteAdminClazzAndPlan(@RequestBody Integer clazzId){
        try {
            adminProjectService.deleteAdminClazzAndPlan(clazzId);
            log.info("删除班级"+clazzId+"、学习计划成功");
            return ResultUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除班级"+clazzId+"、学习计划失败");
            return ResultUtil.error();
        }
    }
}
