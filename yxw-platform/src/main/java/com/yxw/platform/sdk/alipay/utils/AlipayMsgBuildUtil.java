/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.platform.sdk.alipay.controller.AlipaySDKController;

/**
 * 消息构造工具
 * 
 * @author baoxing.gbx
 * @version $Id: AlipayMsgBuildUtil.java, v 0.1 Jul 24, 2014 5:47:19 PM
 *          baoxing.gbx Exp $
 */
public class AlipayMsgBuildUtil {

	private static Logger logger = LoggerFactory.getLogger(AlipayMsgBuildUtil.class);

	/**
	 * 构造单发单图文消息
	 * 
	 * @param fromUserId
	 * @param desc
	 * @param imgsrc
	 * @param title
	 * @param url
	 * @return
	 */
	public static String buildSingleImgTextMsg(String fromUserId, String desc, String imgsrc, String title, String url) {
		JSONObject reqStr = new JSONObject();
		JSONArray articles = new JSONArray();
		JSONObject article = new JSONObject();
		article.put("actionName", "立即查看");
		article.put("desc", desc);
		article.put("imageUrl", AlipaySDKController.basePath + imgsrc);
		article.put("title", title);
		article.put("url", url);
		articles.add(article);
		reqStr.put("articles", articles);
		reqStr.put("msgType", "image-text");
		reqStr.put("toUserId", fromUserId);
		return reqStr.toJSONString();
	}

	/**
	 * 构造单发多图文消息
	 * 
	 * @param fromUserId
	 * @param meterial
	 * @return
	 */
	public static String buildMultiImgTextMsg(String fromUserId, MixedMeterial meterial) {
		JSONObject reqStr = new JSONObject();
		reqStr.put("msgType", "image-text");
		reqStr.put("toUserId", fromUserId);
		JSONArray articles = new JSONArray();
		JSONObject article = new JSONObject();
		article.put("actionName", "立即查看");
		article.put("desc", meterial.getDescription());
		article.put("imageUrl", AlipaySDKController.basePath + meterial.getCoverPicPath());
		article.put("title", meterial.getTitle());
		article.put("url", meterial.getLink());
		articles.add(article);
		List<MixedMeterial> subList = meterial.getSubMixedMeterialList();
		if (subList != null && subList.size() > 0) {
			for (MixedMeterial m : subList) {
				article = new JSONObject();
				article.put("actionName", "立即查看");
				article.put("desc", m.getDescription());
				article.put("imageUrl", AlipaySDKController.basePath + m.getCoverPicPath());
				article.put("title", m.getTitle());
				article.put("url", m.getLink());
				articles.add(article);
			}
		}
		reqStr.put("articles", articles);
		return reqStr.toJSONString();

	}

	/**
	 * 构造单发纯文本消息
	 * 
	 * @param fromUserId
	 * @param textContent
	 * @return
	 */
	public static String buildSingleTextMsg(String fromUserId, String textContent) {
		JSONObject reqStr = new JSONObject();
		JSONObject text = new JSONObject();
		text.put("content", textContent);
		reqStr.put("toUserId", fromUserId);
		reqStr.put("msgType", "text");
		reqStr.put("text", text);
		return reqStr.toJSONString();
	}

	/**
	 * 截取字符串方式组支付宝模版消息
	 * 
	 * @param fromUserId
	 * @param tempId
	 * @param content
	 * @param url
	 * @param topColor
	 * @param template_id
	 * @param remark
	 * @return
	 */
	public static String
			buildTemplateMsg(String fromUserId, String tempId, List<MsgTemplateContent> msgTemplateContents, String url, String topColor) {
		JSONObject reqStr = new JSONObject();
		JSONObject context = new JSONObject();
		JSONObject template = new JSONObject();
		JSONObject keyword = null;
		for (MsgTemplateContent m : msgTemplateContents) {
			keyword = new JSONObject();
			keyword.put("value", m.getValue());
			keyword.put("color", m.getFontColor());
			context.put(m.getNode(), keyword);
		}
		context.put("headColor", topColor);
		context.put("url", url);
		context.put("actionName", "查看详情");
		template.put("templateId", tempId);
		template.put("context", context);
		reqStr.put("toUserId", fromUserId);
		reqStr.put("template", template);
		return reqStr.toJSONString();
	}

	public static String buildTemplateMsg(String fromUserId, String tempId, String data, String url, String topColor) {
		JSONObject reqStr = new JSONObject();
		JSONObject template = new JSONObject();
		JSONObject context = JSONObject.parseObject(data);
		context.put("headColor", topColor);
		context.put("url", url);
		context.put("actionName", "查看详情");
		template.put("templateId", tempId);
		template.put("context", context);
		reqStr.put("toUserId", fromUserId);
		reqStr.put("template", template);
		return reqStr.toJSONString();
	}

	/**
	 * 构造基础的响应消息
	 * 
	 * @return
	 */
	public static String buildBaseAckMsg(String fromUserId, String appId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<XML>");
		sb.append("<ToUserId><![CDATA[" + fromUserId + "]]></ToUserId>");
		sb.append("<AppId><![CDATA[" + appId + "]]></AppId>");
		sb.append("<CreateTime>" + Calendar.getInstance().getTimeInMillis() + "</CreateTime>");
		sb.append("<MsgType><![CDATA[ack]]></MsgType>");
		sb.append("</XML>");
		return sb.toString();
	}

	/**
	 * 构造群发图文消息
	 * 
	 * @return
	 */
	public static String buildGroupImgTextMsg() {

		StringBuilder sb = new StringBuilder();

		// 构建json格式群发图文消息: 所有内容开发者请根据自有业务自行设置响应值，这里只是个样例
		sb.append("{'articles':[{'actionName':'立即查看','desc':'这是图文内容','imageUrl':'http://pic.alipayobjects.com/e/201311/1PaQ27Go6H_src.jpg','title':'这是标题','url':'https://www.alipay.com/'}],'msgType':'image-text'}");

		return sb.toString();
	}

	public static void main(String[] args) {

		MixedMeterial meterial = new MixedMeterial();
		meterial.setCoverPicPath("http://example.com/abc.jpg");
		meterial.setLink("https://www.example.com/a.php");
		meterial.setDescription("图文内容");
		meterial.setTitle("标题");
		List<MixedMeterial> subMixedMeterialList = new ArrayList<MixedMeterial>();
		MixedMeterial m1 = new MixedMeterial();
		MixedMeterial m2 = new MixedMeterial();
		MixedMeterial m3 = new MixedMeterial();
		m2.setCoverPicPath("http://example.com/2343.jpg");
		m2.setLink("https://www.example.com/23423423.php");
		m2.setDescription("图文内容2");
		m2.setTitle("标题2");
		m1.setCoverPicPath("http://example.com/adsfdsc.jpg");
		m1.setLink("https://www.example.com/sdafsd.php");
		m1.setDescription("图文内容3");
		m1.setTitle("标题3");
		m3.setCoverPicPath("http://example.com/2343bc.jpg");
		m3.setLink("https://www.example.com/2332.php");
		m3.setDescription("图文内容4");
		m3.setTitle("标题4");
		subMixedMeterialList.add(m1);
		subMixedMeterialList.add(m2);
		subMixedMeterialList.add(m3);
		meterial.setSubMixedMeterialList(subMixedMeterialList);
		System.out.println(buildMultiImgTextMsg("R8qtKrh1dS49uWaLZUbPr0re8hjWnYClscK-vjUi4qOHCIhO5aJXi4frP7yHEJoJ01", meterial));

		/*String s = buildSingleImgTextMsg("asdasdasdsadadadasdasdas", "hahahahahahahaha", "http://ss.sd.casd", "adasdasdas", "dasddsfadasfdas");
		System.out.println(s);*/
	}

	/**
	 * 构造群发纯文本消息
	 * 
	 * @return
	 */
	public static String buildGroupTextMsg() {

		StringBuilder sb = new StringBuilder();

		// 构建json格式群发纯文本消息体： 所有内容开发者请根据自有业务自行设置响应值，这里只是个样例
		sb.append("{'msgType':'text','text':{'content':'这是纯文本消息'}}");

		return sb.toString();
	}

	/**
	 * 构造免登图文消息
	 * 
	 * @param fromUserId
	 * @return
	 */
	public static String buildImgTextLoginAuthMsg(String fromUserId) {

		StringBuilder sb = new StringBuilder();

		// 免登连接地址，开发者需根据部署服务修改相应服务ip地址
		String url = "http://10.15.132.68:8080/AlipayFuwuDemo/loginAuth.html";

		// 构建json格式的单发免登图文消息体 authType 等于 "loginAuth"表示免登消息 ：
		// 所有内容开发者请根据自有业务自行设置响应值，这里只是个样例
		sb.append("{'articles':[{'actionName':'立即查看','desc':'这是图文内容','imageUrl':'http://pic.alipayobjects.com/e/201311/1PaQ27Go6H_src.jpg','title':'这是标题','url':'"
				+ url + "', 'authType':'loginAuth'}],'msgType':'image-text', 'toUserId':'" + fromUserId + "'}");

		return sb.toString();
	}

	/**
	 * 兼容旧版本
	 * 
	 * @param toUser
	 * @param data
	 * @return
	 */
	public static String buildTempMsg(String toUser, String data, String template_id) {
		// 预约挂号成功
		StringBuilder sb =
				new StringBuilder("{\"template\":{").append("\"context\":").append(data).append(",\"templateId\":\"").append(template_id)
						.append("\"},").append("\"toUserId\":\"").append(toUser).append("\"}");
		// 构建json格式单发纯文本消息体： 所有内容开发者请根据自有业务自行设置响应值，这里只是个样例
		System.out.println("旧版：" + sb.toString());
		return sb.toString();
	}

}
