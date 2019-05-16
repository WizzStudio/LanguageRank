package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/4/7.
*/

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getNextDate(Date date) throws ParseException {
        // 以1为基数
        long addTime = 1;
        // 1天
        addTime *= 1;
        // 一天24小时
        addTime *= 24;
        // 1小时60分钟
        addTime *= 60;
        // 1分钟60秒
        addTime *= 60;
        // 1秒1000毫秒
        addTime *= 1000;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateFormat.format(new Date(date.getTime() + addTime)));
    }

    public static Date getTwelveToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }
}
