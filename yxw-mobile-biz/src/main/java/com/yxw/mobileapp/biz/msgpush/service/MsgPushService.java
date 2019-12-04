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
package com.yxw.mobileapp.biz.msgpush.service;

import java.io.Serializable;
import java.util.Map;

import com.yxw.commons.entity.platform.msgpush.MsgTemplate;

/**
 * 消息推送服务接口
 * @Package: com.yxw.platform.msgpush.service
 * @ClassName: MsgPushService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgPushService {

	/**
	 * 消息推送:模板消息推送,客服消息推送
	 * @param appId
	 * @param templateCode 后台配置的模板编码
	 * @param platformType
	 * 		BizConstant.MODE_TYPE_WEIXIN   
	 *      BizConstant.MODE_TYPE_ALIPAY
	 * @param openId
	 * @param paramMap key 模板的关键字   值根据业务自己设置
	 */
	public abstract void msgPush(String appId, String templateCode, String platformType, String openId, Map<String, Serializable> paramMap, String msgTarget);

	/**
	 * app消息推送
	 * @param appId
	 * @param userId
	 * @param templateCode
	 * @param platformType
	 * @param paramMap
	 */
	public void appMsgTemplatPush(String appId, String userId, String templateCode, String platformType, Map<String, Serializable> paramMap, MsgTemplate msgTemplate, String msgTarget);

}
