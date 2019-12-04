/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-22</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.utils;

import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.google.common.collect.Lists;

/**
 * @Package: com.yxw.framework.utils
 * @ClassName: PinyinUtils
 * @Statement: <p>中文转拼音工具类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PinyinUtils {
	/** 
	 * 获取汉字串拼音首字母，英文字符不变 
	 * @param chinese 汉字串 
	 * @return 汉语拼音首字母 
	 */
	public static String getChineseFirstWord(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] word = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (word != null) {
						pybf.append(word[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/** 
	 * 获取汉字串拼音，英文字符不变 
	 * @param chinese 汉字串 
	 * @return 汉语拼音 
	 */
	public static String getChineseAllWord(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	public static void main(String[] args) {

		List ls = Lists.newArrayList("佛山市第一人民医院", "广州医科大学附属第二医院");
		for (int i = 0; i < ls.size(); i++) {
			System.out.println(getChineseFirstWord((String) ls.get(i)));
		}

	}
}
