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
package com.yxw.mobileapp.datas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 消息模板管理
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: MsgManager
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月24日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgTempManager {
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 根据appId,模板code和模板来源获取模板消息
	 * @param appId
	 * @param code
	 * @param source
	 * @return
	 */
	public MsgTemplate getMsgTemplate(String appId, String code, String source, String msgTarget) {
		MsgTemplate template = null;
		// return msgCache.getMsgTemplate(appId, code, source);
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(code);
		params.add(source);
		params.add(msgTarget);
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "getMsgTemplate", params);
		if (CollectionUtils.isNotEmpty(results)) {
			template = (MsgTemplate) results.get(0);
		}

		return template;
	}

	/**
	 * 根据appId,模板code和模板来源获取客服消息
	 * @param appId
	 * @param code
	 * @param source
	 * @return
	 */
	public MsgCustomer getMsgCustomer(String appId, String code, String source) {
		// return msgCache.getMsgCustomer(appId, code, source);
		MsgCustomer msgCustomer = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(code);
		params.add(source);
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "getMsgCustomer", params);
		if (CollectionUtils.isNotEmpty(results)) {
			msgCustomer = (MsgCustomer) results.get(0);
		}

		return msgCustomer;
	}

}
