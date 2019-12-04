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
package com.yxw.easyhealth.biz.user.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.config.SystemConfig;

/**
 * @Package: com.yxw.easyhealth.biz.user.utils
 * @ClassName: SmsCode
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月12日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SmsCode {

	private static Logger logger = LoggerFactory.getLogger(SmsCode.class);
	private static String SMS_CODE_URL = SystemConfig.getStringValue("sms_code_url");
	private static String SMS_CODE_ACCOUNT = SystemConfig.getStringValue("sms_code_account");
	private static String SMS_CODE_PASSWORD = SystemConfig.getStringValue("sms_code_password");
	public static String SMS_CODE_FAILURE_TIME = SystemConfig.getStringValue("sms_code_failure_time");

	public static final String RESULT_CODE = "code";
	public static final String RESULT_MSG = "msg";
	public static final String RESULT_SMSID = "smsid";

	/**
	 * 发送验证码
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static Map<String, Object> sendCode(String mobile, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(SMS_CODE_URL);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {//提交短信
				new NameValuePair("account", SMS_CODE_ACCOUNT), new NameValuePair("password", SMS_CODE_PASSWORD), //密码可以使用明文密码或使用32位MD5加密
						//new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
						new NameValuePair("mobile", mobile),
						new NameValuePair("content", content), };

		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			String submitResult = method.getResponseBodyAsString();
			logger.debug("短信发送，mobile：{}, content:{}, submitResult:{}", new Object[] { mobile, content, submitResult });
			Document doc = DocumentHelper.parseText(submitResult);
			Element root = doc.getRootElement();
			map.put(RESULT_CODE, root.elementText("code"));
			map.put(RESULT_MSG, root.elementText("msg"));
			map.put(RESULT_SMSID, root.elementText("smsid"));
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    method.releaseConnection();
		}
		return map;
	}

	/**
	 * 生成验证码
	 * @return
	 */
	public static String getCode() {
		String code = String.valueOf(Math.abs(PKGenerator.generateId().hashCode()));
		int i = code.length();
		if (i < 6) {
			code += "000000";
		}
		return code.substring(0, 6);
	}
}
