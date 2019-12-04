package com.yxw.payrefund.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author homer
 * 
 * @1 yyyy-MM-dd'T'HH:mm:ss 中间加T
 * @2 yyyy-MM-dd HH:mm:ss EEEE 年月日时分秒星期
 *
 */

public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    
    public static String formatDate(String value, String srcFormat, String toFormat) {
        SimpleDateFormat srcSDF = new SimpleDateFormat(srcFormat);
        SimpleDateFormat toSDF = new SimpleDateFormat(toFormat);
        
        try {
			return toSDF.format(srcSDF.parse(value));
		} catch (ParseException e) {
			logger.error("时间格式换出错，已返回当前时间：value: {}, srcFormat: {}, toFormat: {}", value, srcFormat, toFormat);
			return toSDF.format(new Date());
		}
    }
    
    public static String formatDate(Long timeMillis, String format) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        
        return formatDate(calendar.getTime(), format);
    }
    
    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date == null ? new Date() : date);
    }
    
    public static Date toDate(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }
    
    public static int getAge(Date birthDay) {
        Calendar calendar = Calendar.getInstance();
        
        if (calendar.before(birthDay)) {
            throw new IllegalArgumentException("出生时间大于当前时间");
        }
        
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(birthDay);
        
        int yearBirth = calendar.get(Calendar.YEAR);
        int monthBirth = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);
        
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        
        return age;
    }
    
    public static void main(String[] args) throws Exception {
        Date d = new Date();
        
        System.out.println(formatDate(d, "yyyy-MM-dd'T'HH:mm:ss"));
        System.out.println(formatDate(d, "yyyy-MM-dd HH:mm:ss EEEE"));
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        
        // 显示年份
        int year = calendar.get(Calendar.YEAR);
        System.out.println("YEAR is = " + String.valueOf(year));
        
        // 显示月份 (从0开始, 实际显示要加一)
        int MONTH = calendar.get(Calendar.MONTH);
        System.out.println("MONTH is = " + (MONTH + 1));
        
        // 今年的第 N 天
        int DAY_OF_YEAR = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println("DAY_OF_YEAR is = " + DAY_OF_YEAR);
        
        // 本月第 N 天
        int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("DAY_OF_MONTH = " + String.valueOf(DAY_OF_MONTH));
        
        // 3天以后
        calendar.add(Calendar.DATE, 3);
        int DATE = calendar.get(Calendar.DATE);
        System.out.println("DATE + 3 = " + DATE);
        
        // 3小时以后
        calendar.add(Calendar.HOUR_OF_DAY, 3);
        int HOUR_OF_DAY = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println("HOUR_OF_DAY + 3 = " + HOUR_OF_DAY);
        
        // 当前分钟数
        int MINUTE = calendar.get(Calendar.MINUTE);
        System.out.println("MINUTE = " + MINUTE);
        
        // 15 分钟以后
        calendar.add(Calendar.MINUTE, 15);
        MINUTE = calendar.get(Calendar.MINUTE);
        System.out.println("MINUTE + 15 = " + MINUTE);
        
        // 30分钟前
        calendar.add(Calendar.MINUTE, -30);
        MINUTE = calendar.get(Calendar.MINUTE);
        System.out.println("MINUTE - 30 = " + MINUTE);
        
        
        // 计算我的年龄
        String myBirth = "19910629";
        Date myBirthDate = toDate(myBirth, "yyyyMMdd");
        System.out.println("my Age = " + getAge(myBirthDate));
        
    }
}
