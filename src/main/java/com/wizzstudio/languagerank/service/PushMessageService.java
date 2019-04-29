package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/29.
*/

import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;

public interface PushMessageService {
    /**
     * 每天定时发送小程序模板消息给用户: 使用WxMaService封装好的轮子
     */
    void sendTemplateMsg(PunchReminderTimeEnum reminderTime);
}
