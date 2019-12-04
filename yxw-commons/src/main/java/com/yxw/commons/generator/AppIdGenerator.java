package com.yxw.commons.generator;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.utils.DateUtils;

public class AppIdGenerator {

	private static Logger logger = LoggerFactory.getLogger(AppIdGenerator.class);

	public static String genAppId(String hospitalId, String appCode) {
		String appId = null;

		try {
			appId = DateUtils.dateToString(new Date(), "yyyyMMdd").concat(String.valueOf(Math.abs(hospitalId.concat(appCode).hashCode())));

		} catch (Exception e) {
			logger.error("generator appId is occured", e);
		}

		return appId;
	}

}
