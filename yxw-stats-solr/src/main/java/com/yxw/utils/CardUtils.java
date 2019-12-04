package com.yxw.utils;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CardUtils.class);
	
	/**
	 * 从身份证中获取性别
	 * 特殊：	有些数据可能是15位了，这里不做处理了
	 * @param idNo -- 18位身份证
	 * @return
	 */
	public static int getSexByIdNo(String idNo) {
		if (StringUtils.isNotBlank(idNo) && idNo.length() == 18) {
			return Integer.valueOf(idNo.substring(16, 17)) % 2 == 0 ? 2 : 1;
		} else {
			return 0;
		}
	}

	public static int getAgeByIdNo(String idNo) {
		String birth = getBirthByIdNo(idNo);
		return getAgeByBirth(birth);
	}

	/**
	 * 从身份证中获取出生日期
	 * @param idNo	-- 18位身份证
	 * @return
	 */
	public static String getBirthByIdNo(String idNo) {
		if (StringUtils.isNotBlank(idNo) && idNo.length() == 18) {
			return idNo.substring(6, 10) + "-" + idNo.substring(10, 12) + "-" + idNo.substring(12, 14);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param birth
	 * @return 	-1:异常or脏数据
	 * 			其他：正常数据 [0岁，即未满一周岁]
	 */
	public static int getAgeByBirth(String birth) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long betweens = System.currentTimeMillis() - df.parse(birth).getTime();
			if (betweens < 0) {
				// 这种就是脏数据，出生日期与当前时间一样or大于当前时间
				return -1;
			} else {
				return Long.valueOf((betweens / (1000 * 60 * 60 * 24) / 365)).intValue();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
}
