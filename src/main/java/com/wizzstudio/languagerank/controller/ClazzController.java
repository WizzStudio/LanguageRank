package com.wizzstudio.languagerank.controller;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.VO.AllClazzVO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.VO.CollectionVO;
import com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.constants.Errors;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.DTO.*;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import com.wizzstudio.languagerank.service.ClazzService;
import com.wizzstudio.languagerank.service.PosterService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ClazzController implements Constant, Errors {
    @Autowired
    ClazzService clazzService;
    @Autowired
    PosterService posterService;

    /**
     * 创建班级的接口
     */
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
                return ResultUtil.error(Errors.ILLEGAL_CLAZZ_TAG);
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

    /**
     * 获取用户已加入班级列表的接口
     */
    @PostMapping("/getuserclazzlist")
    public ResponseEntity getUserClazzList(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<UserClazzDTO> clazzList = clazzService.getUserClazzList(userId);

            log.info("获取" + userId + "号用户已加入班级的列表成功");
            return ResultUtil.success(clazzList);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户已加入班级的列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 刷新用户已加入班级列表的接口(给前端频繁调用刷新缓存)
     */
    @PostMapping("/refreshuserclazzlist")
    public ResponseEntity refreshUserClazzList(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<Integer> clazzList = clazzService.refreshUserClazzList(userId);

            log.info("刷新" + userId + "号用户已加入班级的列表成功");
            return ResultUtil.success(clazzList);
        } catch (Exception e) {
            log.error("刷新" + userId + "号用户已加入班级的列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取全部班级列表接口
     */
    @GetMapping("/getclazzlist")
    public ResponseEntity getClazzList() {
        try {
            List<AllClazzVO> clazzList = clazzService.getAllClazzList();

            log.info("获取班级列表成功");
            return ResultUtil.success(clazzList);
        } catch (Exception e) {
            log.error("获取班级列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 加入班级接口
     */
    @PostMapping("/joinclazz")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity joinClazz(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

//        List<Integer> userClazzList = clazzService.refreshUserClazzList(userId);
//        if (userClazzList.contains(clazzId)) {
//            return ResultUtil.error(Errors.JOINED_CLAZZ);
//        }

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

    /**
     * 退出班级接口
     */
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

    /**
     * 查询某班级基本信息的接口
     */
    @PostMapping("/getclazzmessage")
    public ResponseEntity getClazzMessage(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            ClazzMessageVO clazzMessage = clazzService.getClazzMessage(userId, clazzId);

            log.info("查询班级" + clazzId + "基本信息成功");
            return ResultUtil.success(clazzMessage);
        } catch (Exception e) {
            log.error("查询班级" + clazzId + "基本信息失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 查询某班级课程详情的接口
     */
    @PostMapping("/getclazzstudyplan")
    public ResponseEntity getClazzStudyPlan(@RequestBody JSONObject jsonObject) {
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            Map<String, Object> briefIntroductionMap = clazzService.getClazzStudyPlanBriefIntroduction(clazzId);

            log.info("获取" + clazzId + "号班级课程简介成功");
            return ResultUtil.success(briefIntroductionMap);
        } catch (Exception e) {
            log.error("获取" + clazzId + "号班级课程简介失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 查询用户在某班级学习计划的接口
     */
    @PostMapping("/getuserclazzstudyplan")
    public ResponseEntity getUserClazzStudyPlan(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            List<ClazzStudyPlan> userClazzStudyPlanList = clazzService.getUserClazzStudyPlan(userId, clazzId);

            log.info("获取" + userId + "号用户在" + clazzId + "号班级学习计划成功");
            return ResultUtil.success(userClazzStudyPlanList);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户在" + clazzId + "号班级学习计划失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 查询用户在某班级今日学习计划的百度网盘小程序码图片的接口
     */
    @PostMapping("/getuserclazzstudyplantoday")
    public ResponseEntity getUserClazzStudyPlanToday(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
             Map<String, String> userClazzStudyPlanQRCodeMap = new HashMap<>();
             userClazzStudyPlanQRCodeMap.put("qrCode", clazzService.getUserClazzStudyPlanToday(userId, clazzId));

            log.info("获取" + userId + "号用户在" + clazzId + "号班级今日学习计划成功");
            return ResultUtil.success(userClazzStudyPlanQRCodeMap);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户在" + clazzId + "号班级今日学习计划失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 查询某班级的班长及该班级中某用户全部好友的接口
     */
    @PostMapping("/getspecialclazzmember")
    public ResponseEntity getSpecialClazzMember(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            Map<String, Object> specialClazzMember = clazzService.getSpecialClazzMember(userId, clazzId);

            log.info("获取班级特殊成员成功");
            return ResultUtil.success(specialClazzMember);
        } catch (Exception e) {
            log.error("获取班级特殊成员失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 查询某班级全部成员的接口
     */
    @PostMapping("/getclazzmember")
    public ResponseEntity getClazzMember(@RequestBody JSONObject jsonObject) {
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");

        try {
            Map<String, Object> clazzMemberMap = clazzService.getClazzMember(clazzId, pageIndex);

            log.info("获取班级成员列表成功");
            return ResultUtil.success(clazzMemberMap);
        } catch (Exception e) {
            log.error("获取班级成员列表失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 用户打卡接口
     */
    @PostMapping("/punchcard")
    public ResponseEntity punchCard(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        String formId = jsonObject.getString("formId");

        try {
            clazzService.punchCard(userId, clazzId, formId);

            log.info(userId + "号用户在班级" + clazzId + "打卡成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error(userId + "号用户在班级" + clazzId + "打卡失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取用户打卡提醒时间接口
     */
    @PostMapping("/getpunchcardremindertime")
    public ResponseEntity getPunchCardReminderTime(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            Map<String, Integer> punchCardReminderTimeMap = new HashMap<>();
            punchCardReminderTimeMap.put("reminderTime", clazzService.getPunchCardReminderTime(userId));

            log.info("获取" + userId + "号用户打卡提醒时间成功");
            return ResultUtil.success(punchCardReminderTimeMap);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户打卡提醒时间失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 更新用户打卡提醒时间接口
     */
    @PostMapping("/updatepunchcardremindertime")
    public ResponseEntity updatePunchCardReminderTime(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer reminderTime = jsonObject.getInteger("reminderTime");

        PunchReminderTimeEnum punchReminderTime;
        switch (reminderTime) {
            case 0:
                punchReminderTime = PunchReminderTimeEnum.NOT_REMIND;
                break;
            case 8:
                punchReminderTime = PunchReminderTimeEnum.EIGHT;
                break;
            case 9:
                punchReminderTime = PunchReminderTimeEnum.NINE;
                break;
            case 10:
                punchReminderTime = PunchReminderTimeEnum.TEN;
                break;
            case 11:
                punchReminderTime = PunchReminderTimeEnum.ELEVEN;
                break;
            default:
                return ResultUtil.error(Errors.ILLEGAL_ARGUMENT_IN_UPDATE_PUNCHCARD_REMINDER_TIME);
        }

        try {
            clazzService.updatePunchCardReminderTime(userId, punchReminderTime);

            log.info("更新" + userId + "号用户打卡提醒时间成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error("更新" + userId + "号用户打卡提醒时间失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取用户在某班级今日打卡信息接口
     */
    @PostMapping("/getuserpunchcardmessagetoday")
    public ResponseEntity getUserPunchCardMessageToday(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            UserPunchCardMessageTodayVO message = clazzService.getUserPunchCardMessageToday(userId, clazzId);

            log.info("获取" + userId + "号用户在班级" + clazzId + "打卡信息成功");
            return ResultUtil.success(message);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户在班级" + clazzId + "打卡信息失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取班级勤奋排行榜接口
     */
    @PostMapping("/gethardworkingrank")
    public ResponseEntity getHardWorkingRank(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");

        try {
             Map<String, Object> map = clazzService.getHardWorkingRank(userId, clazzId, pageIndex);

            log.info("获取班级" + clazzId + "勤奋排行榜成功");
            return ResultUtil.success(map);
        } catch (Exception e) {
            log.error("获取班级" + clazzId + "勤奋排行榜失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取班级人气排行榜接口
     */
    @PostMapping("/getpopularityrank")
    public ResponseEntity getPopularityRank(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");

        try {
            Map<String, Object> map = clazzService.getPopularityRank(userId, clazzId, pageIndex);

            log.info("获取班级" + clazzId + "人气排行榜成功");
            return ResultUtil.success(map);
        } catch (Exception e) {
            log.error("获取班级" + clazzId + "人气排行榜失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 膜拜接口
     */
    @PostMapping("/worship")
    public ResponseEntity worship(@RequestBody JSONObject jsonObject) {
        Integer worshippingUser = jsonObject.getInteger("worshippingUser");
        Integer worshippedUser = jsonObject.getInteger("worshippedUser");

        try {
            if (clazzService.worship(worshippingUser, worshippedUser)) {
                log.info(worshippingUser + "号用户膜拜" + worshippedUser + "号用户成功");
                return ResultUtil.success();
            } else {
                log.error(worshippingUser + "号用户今日已膜拜过" + worshippedUser + "号用户");
                return ResultUtil.error(Errors.WORSHIPPED_TODAY);
            }
        } catch (Exception e) {
            log.error(worshippingUser + "号用户膜拜" + worshippedUser + "号用户失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 收藏接口
     */
    @PostMapping("/collect")
    public ResponseEntity collect(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer studyPlanDay = jsonObject.getInteger("studyPlanDay");
        String clazzName = jsonObject.getString("clazzName");

        try {
            if (clazzService.collect(userId, clazzId, clazzName, studyPlanDay)) {
                log.info(userId + "号用户收藏班级" + clazzId + "第" + studyPlanDay + "天学习计划成功");
                return ResultUtil.success();
            } else {
                log.error(userId + "号用户已收藏过班级" + clazzId + "第" + studyPlanDay + "天学习计划");
                return ResultUtil.error(Errors.COLLECTED_STUDY_PLAN);
            }
        } catch (Exception e) {
            log.error(userId + "号用户收藏班级" + clazzId + "第" + studyPlanDay + "天学习计划失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 取消收藏接口
     */
    @PostMapping("cancelcollection")
    public ResponseEntity cancelCollection(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer studyPlanDay = jsonObject.getInteger("studyPlanDay");

        try {
            clazzService.cancelCollection(userId, clazzId, studyPlanDay);
            log.info(userId + "号用户取消收藏班级" + clazzId + "第" + studyPlanDay + "天学习计划成功");
            return ResultUtil.success();
        } catch (Exception e) {
            log.error(userId + "号用户取消收藏班级" + clazzId + "第" + studyPlanDay + "天学习计划失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取我的收藏接口
     */
    @PostMapping("/getcollection")
    public ResponseEntity getCollection(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");

        try {
            List<CollectionVO> list = clazzService.getCollection(userId);

            log.info("获取" + userId + "号用户我的收藏成功");
            return ResultUtil.success(list);
        } catch (Exception e) {
            log.error("获取" + userId + "号用户我的收藏失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    /**
     * 获取微信小程序码接口
     */
    @PostMapping("/getqrcode")
    public ResponseEntity getQrCode(@RequestBody JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        try {
            Map<String, String> qrCodeMap = new HashMap<>();
            qrCodeMap.put("qrCode", clazzService.getQrCode(userId, clazzId));

            log.info("获取" + clazzId + "号班级主页微信小程序码成功");
            return ResultUtil.success(qrCodeMap);
        } catch (Exception e) {
            log.error("获取" + clazzId + "号班级主页微信小程序码失败");
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

//    /**
//     * 生成邀请卡接口
//     */
//    @PostMapping("/invitationcard")
//    public ResponseEntity invitationCard(@RequestBody JSONObject jsonObject) throws Exception{
//        Integer userId = jsonObject.getInteger("userId");
//        Integer clazzId = jsonObject.getInteger("clazzId");
//
//        try {
//            String poster =  posterService.invitationCard(userId, clazzId);
//            log.info(userId + "号用户生成班级" + clazzId + "邀请卡成功");
//            return ResultUtil.success(poster);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error(userId + "号用户生成班级" + clazzId + "邀请卡失败");
//            return ResultUtil.error();
//        }
//    }
//
//    /**
//     * 生成成就卡接口
//     */
//    @PostMapping("/achievementcard")
//    public ResponseEntity achievementCard(@RequestBody JSONObject jsonObject) throws Exception{
//        Integer userId = jsonObject.getInteger("userId");
//        Integer clazzId = jsonObject.getInteger("clazzId");
//
//        try {
//            String poster =  posterService.achievementCard(userId, clazzId);
//            log.info(userId + "号用户生成班级" + clazzId + "成就卡成功");
//            return ResultUtil.success(poster);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error(userId + "号用户生成班级" + clazzId + "成就卡失败");
//            return ResultUtil.error();
//        }
//    }
}
