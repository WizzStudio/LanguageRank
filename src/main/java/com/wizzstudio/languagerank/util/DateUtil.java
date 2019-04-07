package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/4/7.
*/

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date getNextDate(Date date) throws ParseException {
        long addTime = 1;// 以1为基数
        addTime *= 1;    // 1天
        addTime *= 24;   // 一天24小时
        addTime *= 60;   // 1小时60分钟
        addTime *= 60;   // 1分钟60秒
        addTime *= 1000; // 1秒1000毫秒

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateFormat.format(new Date(date.getTime() + addTime)));
    }
}
