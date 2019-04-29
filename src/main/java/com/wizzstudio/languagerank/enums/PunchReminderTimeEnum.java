package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/4/28.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  PunchReminderTimeEnum {
    /**
     * 不提醒
     */
    NOT_REMIND(0),
    /**
     * 每日8点提醒
     */
    EIGHT(8),
    /**
     * 每日9点提醒
     */
    NINE(9),
    /**
     * 每日10点提醒
     */
    TEN(10),
    /**
     * 每日11点提醒
     */
    ELEVEN(11);

    private Integer reminderTime;
}
