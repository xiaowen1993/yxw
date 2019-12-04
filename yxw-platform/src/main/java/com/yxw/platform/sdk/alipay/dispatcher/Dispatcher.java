/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.dispatcher;

import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.internal.util.StringUtils;
import com.yxw.platform.sdk.alipay.constant.AlipayServiceEventConstants;
import com.yxw.platform.sdk.alipay.constant.AlipayServiceNameConstants;
import com.yxw.platform.sdk.alipay.exception.MyException;
import com.yxw.platform.sdk.alipay.executor.ActionExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayChatTextExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayClickMenuExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayDefaultExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayEnterExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayFollowExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayUnFollowExecutor;
import com.yxw.platform.sdk.alipay.executor.InAlipayVerifyExecutor;

/**
 * 业务动作分发器
 * 
 * @author taixu.zqq
 * @version $Id: Dispatcher.java, v 0.1 2014年7月24日 下午4:59:58 taixu.zqq Exp $
 */
public class Dispatcher {

	private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

	/**
	 * 根据业务参数获取业务执行器
	 * 
	 * @param params
	 * @return
	 * @throws MyException
	 */
	public static ActionExecutor getExecutor(Map<String, String> params) throws MyException {
		// 1.获取服务信息
		String service = params.get("service");
		if (logger.isDebugEnabled()) {
			logger.debug("获取服务service   " + service);

		}
		if (StringUtils.isEmpty(service)) {
			throw new MyException("无法取得服务名");
		}
		// 2.获取内容信息
		String bizContent = params.get("biz_content");
		if (logger.isDebugEnabled()) {
			logger.debug("biz_content   " + bizContent);
		}
		if (StringUtils.isEmpty(bizContent)) {
			throw new MyException("无法取得业务内容信息");
		}
		// 3.将XML转化成json对象
		JSONObject bizContentJson = (JSONObject) new XMLSerializer().read(bizContent);
		// 3.1.获取消息类型信息
		String msgType = bizContentJson.getString("MsgType");
		if (logger.isDebugEnabled()) {
			logger.debug("msgType   " + msgType);
		}
		if (StringUtils.isEmpty(msgType)) {
			throw new MyException("无法取得消息类型");
		}
		// 2.根据消息类型(msgType)进行执行器的分类转发
		// 2.1 纯文本聊天类型
		if ("text".equals(msgType)) {
			return new InAlipayChatTextExecutor(bizContentJson);
			// 2.2 事件类型
		} else if ("event".equals(msgType)) {
			if (logger.isDebugEnabled()) {
				logger.debug("event   " + msgType);
				logger.debug("bizContentJson" + bizContentJson);
			}
			return getEventExecutor(service, bizContentJson);
		} else {
			// 2.3 后续支付宝还会新增其他类型，因此默认返回ack应答
			return new InAlipayDefaultExecutor(bizContentJson);
		}
	}

	/**
	 * 根据事件类型细化查找对应执行器
	 * 
	 * @param service
	 * @param bizContentJson
	 * @return
	 * @throws MyException
	 */
	private static ActionExecutor getEventExecutor(String service, JSONObject bizContentJson) throws MyException {
		// 1. 获取事件类型
		String eventType = bizContentJson.getString("EventType");

		if (StringUtils.isEmpty(eventType)) {
			throw new MyException("无法取得事件类型");
		}

		// 2.根据事件类型再次区分服务类型
		// 2.1 激活验证开发者模式
		if (AlipayServiceNameConstants.ALIPAY_CHECK_SERVICE.equals(service) && eventType.equals(AlipayServiceEventConstants.VERIFYGW_EVENT)) {
			if (logger.isDebugEnabled()) {
				logger.debug("激活验证开发者模式   ");
			}
			return new InAlipayVerifyExecutor(bizContentJson);

			// 2.2 其他消息通知类
		} else if (AlipayServiceNameConstants.ALIPAY_PUBLIC_MESSAGE_NOTIFY.equals(service)) {

			return getMsgNotifyExecutor(eventType, bizContentJson);

			// 2.3 对于后续支付宝可能新增的类型，统一默认返回AKC响应
		} else {
			return new InAlipayDefaultExecutor(bizContentJson);
		}
	}

	/**
	 * 根据事件类型(eventType)进行执行器的分类转发
	 * 
	 * @param eventType
	 * @param bizContentJson
	 * @return
	 * @throws MyException
	 */
	private static ActionExecutor getMsgNotifyExecutor(String eventType, JSONObject bizContentJson) throws MyException {
		if (eventType.equals(AlipayServiceEventConstants.FOLLOW_EVENT)) {

			// 服务窗关注事件
			return new InAlipayFollowExecutor(bizContentJson);

		} else if (eventType.equals(AlipayServiceEventConstants.UNFOLLOW_EVENT)) {

			// 服务窗取消关注事件
			return new InAlipayUnFollowExecutor(bizContentJson);

			// 根据actionParam进行执行器的转发
		} else if (eventType.equals(AlipayServiceEventConstants.CLICK_EVENT)) {

			// 点击事件
			return getClickEventExecutor(bizContentJson);

		} else if (eventType.equals(AlipayServiceEventConstants.ENTER_EVENT)) {

			// 进入事件
			return getEnterEventTypeExecutor(bizContentJson);

		} else {

			// 对于后续支付宝可能新增的类型，统一默认返回AKC响应
			return new InAlipayDefaultExecutor(bizContentJson);
		}

	}

	/**
	 * 进入事件执行器
	 * 
	 * @param bizContentJson
	 * @return
	 */
	private static ActionExecutor getEnterEventTypeExecutor(JSONObject bizContentJson) {
		try {

			JSONObject param = JSONObject.fromObject(bizContentJson.get("ActionParam"));
			JSONObject scene = JSONObject.fromObject(param.get("scene"));

			if (!StringUtils.isEmpty(scene.getString("sceneId"))) {

				// 自定义场景参数进入服务窗事件
				// return new InAlipayDIYQRCodeEnterExecutor(bizContentJson);
				return null;
			} else {

				// 普通进入服务窗事件
				return new InAlipayEnterExecutor(bizContentJson);
			}
		} catch (Exception exception) {
			// 无法解析sceneId的情况作为普通进入服务窗事件
			return new InAlipayEnterExecutor(bizContentJson);
		}
	}

	/**
	 * 点击事件执行器
	 * 
	 * @param bizContentJson
	 * @return
	 */
	private static ActionExecutor getClickEventExecutor(JSONObject bizContentJson) {

		String actionParam = bizContentJson.getString("ActionParam");

		if ("authentication".equals(actionParam)) {

			// 申请开发者会员绑定事件: actionParam支付宝服务窗固定值
			// TODO 开发者自行实现，开发者若不需要绑定开发者会员，可无需实现
			return null;
		} else if ("delete".equals(actionParam)) {

			// 删除开发者会员绑定事件：actionParam支付宝服务窗固定值
			// TODO 开发者自行实现，开发者若不需要绑定开发者会员，可无需实现
			return null;

			// 除上述支付宝保留key外，其他key为开发者配置菜单时自定义
		} else {
			// 菜单点击服务窗执行器
			return new InAlipayClickMenuExecutor(bizContentJson);
		}
	}

	public static void main(String[] ss) {
		String xmlString = "<?xml version=\"1.0\" encoding=\"gbk\"?><XML><AppId><![CDATA[2014122400021484]]></AppId><FromUserId></FromUserId><CreateTime><![CDATA[1435232166290]]></CreateTime><MsgType><![CDATA[event]]></MsgType><EventType><![CDATA[verifygw]]></EventType><ActionParam></ActionParam><AgreementId></AgreementId><AccountNo></AccountNo></XML>";
		JSONObject bizContentJson = (JSONObject) new XMLSerializer().read(xmlString);
		// 1.获取消息类型信息
		String msgType = bizContentJson.getString("MsgType");
		System.out.println(msgType);
	}
}
