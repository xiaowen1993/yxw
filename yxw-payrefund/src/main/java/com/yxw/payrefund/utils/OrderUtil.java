package com.yxw.payrefund.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class OrderUtil {

	/**
	 * 获取随机字符串，uuid >> md5 加密（大写）
	 * 
	 * @param type
	 * @guid32：返回32位， @guid16：返回16位， else：直接返回uuid
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String genGUID(String type) {

		String uuid = UUID.randomUUID().toString();

		if ("@guid32".equals(type)) {
			return DigestUtils.md5Hex(uuid).toUpperCase();
		}
		if ("@guid16".equals(type)) {
			return DigestUtils.md5Hex(uuid).toUpperCase().substring(8, 24);
		} else {
			return uuid;
		}

	}

	public static String ifBlank(String... ss) {
		String ret = "";
		for (String s : ss) {
			if (StringUtils.isNotBlank(s)) {
				ret = s;
				break;
			}
		}
		return ret;
	}

	/*
	 * 返回长度为【strLength <= 15】的随机数，在前面补0
	 */
	public static String getFixLenthString(int strLength) {

		Random rm = new Random();

		// 获得随机数
		double pross = ( 1 + rm.nextDouble() ) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		// 返回固定的长度的随机数
		int dianIndex = fixLenthString.indexOf(".");
		return fixLenthString.substring(dianIndex + 1, strLength + dianIndex + 1);
	}
}
