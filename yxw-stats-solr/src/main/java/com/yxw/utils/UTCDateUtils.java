package com.yxw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UTCDateUtils {
	
	public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	public static String parseDate(long timestamp) {
		SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_FORMAT);
		return utcFormat.format(new Date(timestamp));
	}
	
	public static String parseDate(String date) {
		SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_FORMAT);
		try {
			return utcFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String parseDate(Date date) {
		SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_FORMAT);
		return utcFormat.format(date);
	}
	
	public static String getIncDay(String date, int day) {
		SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_FORMAT);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date tempDate = df.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(tempDate);
			c.add(Calendar.DATE, day);
			
			return utcFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(parseDate("2010-10-10"));
		System.out.println(parseDate(new Date()));
		System.out.println(getIncDay("2010-10-10", -1));
	}
}
