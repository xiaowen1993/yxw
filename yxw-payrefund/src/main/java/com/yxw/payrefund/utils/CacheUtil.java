package com.yxw.payrefund.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.CommonConstant;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.AccessTokenVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody.Status;

public class CacheUtil {

	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	private static ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public static HospitalCodeAndAppVo getPayInfoByCache(String hospitalCode, String tradeMode) {
		logger.info("hospitalCode: {}, tradeMode: {}", hospitalCode, tradeMode);

		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(tradeMode);
		HospitalCodeAndAppVo vo = null;
		List<Object> results = serveComm.get(CacheType.PAY_INFO_CACHE, "getPayInfo", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospitalCodeAndAppVo) results.get(0);
		}

		return vo;
	}

	public static void setWechatComponentVerifyTicket(String componentAppId, String componentVerifyTicket) {
		List<Object> params = new ArrayList<Object>();
		params.add(componentAppId);
		params.add(componentVerifyTicket);
		serveComm.set(CacheType.COMPONENT_VERIFY_TICKET_CACHE, "updateComponentVerifyTicket", params);
	}

	public static String getWechatComponentVerifyTicket(String componentAppId) {
		List<Object> params = new ArrayList<Object>();
		params.add(componentAppId);
		List<Object> list = serveComm.get(CacheType.COMPONENT_VERIFY_TICKET_CACHE, "getComponentVerifyTicketByAppId", params);
		if (list != null && list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}

	public static String getWechatAccessToken(String appId) {
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.ACCESS_TOKEN_CACHE, "getTokenByAppId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			AccessTokenVo vo = (AccessTokenVo) results.get(0);
			return vo.getAccessToken();
		}
		return null;
	}

	public static JSONObject getPaySettingCache(final String pharmacyCode) {
		JSONObject jsonObject = null;
		try {
			String url = CommonConstant.TRADE_RESTFUL_URL_PREFIX + CommonConstant.TRADE_RESTFUL_PAYSETTING_PATH;

			String res = HttpClientUtil.getInstance().post(url, new HashMap<String, String>() {
				{
					put("pharmacyCode", pharmacyCode);
				}
			});

			if (StringUtils.isNoneBlank(res)) {
				JSONObject rjsonObject = JSONObject.parseObject(res);
				if (StringUtils.equals(rjsonObject.getString("status"), Status.OK.toString())) {
					jsonObject = rjsonObject.getJSONObject("message");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return jsonObject;

	}

}
