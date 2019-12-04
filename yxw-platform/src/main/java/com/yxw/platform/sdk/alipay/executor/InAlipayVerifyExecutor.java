/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.platform.sdk.alipay.exception.MyException;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;

/**
 * 开通服务窗开发者功能处理器
 * 
 * @author taixu.zqq
 * @version $Id: InAlipayOpenExecutor.java, v 0.1 2014年7月24日 下午5:05:13 taixu.zqq
 *          Exp $
 */
public class InAlipayVerifyExecutor implements ActionExecutor {

	private static Logger logger = LoggerFactory.getLogger(InAlipayVerifyExecutor.class);

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayVerifyExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	/**
	 * @see com.alipay.executor.ActionExecutor#executor(java.util.Map)
	 */
	public String execute() throws MyException {
		return this.setResponse();
	}

	/**
	 * 设置response返回数据
	 * 
	 * @return
	 */
	private String setResponse() throws MyException {
		// logger.info("InAlipayVerifyExecutor");
		final String appId = bizContent.getString("AppId");
		if (logger.isDebugEnabled()) {
			logger.debug("接入验证：appId------------" + appId);
		}
		HospIdAndAppSecretVo vo = MessageSendUtils.obtainByAppId(appId);
		if (vo != null) {
			// 固定响应格式，必须按此格式返回
			StringBuilder builder = new StringBuilder();
			builder.append("<success>").append(Boolean.TRUE.toString()).append("</success>");
			builder.append("<biz_content>").append(vo.getPublicKey()).append("</biz_content>");
			if (logger.isDebugEnabled()) {
				logger.debug("builder.toString()----" + builder.toString());
			}
			return builder.toString();
		} else {
			logger.error("无法获取该服务窗的公钥");
			return "";
		}
	}
}
