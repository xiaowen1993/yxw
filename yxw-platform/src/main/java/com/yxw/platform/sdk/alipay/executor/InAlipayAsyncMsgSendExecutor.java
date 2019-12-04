/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.platform.hospital.service.MenuService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;

/**
 * 菜单点击异步响应执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayAsyncMsgSendExecutor.java, v 0.1 Jul 24, 2014 4:30:38
 *          PM baoxing.gbx Exp $
 */
public class InAlipayAsyncMsgSendExecutor implements ActionExecutor {

	/** 线程池 */
	private static ExecutorService executors = Executors.newSingleThreadExecutor();

	@Autowired
	private MixedMeterialService mixedMeterialService;

	@Autowired
	private MenuService menuService;

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayAsyncMsgSendExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayAsyncMsgSendExecutor() {
		super();
	}

	public String execute() {

		// 取得发起请求的支付宝账号id
		final String fromUserId = bizContent.getString("FromUserId");
		final String actionParam = bizContent.getString("ActionParam");
		final String appId = bizContent.getString("AppId");
		HospIdAndAppSecretVo hospIdAndAppSecretVo = MessageSendUtils.obtainByAppId(appId);
		// 1. 首先同步响应一个消息
		String syncResponseMsg = AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
		Menu menu = menuService.findById(actionParam);
		if (menu != null) {
			List<MixedMeterial> mixedMeterialList = mixedMeterialService.findMixedMeterialsByIds(new String[] { menu.getGrapicsId() });
			if (mixedMeterialList != null && mixedMeterialList.size() > 0) {
				MixedMeterial mixedMeterial = mixedMeterialList.get(0);
				// 2. 异步发送消息
				if (mixedMeterial.getType() == MessageConstant.METERIAL_TYPE_SINGLE) {
					MessageSendUtils.sendSingleImageText(fromUserId, mixedMeterial.getDescription(), hospIdAndAppSecretVo.getAppSecret(), appId,
							mixedMeterial.getCoverPicPath(), mixedMeterial.getTitle(), mixedMeterial.getLink());
				} else {
					MessageSendUtils.sendMultiImageText(fromUserId, hospIdAndAppSecretVo.getAppSecret(), mixedMeterial, appId);
				}
			}
		}

		return syncResponseMsg;
	}
}
