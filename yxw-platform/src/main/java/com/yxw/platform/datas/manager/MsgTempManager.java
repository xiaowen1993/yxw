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
package com.yxw.platform.datas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
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
	 * 根据模板code和模板来源获取模板库模板
	 * @param code
	 * @param source
	 * @return
	 */
	public MsgTemplateLibrary getMsgTemplateLibrary(String code, String source) {
		MsgTemplateLibrary library = null;
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		params.add(source);
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "getMsgTemplateLibrary", params);
		if (CollectionUtils.isNotEmpty(results)) {
			library = (MsgTemplateLibrary) results.get(0);
		}
		// return msgCache.getMsgTemplateLibrary(code, source);

		return library;
	}

	/**
	 * 判断模板库缓存对象是否存在
	 * @param code
	 * @param source
	 * @return
	 */
	public boolean existsMsgTemplateLibrary(String code, String source) {
		boolean result = false;
		// return msgCache.existsMsgTemplateLibrary(code, source);
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		params.add(source);
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "existsMsgTemplateLibrary", params);
		if (CollectionUtils.isNotEmpty(results)) {
			result = (Boolean) results.get(0);
		}

		return result;
	}

	/**
	 * 获取所有的模板库模板
	 * @return
	 */
	public List<MsgTemplateLibrary> getMsgTemplateLibraryAll() {
		List<MsgTemplateLibrary> libraries = null;
		// return msgCache.getMsgTemplateLibraryAll();
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "existsMsgTemplateLibrary", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			libraries = JSON.parseArray(source, MsgTemplateLibrary.class);

		}

		return libraries != null ? libraries : new ArrayList<MsgTemplateLibrary>();
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgTemplateLibrary
	 */
	public void updateMsgTemplateLibrary(MsgTemplateLibrary msgTemplateLibrary) {
		List<Object> params = new ArrayList<Object>();
		params.add(msgTemplateLibrary);
		serveComm.set(CacheType.MSG_TEMP_CACHE, "updateMsgTemplateLibrary", params);
		// msgCache.updateMsgTemplateLibrary(msgTemplateLibrary);
	}

	/**
	 * 删除模板库模板
	 * @param msgTemplateLibrary
	 */
	public void deleteMsgTemplateLibrary(MsgTemplateLibrary msgTemplateLibrary) {
		List<Object> params = new ArrayList<Object>();
		params.add(msgTemplateLibrary);
		serveComm.delete(CacheType.MSG_TEMP_CACHE, "deleteMsgTemplateLibrary", params);
		// msgCache.deleteMsgTemplateLibrary(msgTemplateLibrary);
	}

	/**
	 * 判断消息模板缓存对象是否存在
	 * @param appId
	 * @param code
	 * @param source
	 * @return
	 */
	public boolean existsMsgTemplate(String appId, String code, String source, String msgTarget) {
		// return msgCache.existsMsgTemplate(appId, code, source);
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(code);
		params.add(source);
		params.add(msgTarget);
		Integer count = serveComm.count(CacheType.MSG_TEMP_CACHE, "existsMsgTemplate", params);
		return count != 0;
	}

	/**
	 * 获取所有的模板消息
	 * @return
	 */
	public List<MsgTemplate> getMsgTemplateAll() {
		// return msgCache.getMsgTemplateAll();
		List<MsgTemplate> libraries = null;
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "getMsgTemplateAll", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			libraries = JSON.parseArray(source, MsgTemplate.class);

		}

		return libraries != null ? libraries : new ArrayList<MsgTemplate>();
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgTemplate
	 */
	public void updateMsgTemplate(MsgTemplate msgTemplate) {
		// msgCache.updateMsgTemplate(msgTemplate);
		List<Object> params = new ArrayList<Object>();
		params.add(msgTemplate);
		serveComm.set(CacheType.MSG_TEMP_CACHE, "updateMsgTemplate", params);
	}

	/**
	 * 删除模板库模板
	 * @param msgTemplate
	 */
	public void deleteMsgTemplate(MsgTemplate msgTemplate) {
		// msgCache.deleteMsgTemplate(msgTemplate);
		List<Object> params = new ArrayList<Object>();
		params.add(msgTemplate);
		serveComm.delete(CacheType.MSG_TEMP_CACHE, "deleteMsgTemplate", params);
	}

	/**
	 * 判断客服消息缓存对象是否存在
	 * -- 2017-7-26 14:11:39
	 * 一个平台，可以有多个模板消息，如app有两类（实际是一个），嵌入wechat中则真实的两类
	 * 但最多只会有一个客服消息
	 * @param appIdn  a
	 * @param code
	 * @param source
	 * @return
	 */
	public boolean existsMsgCustomer(String appId, String code, String source) {
		// return msgCache.existsMsgCustomer(appId, code, source);
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(code);
		params.add(source);
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "existsMsgCustomer", params);
		if (CollectionUtils.isNotEmpty(results)) {
			result = (Boolean) results.get(0);
		}

		return result;
	}

	/**
	 * 获取所有的客服消息
	 * @return
	 */
	public List<MsgCustomer> getMsgCustomerAll() {
		// return msgCache.getMsgCustomerAll();
		List<MsgCustomer> customers = null;
		List<Object> results = serveComm.get(CacheType.MSG_TEMP_CACHE, "getMsgTemplateAll", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			customers = JSON.parseArray(source, MsgCustomer.class);

		}

		return customers != null ? customers : new ArrayList<MsgCustomer>();
	}

	/**
	 * 添加/更新模板库模板
	 * @param msgCustomer
	 */
	public void updateMsgCustomer(MsgCustomer msgCustomer) {
		// msgCache.updateMsgCustomer(msgCustomer);
		List<Object> params = new ArrayList<Object>();
		params.add(msgCustomer);
		serveComm.set(CacheType.MSG_TEMP_CACHE, "updateMsgCustomer", params);
	}

	/**
	 * 删除模板库模板
	 * @param msgCustomer
	 */
	public void deleteMsgCustomer(MsgCustomer msgCustomer) {
		// msgCache.deleteMsgCustomer(msgCustomer);
		List<Object> params = new ArrayList<Object>();
		params.add(msgCustomer);
		serveComm.delete(CacheType.MSG_TEMP_CACHE, "deleteMsgCustomer", params);
	}
}
