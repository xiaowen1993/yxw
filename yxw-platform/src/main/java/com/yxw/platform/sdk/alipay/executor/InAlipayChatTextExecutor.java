/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.platform.hospital.service.WhiteListDetailService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.message.service.ReplyService;
import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.alipay.exception.MyException;
import com.yxw.platform.sdk.alipay.utils.AlipayMsgBuildUtil;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;

/**
 * 聊天执行器(纯文本消息)
 * 
 * @author baoxing.gbx
 * @version $Id: InAlipayChatExecutor.java, v 0.1 Jul 28, 2014 5:17:04 PM
 *          baoxing.gbx Exp $
 */
public class InAlipayChatTextExecutor implements ActionExecutor {

	/** 线程池 */
	private static ExecutorService executors = Executors.newSingleThreadExecutor();

	private ReplyService replyService = SpringContextHolder.getBean(ReplyService.class);

	private WhiteListDetailService whiteListDetailService = SpringContextHolder.getBean(WhiteListDetailService.class);

	private MixedMeterialService mixedMeterialService = SpringContextHolder.getBean(MixedMeterialService.class);

	// private WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);;

	private static Logger logger = LoggerFactory.getLogger(InAlipayChatTextExecutor.class);

	/** 业务参数 */
	private JSONObject bizContent;

	public InAlipayChatTextExecutor(JSONObject bizContent) {
		this.bizContent = bizContent;
	}

	public InAlipayChatTextExecutor() {
		super();
	}

	/**
	 * 
	 * @see com.alipay.executor.ActionExecutor#execute()
	 */
	public String execute() throws MyException {

		// 取得发起请求的支付宝账号id
		final String fromUserId = bizContent.getString("FromUserId");
		final String appId = bizContent.getString("AppId");
		JSONObject textJson = bizContent.getJSONObject("Text");
		String content = textJson.getString("Content");// 获取用户的输入keyword
		// 根据appId获取医院ID和app密钥
		String hospId = null;
		String appSecret = null;
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
		// logger.error("获取用户的输入keyword   -----------" + content);
		if (content != null && content.startsWith("SQ")) {
			String phone = content.substring(2, content.length());
			Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher = pattern.matcher(phone);
			if (matcher.matches()) {
				// 判断是否appId对应的医院是否开始白名单设置
//				boolean isOpen = whiteListCache.isOpenWhiteList(appId, AlipayConstant.ALIPAY);
				boolean isOpen = false;
				ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
				List<Object> params = new ArrayList<Object>();
				params.add(appId);
				params.add(AlipayConstant.ALIPAY);
				List<Object> results = serveComm.get(CacheType.WHITE_LIST_CACHE, "isOpenWhiteList", params);
				if (CollectionUtils.isNotEmpty(results)) {
					isOpen = (boolean) results.get(0);
				}
				
				if (isOpen) {
					// 判断申请的手机号是否加入登记
					// boolean isAddWhiteFlag = whiteListCache.isValidWhiteOpenIdOrPhone(appId, AlipayConstant.ALIPAY, phone);
					boolean isAddWhiteFlag = false;
					params.add(phone);
					params.add(fromUserId);
					results = serveComm.get(CacheType.WHITE_LIST_CACHE, "isValidWhiteOpenIdOrPhone", params);
					if (CollectionUtils.isNotEmpty(results)) {
						isAddWhiteFlag = (boolean) results.get(0);
					}
					
					if (isAddWhiteFlag) {
						// 有对应的手机号 换取openId
						whiteListDetailService.updateWhiteListOpenId(appId, AlipayConstant.ALIPAY, phone, fromUserId);
						MessageSendUtils.sendText(fromUserId, "已加入白名单，欢迎参加内测！", appSecret, appId);
					} else {
						MessageSendUtils.sendText(fromUserId, "您的手机号码未登记,不能参加内测！如有意参加测试,请联系管理员登记.", appSecret, appId);
					}
				}
			} else {
				MessageSendUtils.sendText(fromUserId, "手机号码格式不正确,请正确输入手机号码!", appSecret, appId);
			}
		} else {
			// 查询满足条件的医院规则
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospId", hospId);
			params.put("keyword", content);
			params.put("thirdType", MessageConstant.THIRD_ALIPAY_STR);
			List<Rule> ruleList = replyService.getKeywordRule(params);
			// 获取随机规则的随机回复
			if (ruleList != null && ruleList.size() > 0) {
				Rule rule = null;// 随机选择规则
				int n = 0;
				if (ruleList.size() > 1) {
					n = generateRandom(ruleList.size());
				}
				rule = ruleList.get(n);
				List<Reply> replyList = rule.getReplyList();
				if (replyList == null || replyList.size() == 0) {
					return null;
				}
				// ------------------------随机选取回复-----------------------------------------
				if (replyList.size() > 1) {
					n = generateRandom(replyList.size());
				}
				Reply reply = replyList.get(n);
				if (reply != null) {
					if (reply.getContentType() == MessageConstant.TEXT) {
						// 回复文本
						MessageSendUtils.sendText(fromUserId, reply.getContent(), appSecret, appId);
					} else if (reply.getContentType() == MessageConstant.IMAGE) {
						// 回复图片
						// 目前支付宝不支持图片回复
					} else if (reply.getContentType() == MessageConstant.IMAGETEXT) {
						List<MixedMeterial> meterials = reply.getMixedMeterialList();
						if (meterials != null) {
							MixedMeterial meterial = meterials.get(0);
							if (meterial.getType() == MessageConstant.METERIAL_TYPE_SINGLE) {
								// 发送单图文
								MessageSendUtils.sendSingleImageText(fromUserId, meterial.getDescription(), appSecret, appId,
										meterial.getCoverPicPath(), meterial.getTitle(), meterial.getLink());
							} else if (meterial.getType() == MessageConstant.METERIAL_TYPE_MULTI) {
								// 发送多图文
								MessageSendUtils.sendMultiImageText(fromUserId, appSecret, meterial, appId);
							}
						}
					}
				}
			}
		}
		// 1. 首先同步构建ACK响应
		String syncResponseMsg = AlipayMsgBuildUtil.buildBaseAckMsg(fromUserId, appId);
		// 3.返回同步的ACK响应
		return syncResponseMsg;
	}

	public int generateRandom(int b) {
		int temp = 0;
		try {
			if (0 > b) {
				temp = new Random().nextInt(0 - b);
				return temp + b;
			} else {
				temp = new Random().nextInt(b - 0);
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

}
