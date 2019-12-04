package com.yxw.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.outside.service.YxwStatsService;
import com.yxw.utils.DateUtils;

@Controller
public class AgeGroupController {

	private static Logger logger = LoggerFactory.getLogger(AgeGroupController.class);

	@RequestMapping(value = "/ageGroup")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/ageGroupData").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/ageGroup/getDatas")
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

		return new RespBody(Status.OK, getAgeGroupDatas(areaCode, hospitalCode, beginMonth, endMonth));
	}

	private Map<String, Object> getAgeGroupDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		Map<String, Object> resultMap = new HashMap<>();
		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);

		// 查询solr
		String source = service.getAgeGroupDatas(areaCode, hospitalCode, beginMonth, endMonth);

		JSONObject jsonObject = JSON.parseObject(source);
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> entitiesMap = (Map<String, Map<String, String>>) JSONPath.eval(jsonObject, "$.result.items.entity");
		logger.info(JSON.toJSONString(entitiesMap));

		// 饼图：性别
		Map<String, Object> ageGroupScaleMap = new HashMap<>();
		ageGroupScaleMap.put("legend", Lists.newArrayList("0~12岁", "13~18岁", "19~29岁", "30~35岁", "36~50岁", "51~70岁", "70岁以上"));
		resultMap.put("ageGroupScale", ageGroupScaleMap);

		// 日期列表
		List<String> xData = new ArrayList<>();
		xData.addAll(entitiesMap.keySet());
		Collections.sort(xData);

		// 柱状图：按平台
		Map<String, Object> platformMap = new HashMap<>();
		platformMap.put("legend", ageGroupScaleMap.get("legend"));
		List<String> platformList = new ArrayList<>();
		platformList.add("微信平台");
		platformList.add("支付宝平台");
		List<Long> ageGroup0List = new ArrayList<>();
		List<Long> ageGroup1List = new ArrayList<>();
		List<Long> ageGroup2List = new ArrayList<>();
		List<Long> ageGroup3List = new ArrayList<>();
		List<Long> ageGroup4List = new ArrayList<>();
		List<Long> ageGroup5List = new ArrayList<>();
		List<Long> ageGroup6List = new ArrayList<>();
		platformMap.put("platforms", platformList);
		platformMap.put("ageGroup0", ageGroup0List);
		platformMap.put("ageGroup1", ageGroup1List);
		platformMap.put("ageGroup2", ageGroup2List);
		platformMap.put("ageGroup3", ageGroup3List);
		platformMap.put("ageGroup4", ageGroup4List);
		platformMap.put("ageGroup5", ageGroup5List);
		platformMap.put("ageGroup6", ageGroup6List);
		resultMap.put("platformAgeGroup", platformMap);

		// 双层的柱状图：每月年龄段- x轴为月份
		Map<String, Object> monthAgeGroupMap = new HashMap<>();
		monthAgeGroupMap.put("legend", ageGroupScaleMap.get("legend"));
		monthAgeGroupMap.put("xData", xData);
		List<Long> monthAgeGroup0 = new ArrayList<>();
		List<Long> monthAgeGroup1 = new ArrayList<>();
		List<Long> monthAgeGroup2 = new ArrayList<>();
		List<Long> monthAgeGroup3 = new ArrayList<>();
		List<Long> monthAgeGroup4 = new ArrayList<>();
		List<Long> monthAgeGroup5 = new ArrayList<>();
		List<Long> monthAgeGroup6 = new ArrayList<>();
		List<Long> monthTotal = new ArrayList<>();
		monthAgeGroupMap.put("monthAgeGroup0", monthAgeGroup0);
		monthAgeGroupMap.put("monthAgeGroup1", monthAgeGroup1);
		monthAgeGroupMap.put("monthAgeGroup2", monthAgeGroup2);
		monthAgeGroupMap.put("monthAgeGroup3", monthAgeGroup3);
		monthAgeGroupMap.put("monthAgeGroup4", monthAgeGroup4);
		monthAgeGroupMap.put("monthAgeGroup5", monthAgeGroup5);
		monthAgeGroupMap.put("monthAgeGroup6", monthAgeGroup6);
		monthAgeGroupMap.put("monthTotal", monthTotal);
		resultMap.put("monthAgeGroup", monthAgeGroupMap);

		long wxAgeGroup0 = 0L;
		long wxAgeGroup1 = 0L;
		long wxAgeGroup2 = 0L;
		long wxAgeGroup3 = 0L;
		long wxAgeGroup4 = 0L;
		long wxAgeGroup5 = 0L;
		long wxAgeGroup6 = 0L;

		long aliAgeGroup0 = 0L;
		long aliAgeGroup1 = 0L;
		long aliAgeGroup2 = 0L;
		long aliAgeGroup3 = 0L;
		long aliAgeGroup4 = 0L;
		long aliAgeGroup5 = 0L;
		long aliAgeGroup6 = 0L;

		for (String month : xData) {
			Map<String, String> monthMap = entitiesMap.get(month);
			// 微信值
			long monthWx0 = Long.valueOf(monthMap.get("wxAgeGroup0"));
			long monthWx1 = Long.valueOf(monthMap.get("wxAgeGroup1"));
			long monthWx2 = Long.valueOf(monthMap.get("wxAgeGroup2"));
			long monthWx3 = Long.valueOf(monthMap.get("wxAgeGroup3"));
			long monthWx4 = Long.valueOf(monthMap.get("wxAgeGroup4"));
			long monthWx5 = Long.valueOf(monthMap.get("wxAgeGroup5"));
			long monthWx6 = Long.valueOf(monthMap.get("wxAgeGroup6"));
			// 支付宝值
			long monthAli0 = Long.valueOf(monthMap.get("aliAgeGroup0"));
			long monthAli1 = Long.valueOf(monthMap.get("aliAgeGroup1"));
			long monthAli2 = Long.valueOf(monthMap.get("aliAgeGroup2"));
			long monthAli3 = Long.valueOf(monthMap.get("aliAgeGroup3"));
			long monthAli4 = Long.valueOf(monthMap.get("aliAgeGroup4"));
			long monthAli5 = Long.valueOf(monthMap.get("aliAgeGroup5"));
			long monthAli6 = Long.valueOf(monthMap.get("aliAgeGroup6"));

			// 微信值
			wxAgeGroup0 += monthWx0;
			wxAgeGroup1 += monthWx1;
			wxAgeGroup2 += monthWx2;
			wxAgeGroup3 += monthWx3;
			wxAgeGroup4 += monthWx4;
			wxAgeGroup5 += monthWx5;
			wxAgeGroup6 += monthWx6;
			// 支付宝值
			aliAgeGroup0 += monthAli0;
			aliAgeGroup1 += monthAli1;
			aliAgeGroup2 += monthAli2;
			aliAgeGroup3 += monthAli3;
			aliAgeGroup4 += monthAli4;
			aliAgeGroup5 += monthAli5;
			aliAgeGroup6 += monthAli6;

			monthAgeGroup0.add(monthWx0 + monthAli0);
			monthAgeGroup1.add(monthWx1 + monthAli1);
			monthAgeGroup2.add(monthWx2 + monthAli2);
			monthAgeGroup3.add(monthWx3 + monthAli3);
			monthAgeGroup4.add(monthWx4 + monthAli4);
			monthAgeGroup5.add(monthWx5 + monthAli5);
			monthAgeGroup6.add(monthWx6 + monthAli6);

			monthTotal.add(monthWx0 + monthWx1 + monthWx2 + monthWx3 + monthWx4 + monthWx5 + monthWx6 + monthAli0 + monthAli1 + monthAli2 + monthAli3
					+ monthAli4 + monthAli5 + monthAli6);
		}

		// 平台区分
		ageGroup0List.add(wxAgeGroup0);
		ageGroup0List.add(aliAgeGroup0);
		ageGroup1List.add(wxAgeGroup1);
		ageGroup1List.add(aliAgeGroup1);
		ageGroup2List.add(wxAgeGroup2);
		ageGroup2List.add(aliAgeGroup2);
		ageGroup3List.add(wxAgeGroup3);
		ageGroup3List.add(aliAgeGroup3);
		ageGroup4List.add(wxAgeGroup4);
		ageGroup4List.add(aliAgeGroup4);
		ageGroup5List.add(wxAgeGroup5);
		ageGroup5List.add(aliAgeGroup5);
		ageGroup6List.add(wxAgeGroup6);
		ageGroup6List.add(aliAgeGroup6);

		// 饼图统计，无关平台
		List<Long> ageGroupValues = new ArrayList<>();
		ageGroupValues.add(wxAgeGroup0 + aliAgeGroup0);
		ageGroupValues.add(wxAgeGroup1 + aliAgeGroup1);
		ageGroupValues.add(wxAgeGroup2 + aliAgeGroup2);
		ageGroupValues.add(wxAgeGroup3 + aliAgeGroup3);
		ageGroupValues.add(wxAgeGroup4 + aliAgeGroup4);
		ageGroupValues.add(wxAgeGroup5 + aliAgeGroup5);
		ageGroupValues.add(wxAgeGroup6 + aliAgeGroup6);
		ageGroupScaleMap.put("values", ageGroupValues);

		return resultMap;
	}
}
