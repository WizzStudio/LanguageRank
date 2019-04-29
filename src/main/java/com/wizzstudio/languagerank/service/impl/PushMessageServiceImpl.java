package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/29.
*/

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.domain.User.User;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import com.wizzstudio.languagerank.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService, Constant {
    @Autowired
    WxMaService wxService;
    @Autowired
    UserDAO userDAO;

    @Override
    public void sendTemplateMsg(PunchReminderTimeEnum reminderTime) {
        WxMaMsgService msgService = wxService.getMsgService();
        List<User> userList = userDAO.findUsersRemindAtWhen(reminderTime);
        for (User user : userList) {
            List<WxMaTemplateData> data = Arrays.asList(
                    new WxMaTemplateData("keyword1", "来猿圈打卡，记录你的学习"),
                    // 用户打卡总天数
                    new WxMaTemplateData("keyword2", user.getTotalPunch().toString()),
                    // 用户总积分
                    new WxMaTemplateData("keyword3", user.getTotalScore().toString()),
                    new WxMaTemplateData("keyword4", "12点后打卡积分减半哦"),
                    new WxMaTemplateData("keyword5", "放弃，意味着失败")
            );

            WxMaTemplateMessage templateMessage =
                    WxMaTemplateMessage.builder()
                    .toUser(user.getOpenId())
                    .formId(user.getFormId())
                    .templateId(Constant.TEMPLATE_ID)
                    .page(Constant.PAGE_PATH)
                    .data(data)
                    .build();

            try {
                msgService.sendTemplateMsg(templateMessage);
                log.info(user.getUserId() + "号用户消息推送成功");
            } catch (WxErrorException e) {
                log.error(user.getUserId() + "号用户消息推送失败");
                e.printStackTrace();
            }
        }
    }
}
