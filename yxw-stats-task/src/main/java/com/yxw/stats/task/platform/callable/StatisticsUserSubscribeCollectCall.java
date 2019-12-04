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
package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.UserSubscribe;
import com.yxw.stats.service.platform.UserSubscribeService;

public class StatisticsUserSubscribeCollectCall implements Callable<List<UserSubscribe>> {
	public static Logger logger = LoggerFactory.getLogger(StatisticsUserSubscribeCollectCall.class);
	private UserSubscribeService userSubscribeService = SpringContextHolder.getBean(UserSubscribeService.class);

	private String beginDate;
	private String hospitalId;
	private String platformMode;
	private String appId;
	private String appSecret;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getPlatformMode() {
		return platformMode;
	}

	public void setPlatformMode(String platformMode) {
		this.platformMode = platformMode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public StatisticsUserSubscribeCollectCall(String beginDate, String hospitalId, String platformMode, String appId, String appSecret) {
		super();
		this.beginDate = beginDate;
		this.hospitalId = hospitalId;
		this.platformMode = platformMode;
		this.appId = appId;
		this.appSecret = appSecret;
	}

	@Override
	public List<UserSubscribe> call() throws Exception {
		List<UserSubscribe> result = userSubscribeService.findUserSubscribes(beginDate, hospitalId, platformMode, appId, appSecret);
		return result;
	}

}
