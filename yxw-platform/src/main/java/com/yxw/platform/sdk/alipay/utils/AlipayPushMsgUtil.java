package com.yxw.platform.sdk.alipay.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMessageCustomSendRequest;
import com.alipay.api.request.AlipayMobilePublicMessageSingleSendRequest;
import com.alipay.api.response.AlipayMobilePublicMessageCustomSendResponse;
import com.alipay.api.response.AlipayMobilePublicMessageSingleSendResponse;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.alipay.factory.AlipayAPIClientFactory;

public class AlipayPushMsgUtil {

	private static Logger logger = LoggerFactory.getLogger(AlipayPushMsgUtil.class);

	/**
	 * 推送模版消息-支付宝
	 * 
	 * @param appid
	 * @param privateKey
	 * @param toUser
	 * @param msgTemplateContents
	 * @param topcolor
	 * @param url
	 * @param template_id
	 * @return
	 */
	public static JSONObject pushModelMsgAlipay(String appid, String privateKey, String toUser, List<MsgTemplateContent> msgTemplateContents,
			String url, String topcolor, String template_id) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appid, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		AlipayMobilePublicMessageSingleSendRequest request = new AlipayMobilePublicMessageSingleSendRequest();
		String requstStr = "";
		try {
			requstStr = AlipayMsgBuildUtil.buildTemplateMsg(toUser, template_id, msgTemplateContents, url, topcolor);
			logger.info("requstStr:" + requstStr);
			if (logger.isDebugEnabled()) {
				logger.debug("requstStr----" + requstStr);
			}
		} catch (Exception e) {
			logger.error("格式解析异常");
		}
		request.setBizContent(requstStr);
		JSONObject jsonObject = new JSONObject();
		try {
			// 使用SDK，调用单发接口发送纯文本消息
			AlipayMobilePublicMessageSingleSendResponse response = alipayClient.execute(request);
			if (response != null && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
				jsonObject.put("errcode", "0");
				jsonObject.put("errmsg", response.getMsg());
				logger.info("推送模版消息成功 " + response.getCode());
				if (logger.isDebugEnabled()) {
					logger.debug("推送模版消息成功 : response = " + response.getBody());
				}
				return jsonObject;
			} else {
				return null;
			}
		} catch (AlipayApiException e) {
			logger.error("调用pushModelMsgAlipay方法，推送模版消息-支付宝异常");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 推送模版消息-支付宝
	 * 
	 * @param appid
	 * @param privateKey
	 * @param toUser
	 * @param msgTemplateContents
	 * @param topcolor
	 * @param url
	 * @param template_id
	 * @return
	 */
	public static JSONObject pushModelMsgAlipay(String appid, String privateKey, String toUser, String data, String url, String topcolor,
			String template_id) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appid, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		AlipayMobilePublicMessageSingleSendRequest request = new AlipayMobilePublicMessageSingleSendRequest();
		String requstStr = "";
		try {
			requstStr = AlipayMsgBuildUtil.buildTemplateMsg(toUser, template_id, data, url, topcolor);
			logger.info("requstStr:" + requstStr);
			if (logger.isDebugEnabled()) {
				logger.debug("requstStr----" + requstStr);
			}
		} catch (Exception e) {
			logger.error("格式解析异常");
		}
		request.setBizContent(requstStr);
		JSONObject jsonObject = new JSONObject();
		try {
			// 使用SDK，调用单发接口发送纯文本消息
			AlipayMobilePublicMessageSingleSendResponse response = alipayClient.execute(request);
			if (response != null && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
				jsonObject.put("errcode", "0");
				jsonObject.put("errmsg", response.getMsg());
				logger.info("推送模版消息成功 " + response.getCode());
				if (logger.isDebugEnabled()) {
					logger.debug("推送模版消息成功 : response = " + response.getBody());
				}
				return jsonObject;
			} else {
				return null;
			}
		} catch (AlipayApiException e) {
			logger.error("调用pushModelMsgAlipay方法，推送模版消息-支付宝异常");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 发送客服消息——支付宝
	 * 
	 * @param appid
	 * @param toUser
	 * @param privateKey
	 * @param msgCustomer
	 * @return
	 */
	public static JSONObject pushNoticeMsgAlipay(String appid, String privateKey, String toUser, MsgCustomer msgCustomer) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appid, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		// 使用SDK，构建单发请求模型
		AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
		request.setBizContent(AlipayMsgBuildUtil.buildSingleTextMsg(toUser, msgCustomer.getContent()));
		JSONObject jsonObject = new JSONObject();
		try {
			// 使用SDK，调用单发接口发送纯文本消息
			AlipayMobilePublicMessageCustomSendResponse response = alipayClient.execute(request);

			if (response != null && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
				if (logger.isDebugEnabled()) {
					logger.debug("发送客服消息——支付宝-成功 : response = " + response.getBody());
				}
				jsonObject.put("errcode", "0");
				jsonObject.put("errmsg", response.getMsg());
				return jsonObject;
			} else {
				return null;
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("发送客服消息异常----pushNoticeMsgAlipay方法");
			return null;
		}
	}

}
