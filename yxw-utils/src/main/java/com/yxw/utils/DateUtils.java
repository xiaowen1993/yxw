/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月28日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */

package com.yxw.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author 谢家贵
 * @version 1.0
 */

public class DateUtils {

	/**
	 * 将日期格式化转成字符串
	 * 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateformat.format(date);
		String strDate = dateformat.format(date);
		return strDate;

	}

	/**
	 * 将日期格式化转成字符串
	 * 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		dateformat.format(date);
		String strDate = dateformat.format(date);
		return strDate;

	}

	/**
	 * 将字符串转成日期
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static Date StringToDate(String dateStr) {
		SimpleDateFormat sdf = null;
		Date date = null;
		if (dateStr.length() > 10) {
			if (dateStr.indexOf("T") > -1) {
				dateStr = dateStr.replace("T", " ");
			}
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			date = sdf.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

	/**
	 * 将字符串转成日期
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static Date StringToDateYMD(String dateStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

	public static String dateToStringYMD(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		dateformat.format(date);
		String strDate = dateformat.format(date);
		return strDate;

	}

	/**
	 * 用日期加五位随机数作为文件名
	 * 
	 * @param fileName
	 *            文件名
	 * @return String
	 */
	public static String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());

		int random = new Random().nextInt(10000);

		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);

		return formatDate + random + extension;
	}

	/**
	 * 取昨天日期
	 * 
	 * @return
	 */
	public static Date yesterdayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return StringToDate(sdf.format(c.getTime()));
	}

	/**
	 * 取今天日期
	 * 
	 * @return String
	 */
	public static String today() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		return sdf.format(c.getTime());
	}

	/**
	 * 取今天日期
	 * 
	 * @return String
	 */
	public static String toChday() {
		Date d = new Date();
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		SimpleDateFormat sdfd = new SimpleDateFormat("dd");
		return sdfy.format(d) + "年" + sdfm.format(d) + "月" + sdfd.format(d) + "日";
	}

	/**
	 * 取明天日期
	 * 
	 * @return String
	 */
	public static String tomorrowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, +1);
		return sdf.format(c.getTime());
	}

	/**
	 * 取第n天日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getDayDate(Integer day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);
		return sdf.format(c.getTime());
	}

	/**
	 * 求两个日期相差天数
	 * 
	 * @param d1
	 * @param d2
	 * @return long
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				// 得到当年的实际天数
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 日期转换成日历格式
	 * 
	 * @param date
	 * @return Calendar
	 */
	public static Calendar date2Calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 一年的第几周
	 * 
	 * @return int
	 */
	public static int YearOfWeekDay() {
		@SuppressWarnings("unused")
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 一月的第几周
	 * 
	 * @return int
	 */
	public static int MonthOfWeekDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 两个日期相隔天数
	 * 
	 * @param startday
	 * @param endday
	 * @return int
	 */
	public int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) ( ei / ( 1000 * 60 * 60 * 24 ) );
	}

	/**
	 * 返回两个日期相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return long
	 * @throws ParseException
	 */
	public static long DateDiff(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = ( timeend - timestart ) / ( 1000 * 60 * 60 * 24 );
		return totalDate;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		// 保存改后的时间
		String ymd = " ";
		if (date == null || "".equals(date) || "1900-01-01 00:00:00.0".equals(date)) {
			return ymd;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ymd = dateFormat.format(date);
		return ymd;
	}

	public static Date getNextMonth(Date nowdate, int delay) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar temp = Calendar.getInstance();
		temp.setTime(nowdate);
		int oldMonth = temp.get(Calendar.MONTH);
		int newMonth = oldMonth + delay;
		int oldYear = temp.get(Calendar.YEAR);
		int i, j;
		if (newMonth > 12) {
			i = newMonth % 12;
			j = newMonth / 12;
			temp.set(Calendar.MONTH, i);
			temp.set(Calendar.YEAR, j + oldYear);
		} else {
			temp.set(Calendar.MONTH, newMonth);
		}

		temp.set(Calendar.DATE, temp.get(Calendar.DATE));
		String dateString = formatter.format(temp.getTime());
		Date strtodate = null;
		try {
			strtodate = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strtodate;
	}

	public static String formatDate(String str, String sourceFormat, String desFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
		try {
			Date date = formatter.parse(str);
			formatter = new SimpleDateFormat(desFormat);
			return formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 获取当天开始时间 即零点
	 * 
	 * @return
	 */
	public static Date getDateBegin(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(getDateStr(date) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当天开始时间 即零点
	 * 
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(getDateStr(date) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取第n天日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getDayForDate(Date date, Integer day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return sdf.format(c.getTime());
	}

	/**
	 * 昨天
	 * 
	 * @return
	 */
	public static String getYesterday(String date) {
		return DateUtils.getDayForDate(StringToDateYMD(date), -1);
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfThisMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		// calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	public static String getFirstDayOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	public static String getFirstDayNextMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	public static String getFirstDayOfMonth(String date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		calendar.add(Calendar.MONTH, months);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	/**
	 * 获取本年第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfThisYear(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		// calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.MONTH, 0); // 0~11
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	/**
	 * 获取上月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfLastMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		// calendar.add(Calendar.MONTH, 0);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	/**
	 * 获取上周第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfLastWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		// calendar.add(Calendar.MONTH, 0);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	/**
	 * 获取本周第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfThisWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateYMD(date));
		// calendar.add(Calendar.MONTH, 0);
		// calendar.add(Calendar.WEEK_OF_YEAR, 0);
		// 从星期一开始，则需要日期减一，星期减一，最后再加一天
		calendar.add(Calendar.WEEK_OF_YEAR, 0);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
		return DateUtils.formatDate(calendar.getTime());
	}

	public static void main(String[] args) {
		System.out.println(getFirstDayOfMonth("2015-12-21", -1));
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMinimumFractionDigits(2);
		System.out.println(format.format((long) 5 / (long) 13));
	}
}
