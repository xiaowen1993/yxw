/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-13</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.msgpush.thread;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.vo.MessagePushParamsVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgpush.service.MsgPushService;

/**
 * @Package: com.yxw.mobileapp.common.thread
 * @ClassName: MessagePushRunnable
 * @Statement: <p>消息推送处理线程</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MessagePushRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(MessagePushRunnable.class);
	private MsgPushService msgPush = SpringContextHolder.getBean(MsgPushService.class);

	private MessagePushParamsVo params;

	public MessagePushRunnable(MessagePushParamsVo params) {
		super();
		this.params = params;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("MessagePushRunnable is start.messageParams:{}", JSON.toJSONString(params));
		}

		String appId = params.getAppId();
		String templateCode = params.getTemplateCode();
		// 新增一个msgTarget
		String msgTarget = params.getMsgTarget();
		// platformType 作为source，通用了。
		//		String platformMode = String.valueOf(ModeTypeUtil.getPlatformModeType(params.getPlatformType()));
		String appCode = params.getPlatformType();
		String openId = params.getOpenId();
		Map<String, Serializable> paramMap = params.getParamMap();

		msgPush.msgPush(appId, templateCode, appCode, openId, paramMap, msgTarget);

		if (logger.isDebugEnabled()) {
			logger.debug("MessagePushRunnable is end..................");
		}
	}
}
