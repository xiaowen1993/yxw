package com.yxw.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.common.collect.Lists;

public class MD5Utils {

	/*** 获得16位的加密字符 **/
	public static String getMd5String16(String str) throws NoSuchAlgorithmException {
		String md5str = getMd5String32(str).substring(8);
		return md5str.substring(0, md5str.length() - 8);
	}

	/** 获得24位的MD5加密字符 **/
	public static String getMd5String24(String str) throws NoSuchAlgorithmException {

		String md5str = getMd5String32(str).substring(4);
		return md5str.substring(0, md5str.length() - 4);
	}

	/** 获得32位的MD5加密算法 **/
	public static String getMd5String32(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer();
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];

			if (i < 0)
				i += 256;

			if (i < 16)
				buf.append("0");

			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		try {
			List<String> openIds =
					Lists.newArrayList("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");

			for (String openId : openIds) {
				System.out.println(MD5Utils.getMd5String16(openId));
			}

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
	}
}
