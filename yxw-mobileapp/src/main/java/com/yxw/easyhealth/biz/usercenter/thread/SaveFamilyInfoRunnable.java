package com.yxw.easyhealth.biz.usercenter.thread;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

public class SaveFamilyInfoRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SaveFamilyInfoRunnable.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);

	private EasyHealthUser user;

	public SaveFamilyInfoRunnable() {
		super();
	}

	public SaveFamilyInfoRunnable(EasyHealthUser user) {
		super();
		this.user = user;
	}

	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("SaveFamilyInfoRunnable: " + JSON.toJSONString(user));
		}

		Map<String, Object> resultMap = familyService.saveFamilyInfo(user);
		logger.info(JSON.toJSONString(resultMap));
	}

}
