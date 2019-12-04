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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.outside.service.YxwStatsService;
import com.yxw.utils.DateUtils;

/**
 * 描述: TODO<br>
 * 
 * @author Caiwq
 * @date 2015年12月31日
 */
@Controller
public class CardController {

	@RequestMapping(value = "/card")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/cardData").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/card/getDatas")
	public RespBody getDatas(HttpServletRequest request) {
		String areaCode = request.getParameter("areaCode");
		String hospitalCode = request.getParameter("hospitalCode");
		int dateCode = Integer.valueOf(request.getParameter("dateCode"));

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 1 上月(默认), 2 近3月, 3 近半年, 4本年度
		String endMonth = DateUtils.getFirstDayOfLastMonth(DateUtils.today());
		String beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -1);

		switch (dateCode) {
		case 2:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -2);
			break;
		case 3:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -5);
			break;
		case 4:
			beginMonth = DateUtils.getFirstDayOfThisYear(endMonth);
			break;
		case 5:
			beginMonth = startDate;
			endMonth = endDate;
			break;
		default:
			break;
		}

		return new RespBody(Status.OK, getCardDatas(areaCode, hospitalCode, beginMonth, endMonth));
	}

	public Map<String, Object> getCardDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		Map<String, Object> map = new HashMap<>();

		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		String source = service.getCardDatas(areaCode, hospitalCode, beginMonth.substring(0, 7), endMonth.substring(0, 7));

		JSONObject jsonSource = JSON.parseObject(source);
		JSONArray items = (JSONArray) JSONPath.eval(jsonSource, "$.result.items.entity");

		if (areaCode.equals("-1")) {
			// 仅按区域
			List<String> areaList = new ArrayList<>();
			List<String> monthList = new ArrayList<>();

			Map<String, Map<String, Map<String, Object>>> dataMap = new LinkedHashMap<>();
			for (Object object : items) {
				String areaName = ( (String) JSONPath.eval(object, "$.areaName") ).replaceAll("/", "");

				String thisMonth = (String) JSONPath.eval(object, "$.thisMonth");
				int wxCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.wxCumulateCountTillThisMonth");
				int aliCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.aliCumulateCountTillThisMonth");
				int thisMonthWxIncCount = (Integer) JSONPath.eval(object, "$.thisMonthWxIncCount");
				int thisMonthAliIncCount = (Integer) JSONPath.eval(object, "$.thisMonthAliIncCount");

				Map<String, Map<String, Object>> itemMap = null;
				if (dataMap.containsKey(areaName)) {
					itemMap = dataMap.get(areaName);
				} else {
					itemMap = new LinkedHashMap<>();
				}

				// 按月分
				Map<String, Object> monthMap = new LinkedHashMap<>();
				monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
				monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
				monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
				monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
				itemMap.put(thisMonth, monthMap);

				if (!areaList.contains(areaName))
					areaList.add(areaName);

				if (!monthList.contains(thisMonth))
					monthList.add(thisMonth);

				dataMap.put(areaName, itemMap);
			}

			map.put("areaList", areaList);
			map.put("monthList", monthList);
			map.put("datas", dataMap);
			map.put("resultType", 1);
			map.put("description", "显示所有区域的信息(无医院)");
		} else if (areaCode.equals("0")) {
			// 所有区域+所有区域下医院
			Map<String, Map<String, Map<String, Map<String, Object>>>> dataMap = new LinkedHashMap<>();
			List<String> hospitalList = new ArrayList<>();
			List<String> monthList = new ArrayList<>();
			for (Object object : items) {
				String areaName = ( (String) JSONPath.eval(object, "$.areaName") ).replaceAll("/", "");
				String hospitalName = ( (String) JSONPath.eval(object, "$.hospitalName") );
				String thisMonth = (String) JSONPath.eval(object, "$.thisMonth");
				int wxCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.wxCumulateCountTillThisMonth");
				int aliCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.aliCumulateCountTillThisMonth");
				int thisMonthWxIncCount = (Integer) JSONPath.eval(object, "$.thisMonthWxIncCount");
				int thisMonthAliIncCount = (Integer) JSONPath.eval(object, "$.thisMonthAliIncCount");

				Map<String, Map<String, Map<String, Object>>> areaMap = null;
				if (dataMap.containsKey(areaName)) {
					areaMap = dataMap.get(areaName);

					Map<String, Map<String, Object>> hospitalMap = null;
					if (areaMap.containsKey(hospitalName)) {
						hospitalMap = areaMap.get(hospitalName);

						Map<String, Object> monthMap = new LinkedHashMap<>();
						monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
						monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
						monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
						monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
						hospitalMap.put(thisMonth, monthMap);

					} else {
						hospitalMap = new LinkedHashMap<>();
						Map<String, Object> monthMap = new LinkedHashMap<>();
						monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
						monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
						monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
						monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
						hospitalMap.put(thisMonth, monthMap);
					}

					areaMap.put(hospitalName, hospitalMap);
				} else {
					areaMap = new LinkedHashMap<>();
					Map<String, Map<String, Object>> hospitalMap = new LinkedHashMap<>();
					Map<String, Object> monthMap = new LinkedHashMap<>();
					monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
					monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
					monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
					monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
					hospitalMap.put(thisMonth, monthMap);
					areaMap.put(hospitalName, hospitalMap);
				}

				if (!hospitalList.contains(hospitalName))
					hospitalList.add(hospitalName);

				if (!monthList.contains(thisMonth))
					monthList.add(thisMonth);

				dataMap.put(areaName, areaMap);
			}

			map.put("datas", dataMap);
			map.put("hospitalList", hospitalList);
			map.put("monthList", monthList);
			map.put("resultType", 2);
			map.put("description", "显示所有区域内所有医院的信息");
		} else {
			if (hospitalCode.equals("-1")) {
				// 单个区域下所有医院  -- areaName, 自己在前端页面拿吧，这边就不需要特别整理了
				Map<String, Map<String, Map<String, Object>>> dataMap = new LinkedHashMap<>();
				List<String> hospitalList = new ArrayList<>();
				List<String> monthList = new ArrayList<>();

				for (Object object : items) {
					String hospitalName = ( (String) JSONPath.eval(object, "$.hospitalName") );
					String thisMonth = (String) JSONPath.eval(object, "$.thisMonth");
					int wxCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.wxCumulateCountTillThisMonth");
					int aliCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.aliCumulateCountTillThisMonth");
					int thisMonthWxIncCount = (Integer) JSONPath.eval(object, "$.thisMonthWxIncCount");
					int thisMonthAliIncCount = (Integer) JSONPath.eval(object, "$.thisMonthAliIncCount");

					Map<String, Map<String, Object>> itemMap = null;
					if (dataMap.containsKey(hospitalName)) {
						itemMap = dataMap.get(hospitalName);
					} else {
						itemMap = new LinkedHashMap<>();
					}

					// 按月分
					Map<String, Object> monthMap = new LinkedHashMap<>();
					monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
					monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
					monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
					monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
					itemMap.put(thisMonth, monthMap);

					dataMap.put(hospitalName, itemMap);

					if (!hospitalList.contains(hospitalName))
						hospitalList.add(hospitalName);

					if (!monthList.contains(thisMonth))
						monthList.add(thisMonth);
				}

				map.put("datas", dataMap);
				map.put("hospitalList", hospitalList);
				map.put("monthList", monthList);
				map.put("resultType", 3);
				map.put("description", "显示单个区域内所有医院的信息");
			} else {
				// 单个区域下单个医院  -- areaName, 自己在前端页面拿吧，这边就不需要特别整理了
				Map<String, Map<String, Map<String, Object>>> dataMap = new LinkedHashMap<>();
				List<String> hospitalList = new ArrayList<>();
				List<String> monthList = new ArrayList<>();
				for (Object object : items) {
					String hospitalName = ( (String) JSONPath.eval(object, "$.hospitalName") );
					String thisMonth = (String) JSONPath.eval(object, "$.thisMonth");
					int wxCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.wxCumulateCountTillThisMonth");
					int aliCumulateCountTillThisMonth = (Integer) JSONPath.eval(object, "$.aliCumulateCountTillThisMonth");
					int thisMonthWxIncCount = (Integer) JSONPath.eval(object, "$.thisMonthWxIncCount");
					int thisMonthAliIncCount = (Integer) JSONPath.eval(object, "$.thisMonthAliIncCount");

					Map<String, Map<String, Object>> itemMap = null;
					if (dataMap.containsKey(hospitalName)) {
						itemMap = dataMap.get(hospitalName);
					} else {
						itemMap = new LinkedHashMap<>();
					}

					// 按月分
					Map<String, Object> monthMap = new LinkedHashMap<>();
					monthMap.put("wxCumulateCountTillThisMonth", wxCumulateCountTillThisMonth);
					monthMap.put("aliCumulateCountTillThisMonth", aliCumulateCountTillThisMonth);
					monthMap.put("thisMonthWxIncCount", thisMonthWxIncCount);
					monthMap.put("thisMonthAliIncCount", thisMonthAliIncCount);
					itemMap.put(thisMonth, monthMap);

					dataMap.put(hospitalName, itemMap);

					if (!hospitalList.contains(hospitalName))
						hospitalList.add(hospitalName);

					if (!monthList.contains(thisMonth))
						monthList.add(thisMonth);
				}

				map.put("datas", dataMap);
				map.put("hospitalList", hospitalList);
				map.put("monthList", monthList);
				map.put("resultType", 4);
				map.put("description", "显示单个区域内单个医院的信息");
			}
		}

		return map;
	}

}
