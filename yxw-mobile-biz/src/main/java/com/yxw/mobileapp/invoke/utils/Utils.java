/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.thoughtworks.xstream.XStream;
import com.yxw.commons.dto.inside.PaidMZDetailCommParams;

/**
 * @Package: com.yxw.mobileapp.invoke.utils
 * @ClassName: Utils
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Utils {

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * 解析入参实体参数
	 */
	private static final String XML_ENTITY = "entity";

	/**
	 * 解析入参实体开头
	 */
	private static final String XML_HEAD = "<" + XML_ENTITY + ">";

	/**
	 * 解析入参实体结尾
	 */
	private static final String XML_FOOT = "</" + XML_ENTITY + ">";

	/**
	 * 判断是否是正整数
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证字符 是否是时间格式
	 * @param str
	 * @param format
	 * @return
	 */
	public static boolean validDate(String str, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			Date date = (Date) formatter.parse(str);
			return str.equals(formatter.format(date));
		} catch (Exception e) {
			return false;
		}
	}

	/** 
	 * 获取字符串的长度，对双字符（包括汉字）按两位计数 
	 *  
	 * @param value 
	 * @return 
	 */
	public static int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 参数转义
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 */
	public static <T> T xmlToObject(String xml, Class<T> clazz) {
		XStream stream = new XStream();
		StringBuffer sb = new StringBuffer();
		sb.append(XML_HEAD);
		sb.append(xml);
		sb.append(XML_FOOT);
		// System.out.println(sb.toString());
		stream.alias(XML_ENTITY, clazz);
		return (T) stream.fromXML(sb.toString());
	}
	
	/**
	 *  xml字符串转成对象
	 * @param xml
	 * @param clazz
	 * @return
	 */
	public static <T> T xmlStrToObject(String xml, Class<T> clazz) {
		XStream stream = new XStream();
		return (T) stream.fromXML(xml);
	}
	

	public static void main(String[] args) {
		//System.out.println(validDate("2015-08-11 23:05:09", "yyyy-MM-dd HH:mm:ss"));
//		String xml= 
//		  "<appId>wx7759862b6d224820</appId>"+
//		  "<appCode>wechat</appCode>"+
//		  "<openId>oSJHhsgW14Kh_Xtg43-BK0tDO1_8</openId>"+
//		  "<hospitalCode>gzhszhyy</hospitalCode>"+
//		  "<orderNo>Y1201712091515102043461</orderNo>"+
//		  "<mzFeeId>16553417</mzFeeId>";
		PaidMZDetailCommParams params=new PaidMZDetailCommParams();
//		 JSONObject json = JSONObject.fromObject(params);
//         XMLSerializer xmlSerializer = new XMLSerializer();
//         String xml = xmlSerializer.write(json, "UTF-8");
		params.setAppCode("111");
         XStream stream = new XStream();
 		String xml =stream.toXML(params);
         
 		params=(PaidMZDetailCommParams) stream.fromXML(xml);
 		
		System.out.println(params.getAppCode());
	}
}
