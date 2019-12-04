package com.yxw.mobileapp.biz.msgpush.thread;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgpush.service.MsgPushService;

public class OutsideMessagePushRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(OutsideMessagePushRunnable.class);
	private MsgPushService msgPush = SpringContextHolder.getBean(MsgPushService.class);

	private BaseEntity params;

	private String appId;
	private String templateCode;
	private String platformType;
	private String openId;
	private Map<String, Serializable> paramMap;

	public OutsideMessagePushRunnable(String appId, String templateCode, String platformType, String openId, BaseEntity params,
			Map<String, Serializable> paramMap) {
		super();
		this.appId = appId;
		this.templateCode = templateCode;
		this.platformType = platformType;
		this.openId = openId;
		this.params = params;
		this.paramMap = paramMap;
	}

	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("OutsideMessagePushRunnable is start.messageParams:{}", JSON.toJSONString(params));
		}

		if (params instanceof MsgTemplate) {
			MsgTemplate msgTemplate = (MsgTemplate) params;

			//			msgPush.templatePush(appId, templateCode, platformType, openId, Collections.EMPTY_MAP, msgTemplate);
			msgPush.msgPush(appId, templateCode, platformType, openId, paramMap, String.valueOf(msgTemplate.getMsgTarget()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("OutsideMessagePushRunnable is end..................");
		}
	}
}
