/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年12月31日
 * @version 1.0
 */
package com.yxw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.outside.constant.OutsideConstants;
import com.yxw.outside.service.YxwStatsService;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2015年12月31日  
 */
@Controller
public class IndexController {
	
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/index")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/index").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/getAllResume", method = RequestMethod.GET)
	public RespBody getAllResume(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 区域 -- 柱状图
		//		map.put("areaCategories", Lists.newArrayList("衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"));
		//		map.put("areaData", Lists.newArrayList(5, 20, 36, 10, 10, 20));

		// 饼状图是订单，后面做

		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		String resumeData = service.getAllResume();
		logger.info(resumeData);

		JSONObject sourceResume = JSON.parseObject(resumeData);
		map.putAll(getAreaHospitalInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.AREA_HOSPITAL_INFOS)));
		map.putAll(getSubscribeInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.SUBSCRIBE_INFOS)));
		map.putAll(getCardInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.CARD_INFOS)));
		map.putAll(getRegInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.REG_INFOS)));
		map.putAll(getClinicInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.CLINIC_INFOS)));
		map.putAll(getDepositInfos(JSONPath.eval(sourceResume, "$.result.items." + OutsideConstants.DEPOSIT_INFOS)));
		//		map.putAll(getTradingAmountInfos(sourceResume.get(OutsideConstants.TRADING_AMOUNT_INFOS)));

		return new RespBody(Status.OK, map);
	}

	/**
	 * 区域医院数
	 * @param object
	 * @return
	 */
	private Map<String, Object> getAreaHospitalInfos(Object object) {
		if (object != null) {
			Map<String, Object> resultMap = new HashMap<>();

			// hospitalCount|areaData[list<map>]
			int hospitalCount = 0;
			List<Map<String, Object>> dataList = new ArrayList<>();
			JSONArray datas = (JSONArray) object;
			for (Object obj : datas) {
				JSONObject jObj = (JSONObject) obj;
				hospitalCount += jObj.getIntValue("hospitalCount");
				Map<String, Object> map = new HashMap<>();
				map.put("name", jObj.getString("areaName").replaceAll("/", ""));
				map.put("value", jObj.getIntValue("hospitalCount"));

				// 可以让饼被吃了

				dataList.add(map);
			}

			resultMap.put("hospitalCount", hospitalCount);
			resultMap.put("areaData", dataList);

			return resultMap;
		}
		return null;
	}

	/**
	 * 关注数
	 * @param object
	 * @return
	 */
	private Map<String, Object> getSubscribeInfos(Object object) {
		if (object != null) {
			Map<String, Object> resultMap = new HashMap<>();

			// wxSubscribe|aliSubscribe|cardDatas[list<map>] -- hospitalName, aliData, wxData
			Map<String, Object> dataMap = new HashMap<>();
			List<String> hospitalList = new ArrayList<>();
			List<Integer> wxList = new ArrayList<>();
			List<Integer> aliList = new ArrayList<>();
			int wxCumulateCountTillThisMonth = 0;
			int aliCumulateCountTillThisMonth = 0;

			@SuppressWarnings("unchecked")
			Map<String, Map<String, Integer>> datas = JSONObject.toJavaObject((JSONObject) object, Map.class);
			for (Entry<String, Map<String, Integer>> hospitalEntry : datas.entrySet()) {
				String hospitalName = hospitalEntry.getKey();
				hospitalList.add(hospitalName);
				for (Entry<String, Integer> dataEntry : hospitalEntry.getValue().entrySet()) {
					int count = dataEntry.getValue();
					if (dataEntry.getKey().equals("wxCumulateCountTillThisMonth")) {
						wxList.add(count);
						wxCumulateCountTillThisMonth += count;
					} else {
						aliList.add(count);
						aliCumulateCountTillThisMonth += count;
					}
				}
			}

			resultMap.put("wxSubscribe", wxCumulateCountTillThisMonth);
			resultMap.put("aliSubscribe", aliCumulateCountTillThisMonth);
			dataMap.put("hospitals", hospitalList);
			dataMap.put("wxSubscribeCount", wxList);
			dataMap.put("aliSubscribeCount", aliList);

			resultMap.put("subscribeData", dataMap);

			return resultMap;
		}
		return null;
	}

	/**
	 * 绑卡数
	 * @param object
	 * @return
	 */
	private Map<String, Object> getCardInfos(Object object) {
		if (object != null) {
			Map<String, Object> resultMap = new HashMap<>();

			// cardCount|cardDatas[list<map>]  -- hospitalName, aliData, wxData
			Integer cardCount = 0;

			Map<String, Object> dataMap = new HashMap<>();
			List<String> hospitalList = new ArrayList<>();
			List<Integer> wxList = new ArrayList<>();
			List<Integer> aliList = new ArrayList<>();

			//			List<String> platforms = new ArrayList<>();
			//			platforms.add("微信绑卡数");
			//			platforms.add("支付宝绑卡数");

			@SuppressWarnings("unchecked")
			Map<String, Map<String, Integer>> datas = JSONObject.toJavaObject((JSONObject) object, Map.class);
			for (Entry<String, Map<String, Integer>> hospitalEntry : datas.entrySet()) {
				String hospitalName = hospitalEntry.getKey();
				hospitalList.add(hospitalName);
				for (Entry<String, Integer> dataEntry : hospitalEntry.getValue().entrySet()) {
					int count = dataEntry.getValue();
					cardCount += count;
					if (dataEntry.getKey().equals("wxCumulateCountTillThisMonth")) {
						wxList.add(count);
					} else {
						aliList.add(count);
					}
				}
			}

			dataMap.put("hospitals", hospitalList);
			dataMap.put("wxCardCount", wxList);
			dataMap.put("aliCardCount", aliList);

			resultMap.put("cardCount", cardCount);
			resultMap.put("cardData", dataMap);

			return resultMap;
		}
		return null;
	}

	/**
	 * 挂号数量、金额
	 * @param object
	 * @return
	 */
	private Map<String, Object> getRegInfos(Object object) {
		Map<String, Object> resultMap = new HashMap<>();

		if (object != null) {
			// cumulateCount|orderData[list]|orderCataXXX[list]
			JSONObject datas = (JSONObject) object;
			resultMap.put("regInfos", datas);
		}
		return resultMap;
	}

	/**
	 * 门诊数量、金额
	 * @param object
	 * @return
	 */
	private Map<String, Object> getClinicInfos(Object object) {
		Map<String, Object> resultMap = new HashMap<>();

		if (object != null) {
			// cumulateCount|orderData[list]|orderCataXXX[list]
			JSONObject datas = (JSONObject) object;
			resultMap.put("clinicInfos", datas);
		}
		return resultMap;
	}

	/**
	 * 押金数量、金额
	 * @param object
	 * @return
	 */
	private Map<String, Object> getDepositInfos(Object object) {
		Map<String, Object> resultMap = new HashMap<>();

		if (object != null) {
			// cumulateCount|orderData[list]|orderCataXXX[list]
			JSONObject datas = (JSONObject) object;
			resultMap.put("depositInfos", datas);
		}
		return resultMap;
	}
}
