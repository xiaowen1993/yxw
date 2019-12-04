package com.yxw.platform.sdk.alipay.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StrUtils {
	static final int GB_SP_DIFF = 160;
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y',
			'z' };

	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) {
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	static char convert(byte[] bytes) {

		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	public static String ifBlank(String... ss) {
		String ret = "";
		for (String s : ss) {
			if (isNotBlank(s)) {
				ret = s;
				break;
			}
		}
		return ret;
	}

	public static boolean isTrue(String str) {
		return Boolean.parseBoolean(str);
	}

	public static String repalceMap(String str, Map<String, String> params) {

		String regex = "\\{x:([^:\\{\\}]+)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			String key = matcher.group(1);
			String value = params.containsKey(key) ? params.get(key).toString() : "";

			matcher.appendReplacement(buffer, "");
			buffer.append(value);
		}

		matcher.appendTail(buffer);

		return buffer.toString();
	}

	public static boolean isNull(String str) {
		return "".equals(str) || str == null;
	}

	public static String privacyInfo(String currString) {

		String targetString = "";
		if (!StringUtils.isBlank(currString)) {
			if (currString.length() <= 3) {
				targetString = currString.replace(StringUtils.substring(currString, 0, 1), "*");
			} else if (currString.length() > 3) {
				targetString = currString.replace(StringUtils.substring(currString, 0, 2), "*");
			}
		}

		return targetString;
	}
}
