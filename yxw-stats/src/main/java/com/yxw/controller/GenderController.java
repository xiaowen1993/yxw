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

/**
 * 
 * 功能概要：从绑卡信息中获取性别、年龄段的统计
 * @author Administrator
 * @date 2017年3月4日
 */
@Controller
public class GenderController {

	private static Logger logger = LoggerFactory.getLogger(GenderController.class);

	@RequestMapping(value = "/gender")
	public ModelAndView index(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/genderData").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/gender/getDatas")
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

		return new RespBody(Status.OK, getGenderDatas(areaCode, hospitalCode, beginMonth, endMonth));
	}

	public Map<String, Object> getGenderDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth) {
		Map<String, Object> resultMap = new HashMap<>();
		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);

		// 查询solr
		String source = service.getGenderDatas(areaCode, hospitalCode, beginMonth, endMonth);

		JSONObject jsonObject = JSON.parseObject(source);
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> entitiesMap =
				(Map<String, Map<String, String>>) JSONPath.eval(jsonObject, "$.result.items.entity");
		logger.info(JSON.toJSONString(entitiesMap));

		// 饼图：性别
		Map<String, Object> genderScaleMap = new HashMap<>();
		genderScaleMap.put("legend", Lists.newArrayList("男性", "女性"));
		resultMap.put("genderScale", genderScaleMap);

		// 平台性别定义
		long wxMale = 0L;
		long wxFemale = 0L;
		long aliMale = 0L;
		long aliFemale = 0L;

		// 双层的柱状图：每月性别 - x轴为月份
		Map<String, Object> monthGenderMap = new HashMap<>();
		List<String> xData = new ArrayList<>();
		xData.addAll(entitiesMap.keySet());
		Collections.sort(xData);
		monthGenderMap.put("xData", xData);
		List<Long> monthMaleList = new ArrayList<>();
		List<Long> monthFemaleList = new ArrayList<>();
		List<Long> monthTotal = new ArrayList<>();
		monthGenderMap.put("monthMaleList", monthMaleList);
		monthGenderMap.put("monthFemaleList", monthFemaleList);
		monthGenderMap.put("monthTotal", monthTotal);
		resultMap.put("monthGender", monthGenderMap);

		// 双层的柱状图: 平台性别 - x轴为平台
		Map<String, Object> platformGenderMap = new HashMap<>();
		List<String> platformList = new ArrayList<>();
		platformList.add("微信平台");
		platformList.add("支付宝平台");
		List<Long> maleList = new ArrayList<>();
		List<Long> femaleList = new ArrayList<>();
		platformGenderMap.put("platforms", platformList);
		platformGenderMap.put("maleList", maleList);
		platformGenderMap.put("femaleList", femaleList);
		resultMap.put("platformGender", platformGenderMap);

		// 按照month来
		for (String month : xData) {
			Map<String, String> monthMap = entitiesMap.get(month);

			long monthWxMale = Long.valueOf(monthMap.get("thisMonthWxMan"));
			long monthWxFemale = Long.valueOf(monthMap.get("thisMonthWxWoman"));
			long monthAliMale = Long.valueOf(monthMap.get("thisMonthAliMan"));
			long monthAliFemale = Long.valueOf(monthMap.get("thisMonthAliWoman"));

			// 每月性别，按月，不区分平台，即平台相加
			monthMaleList.add(monthWxMale + monthAliMale);
			monthFemaleList.add(monthWxFemale + monthAliFemale);

			// 平台性别，与月无关
			wxMale += monthWxMale;
			wxFemale += monthWxFemale;
			aliMale += monthAliMale;
			aliFemale += monthAliFemale;

			monthTotal.add(monthWxFemale + monthWxMale + monthAliFemale + monthAliMale);
		}

		maleList.add(wxMale);
		maleList.add(aliMale);
		femaleList.add(wxFemale);
		femaleList.add(aliFemale);

		genderScaleMap.put("values", Lists.newArrayList(wxMale + aliMale, wxFemale + aliFemale));

		return resultMap;
	}

}
