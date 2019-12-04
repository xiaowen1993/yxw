/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.platform.hospital.service.MenuService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;

/**
 * 菜单点击服务窗执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayFollowExecutor.java, v 0.1 Jul 24, 2014 4:29:04 PM
 *          baoxing.gbx Exp $
 */
public class InAlipayClickMenuExecutor implements ActionExecutor {

	private static Logger logger = LoggerFactory.getLogger(InAlipayClickMenuExecutor.class);
	private MenuService menuService = SpringContextHolder.getBean(MenuService.class);
	private MixedMeterialService mixedMeterialService = SpringContextHolder.getBean(MixedMeterialService.class);

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayClickMenuExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayClickMenuExecutor() {
		super();
	}

	public String execute() {
		String actionParam = bizContent.getString("ActionParam");
		final String fromUserId = bizContent.getString("FromUserId");
		final String appId = bizContent.getString("AppId");

		String hospId = null;
		String appSecret = null;
		// 根据appId获取医院ID和app密钥
		HospIdAndAppSecretVo hospIdAndAppSecretVo = MessageSendUtils.obtainByAppId(appId);
		if (hospIdAndAppSecretVo != null) {
			hospId = hospIdAndAppSecretVo.getHospId();
			appSecret = hospIdAndAppSecretVo.getAppSecret();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("获取不到医院APPID和密钥");
			}
			return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospId", hospId);
		params.put("thirdType", MessageConstant.THIRD_ALIPAY_STR);
		Menu menu = menuService.findById(actionParam);
		if (menu != null) {
			List<MixedMeterial> mixedMeterialList = mixedMeterialService.findMixedMeterialsByIds(new String[] { menu.getGrapicsId() });
			if (mixedMeterialList != null && mixedMeterialList.size() > 0) {
				MixedMeterial meterial = mixedMeterialList.get(0);
				if (meterial.getType() == MessageConstant.METERIAL_TYPE_SINGLE) {
					// 发送单图文
					MessageSendUtils.sendSingleImageText(fromUserId, meterial.getDescription(), appSecret, appId, meterial.getCoverPicPath(),
							meterial.getTitle(), meterial.getLink());
				} else if (meterial.getType() == MessageConstant.METERIAL_TYPE_MULTI) {
					// 发送多图文
					MessageSendUtils.sendMultiImageText(fromUserId, appSecret, meterial, appId);
				}
			}
		}
		return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
	}
}
