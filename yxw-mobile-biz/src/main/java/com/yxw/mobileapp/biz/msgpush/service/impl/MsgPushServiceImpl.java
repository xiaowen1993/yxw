/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.msgpush.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateFunction;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgcenter.service.MsgCenterService;
import com.yxw.mobileapp.biz.msgpush.service.MsgPushService;
import com.yxw.mobileapp.datas.manager.MsgTempManager;

/**
 * 消息推送
 * 
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgPushServiceImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class MsgPushServiceImpl implements MsgPushService {
	protected static Logger logger = LoggerFactory.getLogger(MsgPushServiceImpl.class);
	@Autowired
	private MsgTempManager msgManager;

	private MsgCenterService msgCenterService = SpringContextHolder.getBean(MsgCenterService.class);

	@Override
	public void msgPush(String appId, String templateCode, String appCode, String openId, Map<String, Serializable> paramMap,
			String msgTarget) {
		MsgTemplate msgTemplate = null;

		String platformMode = String.valueOf(ModeTypeUtil.getPlatformModeType(appCode));
		msgTemplate = msgManager.getMsgTemplate(appId, templateCode, platformMode, msgTarget);

		if (msgTemplate != null) {
			appMsgTemplatPush(appId, openId, templateCode, appCode, paramMap, msgTemplate, msgTarget);
		}

	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年3月26日 
	 * @param appId
	 * @param userId
	 * @param templateCode
	 * @param appCode
	 * @param paramMap
	 * @param msgTemplate 
	 */
	@Override
	public void appMsgTemplatPush(String appId, String userId, String templateCode, String appCode, Map<String, Serializable> paramMap,
			MsgTemplate msgTemplate, String msgTarget) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<MsgTemplateContent> msgTemplateContents = new ArrayList<MsgTemplateContent>();
		List<MsgTemplateFunction> msgTemplateFunctions = new ArrayList<MsgTemplateFunction>();

		if (msgTemplate.getMsgTemplateContents().size() > 0) {
			for (MsgTemplateContent msgTemplateContent : msgTemplate.getMsgTemplateContents()) {
				for (String key : paramMap.keySet()) {
					msgTemplateContent.setValue(msgTemplateContent.getValue().replaceAll("%" + key + "%",
							Matcher.quoteReplacement(String.valueOf(paramMap.get(key)))));
				}
				msgTemplateContents.add(msgTemplateContent);
			}
			msgTemplate.setMsgTemplateContents(msgTemplateContents);
		}

		if (msgTemplate.getMsgTemplateFunctions().size() > 0) {
			for (MsgTemplateFunction msgTemplateFunction : msgTemplate.getMsgTemplateFunctions()) {
				for (String key : paramMap.keySet()) {
					msgTemplateFunction.setFunctionCode(msgTemplateFunction.getFunctionCode().replaceAll("%" + key + "%",
							Matcher.quoteReplacement(String.valueOf(paramMap.get(key)))));
				}
				msgTemplateFunctions.add(msgTemplateFunction);
			}
		}

		HospIdAndAppSecretVo hospIdAndAppSecretVo = null;
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> cacheParams = new ArrayList<Object>();
		cacheParams.add(appId);
		cacheParams.add(appCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", cacheParams);
		if (CollectionUtils.isNotEmpty(results)) {
			hospIdAndAppSecretVo = (HospIdAndAppSecretVo) results.get(0);
		}

		if (hospIdAndAppSecretVo != null) {
			String jsonStr = JSON.toJSONString(msgTemplate);
			MsgCenter msgCenter =
					new MsgCenter(userId, msgTemplate.getId(), msgTemplate.getIconName(), msgTemplate.getIconPath(),
							hospIdAndAppSecretVo.getHospId(), hospIdAndAppSecretVo.getHospName(), msgTemplate.getTitle(), 1, jsonStr);
			msgCenterService.add(msgCenter);
		} else {
			logger.error("hospIdAndAppSecretVo is null! appId: {}. appCode: {}.", new Object[] { appId, appCode });
		}

	}
}
