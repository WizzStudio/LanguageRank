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
    /**
     * 获取明天的日期
     */
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

    /**
     * 获取当天中午12点的Date对象
     */
    public static Date getTwelveToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 判断date对象表示的时间是否为今天
     */
    public static Boolean isToday(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // date对象表示的时间
        String param = sdf.format(date);
        // 当前时间
        String now = sdf.format(new Date());

        return param.equals(now);
    }
}
