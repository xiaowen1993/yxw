/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.platform.hospital.Extension;
import com.yxw.commons.entity.platform.hospital.ExtensionDetail;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.platform.hospital.service.ExtensionDetailService;
import com.yxw.platform.hospital.service.ExtensionService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.ReplyService;
import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;

/**
 * 关注服务窗执行器
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayFollowExecutor.java, v 0.1 Jul 24, 2014 4:29:04 PM
 *          baoxing.gbx Exp $
 */
public class InAlipayFollowExecutor implements ActionExecutor {

	private ReplyService replyService = SpringContextHolder.getBean(ReplyService.class);

	private static Logger logger = LoggerFactory.getLogger(InAlipayFollowExecutor.class);

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayFollowExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayFollowExecutor() {
		super();
	}

	public String execute() {

		// TODO 根据支付宝请求参数，可以将支付宝账户UID-服务窗ID关系持久化，用于后续开发者自己的其他操作
		// 这里只是个样例程序，所以这步省略。
		// 直接构造简单响应结果返回
		final String fromUserId = bizContent.getString("FromUserId");
		final String appId = bizContent.getString("AppId");
		final String actionParam = bizContent.getString("ActionParam");
		if (logger.isDebugEnabled()) {
			logger.debug("fromUserId:{}, appId:{}, actionParam:{}", new Object[] { fromUserId, appId, actionParam });
		}
		logger.info("actionParam:{}", new Object[] { actionParam });
		if (!StringUtils.isBlank(actionParam)) {
			addExtensionCount(appId, fromUserId, actionParam);
		}

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
		Reply reply = replyService.getFocusedReply(params);
		// logger.info("reply == null   " + reply == null ? "true" : "false");
		if (reply != null) {
			if (reply.getContentType() == MessageConstant.TEXT) {
				// logger.info(" reply.getContent()   " + reply.getContent());
				// 回复文本
				MessageSendUtils.sendText(fromUserId, reply.getContent(), appSecret, appId);
			} else if (reply.getContentType() == MessageConstant.IMAGE) {
				// 回复图片
				// 目前支付宝无回复图片
			} else if (reply.getContentType() == MessageConstant.IMAGETEXT) {
				List<MixedMeterial> meterials = reply.getMixedMeterialList();
				if (meterials != null) {
					MixedMeterial meterial = meterials.get(0);
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
		}
		return AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
	}

	/**
	 * 获取关注来源的scennId
	 * @param json
	 * @return
	 */
	private String getSceneID(String json) {
		Map<String, Object> map = JSON.parseObject(json);
		map = (Map<String, Object>) map.get("scene");
		return map.get("sceneId").toString();
	}

	/**
	 * 添加来源关注人
	 * @param appId
	 * @param openId
	 * @param actionParam
	 */
	public void addExtensionCount(String appId, String openId, String actionParam) {
		String sceneId = getSceneID(actionParam);
		logger.info("appId:{},openId:{},sceneId:{}.", new Object[] { appId, openId, sceneId });
		if (!StringUtils.isBlank(sceneId)) {//获取到sceneId,表示由二维码关注 记录关注人
			ExtensionService extensionService = SpringContextHolder.getBean(ExtensionService.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("appId", appId);
			params.put("sceneid", sceneId);
			logger.info("查推广二维码参数params:{}", new Object[] { JSON.toJSONString(params) });
			Extension extension = extensionService.findExtensionByAppIdAndSceneid(params);
			logger.info("查推广二维码结果:{}", new Object[] { JSON.toJSONString(extension) });
			if (extension != null) {//属于系统中保存的推广二维码则添加关注人
				params.clear();
				ExtensionDetailService detailService = SpringContextHolder.getBean(ExtensionDetailService.class);
				params.put("openId", openId);
				params.put("extensionId", extension.getId());
				logger.info("查是否已经关注参数params:{}", new Object[] { JSON.toJSONString(params) });
				ExtensionDetail detail = detailService.findExtensionDetailByExtensionIdAndOpenId(params);
				logger.info("查是否已经关注结果:{}", new Object[] { JSON.toJSONString(detail) });
				if (detail == null) {
					detail = new ExtensionDetail(extension.getId(), new Date().getTime(), openId);
					logger.info("保存关注人:{}", new Object[] { JSON.toJSONString(detail) });
					extension.setCount(extension.getCount() + 1);
					logger.info("关注统计数加1:{}", new Object[] { extension.getCount() });
					detailService.add(detail);
					extensionService.update(extension);
				}
			}
		}
	}
}
