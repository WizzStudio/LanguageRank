package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.VO.CommentVO;
import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import com.wizzstudio.languagerank.service.CommentService;
import com.wizzstudio.languagerank.service.UserService;
import com.wizzstudio.languagerank.util.RedisUtil;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/getemployeerankcomment")
    public ResponseEntity getEmployeeRankComment(@RequestBody JSONObject jsonObject) {
        String languageName = jsonObject.getString("languageName");
        Integer pageIndex = jsonObject.getInteger("pageIndex");
        Integer commentDisplayModeInteger = jsonObject.getInteger("commentDisplayMode");

        CommentDisplayModeEnum commentDisplayMode;
        // 1表示优先显示最新评论，2表示优先显示最先评论(默认为1)
        if (commentDisplayModeInteger == 1) {
            commentDisplayMode = CommentDisplayModeEnum.NEW_COMMENT_PRIORITIZED;
        } else {
            commentDisplayMode = CommentDisplayModeEnum.OLD_COMMENT_PRIORITIZED;
        }

        try {
            CommentVO commentVO = commentService.getEmployeeRankComment(languageName, pageIndex, commentDisplayMode);

            log.info("获取雇主需求详情页的评论成功");
            return ResultUtil.success(commentVO);
        } catch (Exception e) {
            log.error("获取雇主需求详情页的评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("getfixedrankcomment")
    public ResponseEntity getFixedRankComment(@RequestBody JSONObject jsonObject) {
        String languageName = jsonObject.getString("languageName");
        Integer pageIndex = jsonObject.getInteger("pageIndex");
        Integer commentDisplayModeInteger = jsonObject.getInteger("commentDisplayMode");

        CommentDisplayModeEnum commentDisplayMode;
        if (commentDisplayModeInteger == 1) {
            commentDisplayMode = CommentDisplayModeEnum.NEW_COMMENT_PRIORITIZED;
        } else {
            commentDisplayMode = CommentDisplayModeEnum.OLD_COMMENT_PRIORITIZED;
        }

        try {
            CommentVO commentVO = commentService.getFixedRankComment(languageName, pageIndex, commentDisplayMode);

            log.info("获取语言主页的评论成功");
            return ResultUtil.success(commentVO);
        } catch (Exception e) {
            log.error("获取语言主页的评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("getclazzcomment")
    public ResponseEntity getClazzComment(@RequestBody JSONObject jsonObject) {
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");
        Integer commentDisplayModeInteger = jsonObject.getInteger("commentDisplayMode");

        CommentDisplayModeEnum commentDisplayMode;
        if (commentDisplayModeInteger == 1) {
            commentDisplayMode = CommentDisplayModeEnum.NEW_COMMENT_PRIORITIZED;
        } else {
            commentDisplayMode = CommentDisplayModeEnum.OLD_COMMENT_PRIORITIZED;
        }

        try {
            CommentVO commentVO = commentService.getClazzComment(clazzId, pageIndex, commentDisplayMode);

            log.info("获取班级评论成功");
            return ResultUtil.success(commentVO);
        } catch (Exception e) {
            log.error("获取班级评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("/updateemployeerankcomment")
    public ResponseEntity updateEmployeeRankComment(@RequestBody JSONObject jsonObject) {
        try {
            commentService.updateEmployeeRankComment(jsonObject);
        } catch (Exception e) {
            log.error("更新雇主需求详情页的评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
        log.info("更新雇主需求详情页的评论成功");
        return ResultUtil.success();
    }

    @PostMapping("/updatefixedrankcomment")
    public ResponseEntity updateFixedRankComment(@RequestBody JSONObject jsonObject) {
        try {
            commentService.updateFixedRankComment(jsonObject);
        } catch (Exception e) {
            log.error("更新语言主页的评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
        log.info("更新语言主页的评论成功");
        return ResultUtil.success();
    }

    @PostMapping("/updateclazzcomment")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateClazzComment(@RequestBody JSONObject jsonObject) {
        try {
            commentService.updateClazzComment(jsonObject);
        } catch (Exception e) {
            log.error("更新班级评论失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
        log.info("更新班级评论成功");
        return ResultUtil.success();
    }
}
