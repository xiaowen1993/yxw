/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.thread;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.config.SystemConfig;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: AutoFocusDoctorRunnable
 * @Statement: <p>
 *             子线程自动关注医生
 *             </p>
 * @JDK version used: 1.7
 * @Author: Luob
 * @Create Date: 2015-7-10
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AutoFocusDoctorRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(AutoFocusDoctorRunnable.class);

	private RegisterRecord record;

	private final String SECRET = SystemConfig.getStringValue("ask_doctor_secret");
	private final String ASK_DOCTOR_URL = SystemConfig.getStringValue("ask_doctor_url");

	public AutoFocusDoctorRunnable() {
		super();
	}

	public AutoFocusDoctorRunnable(RegisterRecord record) {
		super();
		this.record = record;
	}

	@Override
	public void run() {
		if (record != null) {
			focusDoctor(record);
		}
	}

	/**
	 * 自动关注医生
	 * 
	 * @param record
	 * */
	public void focusDoctor(RegisterRecord record) {
		/*		List<PostParameter> askParams = new ArrayList<PostParameter>();
				askParams.add(new PostParameter("controller", "Notice"));
				askParams.add(new PostParameter("action", "doNotice"));
				askParams.add(new PostParameter("secret", SECRET));
				askParams.add(new PostParameter("doctor_name", record.getDoctorName()));
				logger.info("AutoFocusDoctorRunnable.doctor_name" + record.getDoctorName());
				askParams.add(new PostParameter("doctor_code", record.getDoctorCode()));
				logger.info("AutoFocusDoctorRunnable.doctor_code" + record.getDoctorCode());
				askParams.add(new PostParameter("appid", record.getAppId()));
				logger.info("AutoFocusDoctorRunnable.appid" + record.getAppId());
				askParams.add(new PostParameter("patName", record.getPatientName()));
				logger.info("AutoFocusDoctorRunnable.patName" + record.getPatientName());
				askParams.add(new PostParameter("patSex", record.getPatientSex() == 1 ? "M" : "F"));
				logger.info("AutoFocusDoctorRunnable.patSex" + record.getPatientSex());
				askParams.add(new PostParameter("patMobile", record.getPatientMobile()));
				logger.info("AutoFocusDoctorRunnable.patMobile" + record.getPatientMobile());
				askParams.add(new PostParameter("from", "挂号后自动关注"));
				HttpResponse resp = new HttpClientUtil().post(ASK_DOCTOR_URL, askParams);*/
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("controller", "Notice");
		reqParams.put("action", "doNotice");
		reqParams.put("secret", SECRET);
		reqParams.put("doctor_name", record.getDoctorName());
		reqParams.put("doctor_code", record.getDoctorCode());
		reqParams.put("appid", record.getAppId());
		reqParams.put("patName", record.getPatientName());
		reqParams.put("patSex", record.getPatientSex() == 1 ? "M" : "F");
		reqParams.put("patMobile", record.getPatientMobile());
		reqParams.put("from", "挂号后自动关注");

		String responseString = HttpClientUtil.getInstance().post(ASK_DOCTOR_URL, reqParams);

		if (StringUtils.isNotBlank(responseString)) {
			JSONObject result = JSONObject.parseObject(responseString);
			int code = result.getIntValue("code");
			String error = result.getString("msg");
			if (code == 40000) {
				logger.info("AutoFocusDoctorRunnable.挂号后自动关注成功");
			} else {
				logger.info("AutoFocusDoctorRunnable.错误代码：" + code + "，错误信息:" + error);
			}
		} else {
			logger.info("AutoFocusDoctorRunnable.挂号后关注医生线程-----问医生post请求异常");
		}
	}
}
