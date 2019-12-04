package com.yxw.platform.sdk.wechat.util;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;

/**
 * 
 * wechat消息推送
 * 
 * */
public class PushMsgUtil {

	private static Logger logger = LoggerFactory.getLogger(PushMsgUtil.class);

	/**
	 * 微信服务，发送模版消息
	 * 
	 * @param touser
	 * @param template_id
	 * @param url
	 * @param topcolor
	 * @param data
	 * @param registerId
	 * @return
	 */
	public static JSONObject pushWXTemplate(String touser, String appId, String appSecret, String template_id, String url, String topcolor,
			List<MsgTemplateContent> msgTemplateContents) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.SEND_TEMPLATE_MSG + tocken;
		JSONObject data = new JSONObject();
		JSONObject reqStr = new JSONObject();
		JSONObject keyword = null;
		for (MsgTemplateContent m : msgTemplateContents) {
			keyword = new JSONObject();
			// 反转义特殊字符,比如换行符和回车符,如果取到的内容包含明文的换行符或者回车符,防止java不自动转义,比如 \n或者\r
			keyword.put("value", StringEscapeUtils.unescapeJava(m.getValue()));
			keyword.put("color", m.getFontColor());
			data.put(m.getNode(), keyword);
		}
		reqStr.put("touser", touser);
		reqStr.put("template_id", template_id);
		reqStr.put("url", url);
		reqStr.put("topcolor", topcolor);
		reqStr.put("data", data);
		//		HttpResponse response =
		//				new HttpClientUtil().post(postURL, reqStr.toJSONString(), HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);
		String responseString = HttpClientUtil.getInstance().post(postURL, reqStr.toJSONString());
		if (StringUtils.isNotBlank(responseString)) {
			return JSONObject.parseObject(responseString);
		} else {
			return null;
		}

	}

	/**
	 * 微信服务，发送模版消息
	 * 
	 * @param touser
	 * @param template_id
	 * @param url
	 * @param topcolor
	 * @param data
	 * @param registerId
	 * @return
	 */
	public static JSONObject pushWXTemplate(String touser, String appId, String appSecret, String template_id, String url, String topcolor,
			String data) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.SEND_TEMPLATE_MSG + tocken;
		JSONObject jsonObject = JSONObject.parseObject(data);
		JSONObject reqStr = new JSONObject();
		reqStr.put("touser", touser);
		reqStr.put("template_id", template_id);
		reqStr.put("url", url);
		reqStr.put("topcolor", topcolor);
		reqStr.put("data", jsonObject);
		//		HttpResponse response =
		//				new HttpClientUtil().post(postURL, reqStr.toJSONString(), HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);

		String responseString = HttpClientUtil.getInstance().post(postURL, reqStr.toJSONString());

		if (StringUtils.isNotBlank(responseString)) {
			return JSONObject.parseObject(responseString);
		} else {
			return null;
		}

	}

	/**
	 * 根据模版短ID获取模版ID
	 * */
	public static JSONObject getTemplate(String templateShortId, String appId, String appSecret) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.GET_TEMPLATE_ID + tocken;
		JSONObject reqStr = new JSONObject();
		reqStr.put("template_id_short", templateShortId);
		//		HttpResponse response =
		//				new HttpClientUtil().post(postURL, reqStr.toJSONString(), HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);
		String responseString = HttpClientUtil.getInstance().post(postURL, reqStr.toJSONString());
		if (StringUtils.isNotBlank(responseString)) {
			return JSONObject.parseObject(responseString);
		} else {
			return null;
		}
	}

	/**
	 * 发送客服消息 pushServiceNotice
	 * */
	public static JSONObject pushServiceNotice(String touser, String appId, String appSecret, MsgCustomer msgCustomer) {
		String postURL = WechatConstant.SEND_KF_MESSAGE + WechatSDK.getAccessToken(appId, appSecret);
		JSONObject reqStr = new JSONObject();
		JSONObject text = new JSONObject();
		// 反转义特殊字符,比如换行符和回车符,如果取到的内容包含明文的换行符或者回车符,防止java不自动转义,比如 \n或者\r
		text.put("content", StringEscapeUtils.unescapeJava(msgCustomer.getContent()));
		reqStr.put("touser", touser);
		if (msgCustomer.getType() == 1) {
			reqStr.put("msgtype", "text");
		}
		reqStr.put("text", text);
		logger.info(reqStr.toString());
		//		HttpResponse response = new HttpClientUtil().post(postURL, reqStr.toString(), HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);
		String responseString = HttpClientUtil.getInstance().post(postURL, reqStr.toJSONString());
		if (StringUtils.isNotBlank(responseString)) {
			return JSONObject.parseObject(responseString);
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		MsgCustomer msgCustomer = new MsgCustomer();
		msgCustomer.setContent("lopin is really cute");
		msgCustomer.setType(1);
		pushServiceNotice("oekc9s1G4RpuEbg08JdZJibMLQ2I", "d", "d", msgCustomer);
	}
}
