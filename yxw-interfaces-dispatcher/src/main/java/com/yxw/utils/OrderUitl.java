package com.yxw.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwESService;
import com.yxw.mobileapp.invoke.OutsideConstants;

public class OrderUitl {

	public static String getPlatformByOrderNo(String esIndexType, String orderNo) {
		String platform = "";
		YxwESService yxwESService = SpringContextHolder.getBean("yxwESService");
		JSONObject resonpse = yxwESService.getPlatformByOrderNo(esIndexType, orderNo);
		if (resonpse.getInteger("total") == 1) {
			platform = JSONPath.eval(resonpse, "$.data[0].es_index").toString();
		}

		if (platform.startsWith(OutsideConstants.ES_INDEX_PREFIX_PT)) {
			// 新平台
			platform = OutsideConstants.SERVICEID_PREFIX_PT; // 统一平台
		} else if (platform.startsWith(OutsideConstants.ES_INDEX_PREFIX_STANDARD)) {
			// 标准平台
			platform = OutsideConstants.SERVICEID_PREFIX_STANDARD;
		}

		return platform;
	}

	public static String getPlatformByOrderNo(String orderNo) {
		String platform = "";

		if (orderNo.length() > 30) {
			// 新平台
			platform = OutsideConstants.SERVICEID_PREFIX_PT; // 统一平台
		} else {
			// 标准平台
			platform = OutsideConstants.SERVICEID_PREFIX_STANDARD;
		}

		return platform;
	}

	/**
	 * 订单号按平台类型分批
	 * 
	 * @param orderNos 订单号,逗号分隔
	 * @return Map<String, List<String>>
	 */
	public static Map<String, List<String>> orderNosSplitByPlatform(String orderNos) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		String[] orderNoArray = orderNos.split(",");

		String platform = "";
		for (String orderNo : orderNoArray) {
			platform = getPlatformByOrderNo(orderNo);
			List<String> list = map.get(platform);
			if (list != null) {
				list.add(orderNo);
			} else {
				list = new ArrayList<String>();
				list.add(orderNo);

				map.put(platform, list);
			}

		}

		return map;
	}

	/**
	 * 订单号按平台类型分批
	 * 
	 * @param orderNos 订单号,逗号分隔
	 * @return Map<String, List<String>>
	 */
	public static Map<String, List<String>> orderNosSplitByPlatform(String esIndexType, String orderNos) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String[] orderNoArray = orderNos.split(",");

		YxwESService yxwESService = SpringContextHolder.getBean("yxwESService");
		JSONObject resonpse = yxwESService.getPlatformByOrderNo(esIndexType, orderNoArray);

		if (resonpse.getInteger("total") > 0) {
			JSONArray data = resonpse.getJSONArray("data");

			String platform = "";
			for (int i = 0; i < data.size(); i++) {
				JSONObject item = data.getJSONObject(i);
				if (item.getString("es_index").startsWith(OutsideConstants.ES_INDEX_PREFIX_PT)) {
					// 新平台
					platform = OutsideConstants.SERVICEID_PREFIX_PT; // 统一平台
				} else if (item.getString("es_index").startsWith(OutsideConstants.ES_INDEX_PREFIX_STANDARD)) {
					// 标准平台
					platform = OutsideConstants.SERVICEID_PREFIX_STANDARD;
				}

				List<String> list = map.get(platform);
				if (list != null) {
					list.add(item.getString("order_no"));
				} else {
					list = new ArrayList<String>();
					list.add(item.getString("order_no"));

					map.put(platform, list);
				}

			}
		}

		return map;
	}

}
