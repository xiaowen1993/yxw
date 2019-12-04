/**
 * Copyright© 2014-2015 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2015年12月31日
 * @version 1.0
 */
package com.yxw.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
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
public class OrderController {

	@RequestMapping(value = "/orderCount")
	public ModelAndView orderCountIndex(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/orderCountData").addObject("userName", userName);
	}

	@RequestMapping(value = "/orderAmount")
	public ModelAndView orderAmountIndex(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/orderAmountData").addObject("userName", userName);
	}

	@ResponseBody
	@RequestMapping(value = "/order/getDatas")
	public RespBody getDatas(HttpServletRequest request) {
		String areaCode = request.getParameter("areaCode");
		String hospitalCode = request.getParameter("hospitalCode");
		int dateCode = Integer.valueOf(request.getParameter("dateCode"));
		int statsType = Integer.valueOf(request.getParameter("statsType"));
		String statsField = request.getParameter("statsField"); // 看到底是count还是amount

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
		default:
			break;
		}

		return new RespBody(Status.OK, getOrderDatas(areaCode, hospitalCode, statsType, statsField, beginMonth, endMonth));
	}

	public Map<String, Object> getOrderDatas(String areaCode, String hospitalCode, Integer statsType, String statsField, String beginMonth,
			String endMonth) {
		Map<String, Object> resultMap = new HashMap<>();
		YxwStatsService service = SpringContextHolder.getBean(YxwStatsService.class);
		String source = service.getOrderDatas(areaCode, hospitalCode, beginMonth, endMonth);
		resultMap.putAll(getTotalAreaCumulateDatas(source, statsField, statsType));

		return resultMap;
	}

	private Map<String, Object> getTotalAreaCumulateDatas(String json, String statsField, int statsType) {
		Map<String, Object> resultMap = new HashMap<>();

		String subTitle = statsField.equals("count") ? "数量" : "金额";

		JSONObject jsonObject = JSON.parseObject(json);

		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> regMap =
				JSON.toJavaObject((JSONObject) JSONPath.eval(jsonObject, "$.result.items.statsRegister"), Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> clinicMap =
				JSON.toJavaObject((JSONObject) JSONPath.eval(jsonObject, "$.result.items.statsClinic"), Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> depositMap =
				JSON.toJavaObject((JSONObject) JSONPath.eval(jsonObject, "$.result.items.statsDeposit"), Map.class);

		// x轴信息 register, clinic, deposit 的数目应该是一样的
		List<String> xData = new ArrayList<>();
		xData.addAll(regMap.keySet());
		Collections.sort(xData);

		// 订单笔数 -- 折线
		Map<String, Object> orderCountMap = new HashMap<>();
		orderCountMap.put("legend", Lists.newArrayList("总订单" + subTitle));
		orderCountMap.put("xData", xData);
		List<Object> orderCounts = new ArrayList<>();
		Map<String, List<Object>> orderCountYData = new LinkedHashMap<>();
		orderCountYData.put("总订单" + subTitle, orderCounts);
		orderCountMap.put("yData", orderCountYData);

		// 三种类型订单 -- 折线
		Map<String, Object> orderTypeMap = new HashMap<>();
		orderTypeMap.put("legend", Lists.newArrayList("挂号订单" + subTitle, "门诊订单" + subTitle, "押金订单" + subTitle));
		orderTypeMap.put("xData", xData);
		List<Object> regCounts = new ArrayList<>();
		List<Object> clinicCounts = new ArrayList<>();
		List<Object> depositCounts = new ArrayList<>();
		Map<String, List<Object>> orderTypeYData = new LinkedHashMap<>();
		orderTypeYData.put("挂号订单" + subTitle, regCounts);
		orderTypeYData.put("门诊订单" + subTitle, clinicCounts);
		orderTypeYData.put("押金订单" + subTitle, depositCounts);
		orderTypeMap.put("yData", orderTypeYData);

		// 两种平台订单 -- 折线
		Map<String, Object> orderPlatformMap = new HashMap<>();
		orderPlatformMap.put("legend", Lists.newArrayList("微信平台订单" + subTitle, "支付宝平台订单" + subTitle));
		orderPlatformMap.put("xData", xData);
		List<Object> wxCounts = new ArrayList<>();
		List<Object> aliCounts = new ArrayList<>();
		Map<String, List<Object>> orderPlatformYData = new LinkedHashMap<>();
		orderPlatformYData.put("微信平台订单" + subTitle, wxCounts);
		orderPlatformYData.put("支付宝平台订单" + subTitle, aliCounts);
		orderPlatformMap.put("yData", orderPlatformYData);

		// 三种类型订单比例 -- 与日期无关，拿上一个月的 ，但是如果选择每月，则按照所选的区间内monthValue相加
		Map<String, Object> orderTypeScaleMap = new HashMap<>();
		orderTypeScaleMap.put("legend", Lists.newArrayList("总挂号订单" + subTitle, "总门诊订单" + subTitle, "总押金订单" + subTitle));

		// 两种平台订单比例 -- 与日期无关，拿上一个月的 ，但是如果选择每月，则按照所选的区间内monthValue相加
		Map<String, Object> orderPlatformScaleMap = new HashMap<>();
		orderPlatformScaleMap.put("legend", Lists.newArrayList("微信平台订单" + subTitle, "支付宝平台订单" + subTitle));

		// 挂号类型笔 -- 折线
		Map<String, Object> regTypeMap = new HashMap<>();
		regTypeMap.put("legend", Lists.newArrayList("当班订单" + subTitle, "预约订单" + subTitle));
		regTypeMap.put("xData", xData);
		List<Object> dutyCounts = new ArrayList<>();
		List<Object> appointmentCounts = new ArrayList<>();
		Map<String, List<Object>> regTypeYData = new LinkedHashMap<>();
		regTypeYData.put("当班订单" + subTitle, dutyCounts);
		regTypeYData.put("预约订单" + subTitle, appointmentCounts);
		regTypeMap.put("yData", regTypeYData);

		// 挂号类型比例 -- 与日期无关，拿上一个月的 ，但是如果选择每月，则按照所选的区间内monthValue相加
		Map<String, Object> regTypeScaleMap = new HashMap<>();
		regTypeScaleMap.put("legend", Lists.newArrayList("当班订单" + subTitle, "预约订单" + subTitle));

		// 订单支付未支付笔 -- 折线
		Map<String, Object> orderPaymentMap = new HashMap<>();
		orderPaymentMap.put("legend", Lists.newArrayList("支付订单" + subTitle, "未支付订单" + subTitle));
		orderPaymentMap.put("xData", xData);
		List<Object> payCounts = new ArrayList<>();
		List<Object> noPayCounts = new ArrayList<>();
		Map<String, List<Object>> orderPaymentYData = new LinkedHashMap<>();
		orderPaymentYData.put("支付订单" + subTitle, payCounts);
		orderPaymentYData.put("未支付订单" + subTitle, noPayCounts);
		orderPaymentMap.put("yData", orderPaymentYData);

		// 订单支付未支付比例-- 与日期无关，拿上一个月的 ，但是如果选择每月，则按照所选的区间内monthValue相加
		Map<String, Object> orderPaymentScaleMap = new HashMap<>();
		orderPaymentScaleMap.put("legend", Lists.newArrayList("支付订单" + subTitle, "未支付订单" + subTitle));

		if (statsType == 1) { // 累计
			if (statsField.equalsIgnoreCase("count")) {
				// 折线图据
				for (String month : xData) {
					// 挂号支付订单
					long regDutyPayCount = Long.valueOf(regMap.get(month).get("cumulateDutyPayCount"));
					long regAppointmentPayCount = Long.valueOf(regMap.get(month).get("cumulateAppointmentPayCount"));
					long regWxPayCount = Long.valueOf(regMap.get(month).get("cumulateWxTotalCount"));
					long regAliPayCount = Long.valueOf(regMap.get(month).get("cumulateAliTotalCount"));
					// 挂号的未支付订单
					long regWxDutyNoPayCount = Long.valueOf(regMap.get(month).get("cumulateWxDutyNoPayCount"));
					long regWxAppointmentNoPayCount = Long.valueOf(regMap.get(month).get("cumulateWxAppointmentNoPayCount"));
					long regAliAppointmentNoPayCount = Long.valueOf(regMap.get(month).get("cumulateAliAppointmentNoPayCount"));
					long regAliDutyNoPayCount = Long.valueOf(regMap.get(month).get("cumulateAliDutyNoPayCount"));
					// 门诊支付订单
					long clinicPayCount = Long.valueOf(clinicMap.get(month).get("cumulateTotalCount"));
					long clinicWxCount = Long.valueOf(clinicMap.get(month).get("cumulateWxTotalCount"));
					long clinicAliCount = Long.valueOf(clinicMap.get(month).get("cumulateAliTotalCount"));
					// 门诊未支付订单
					long clinicWxNoPayCount = Long.valueOf(clinicMap.get(month).get("cumulateWxNoPayCount"));
					long clinicAliNoPayCount = Long.valueOf(clinicMap.get(month).get("cumulateAliNoPayCount"));
					// 押金支付订单
					long depositPayCount = Long.valueOf(depositMap.get(month).get("cumulateTotalCount"));
					long depositWxCount = Long.valueOf(depositMap.get(month).get("cumulateWxTotalCount"));
					long depositAliCount = Long.valueOf(depositMap.get(month).get("cumulateAliTotalCount"));
					// 押金未支付订单
					long depositWxNoPayCount = Long.valueOf(depositMap.get(month).get("cumulateWxNoPayCount"));
					long depositAliNoPayCount = Long.valueOf(depositMap.get(month).get("cumulateAliNoPayCount"));

					// 统计
					long regCount =
							regDutyPayCount + regAppointmentPayCount + regWxDutyNoPayCount + regWxAppointmentNoPayCount
									+ regAliAppointmentNoPayCount + regAliDutyNoPayCount;
					long clinicCount = clinicPayCount + clinicWxNoPayCount + clinicAliNoPayCount;
					long depositCount = depositPayCount + depositWxNoPayCount + depositAliNoPayCount;
					long wxCount =
							regWxPayCount + regWxAppointmentNoPayCount + regWxDutyNoPayCount + clinicWxCount + clinicWxNoPayCount
									+ depositWxCount + depositWxNoPayCount;
					long aliCount =
							regAliAppointmentNoPayCount + regAliPayCount + regAliDutyNoPayCount + clinicAliCount + clinicAliNoPayCount
									+ depositAliCount + depositAliNoPayCount;
					long dutyCount = regDutyPayCount + regAliDutyNoPayCount + regWxDutyNoPayCount;
					long appointmentCount = regAppointmentPayCount + regAliAppointmentNoPayCount + regWxAppointmentNoPayCount;
					long payCount = regDutyPayCount + regAppointmentPayCount + clinicPayCount + depositPayCount;
					long noPayCount =
							regAliDutyNoPayCount + regAliAppointmentNoPayCount + regWxDutyNoPayCount + regWxAppointmentNoPayCount
									+ clinicWxNoPayCount + clinicAliNoPayCount + depositWxNoPayCount + depositAliNoPayCount;
					// 挂号订单
					regCounts.add(regCount);
					// 门诊订单
					clinicCounts.add(clinicCount);
					// 押金订单
					depositCounts.add(depositCount);
					// 微信订单
					wxCounts.add(wxCount);
					// 支付宝订单
					aliCounts.add(aliCount);
					// 当班订单
					dutyCounts.add(dutyCount);
					// 预约订单
					appointmentCounts.add(appointmentCount);
					// 订单支付情况 -- 已支付
					payCounts.add(payCount);
					// 订单支付情况 -- 未支付
					noPayCounts.add(noPayCount);

					// 总订单 -- 就是已支付+未支付
					orderCounts.add(payCount + noPayCount);
				}

				String key = xData.get(xData.size() - 1);
				// 饼图据 （累计，则选最后一个日期，如果是每月，则累计选中的几个月的据 ）
				// 订单类型 -- 只计算已支付订单
				// long regData = Long.valueOf(regMap.get(key).get("cumulateTotalCount"));
				// long clinicData = Long.valueOf(clinicMap.get(key).get("cumulateTotalCount"));
				// long depositData = Long.valueOf(depositMap.get(key).get("cumulateTotalCount"));

				long regData =
						Long.valueOf(regMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxDutyNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxAppointmentNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliAppointmentNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliDutyNoPayCount"));

				long clinicData =
						Long.valueOf(clinicMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateWxNoPayCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateAliNoPayCount"));

				long depositData =
						Long.valueOf(depositMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateWxNoPayCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateAliNoPayCount"));
				orderTypeScaleMap.put("values", Lists.newArrayList(regData, clinicData, depositData));

				// 订单平台
				long wxData =
						Long.valueOf(regMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxDutyNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxAppointmentNoPayCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateWxNoPayCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateWxTotalCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateWxNoPayCount"));
				long aliData =
						Long.valueOf(regMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliAppointmentNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliDutyNoPayCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateAliNoPayCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateAliTotalCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateAliNoPayCount"));
				orderPlatformScaleMap.put("values", Lists.newArrayList(wxData, aliData));
				// 挂号类型
				long dutyData =
						Long.valueOf(regMap.get(key).get("cumulateDutyPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxDutyNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliDutyNoPayCount"));
				long appointmentData =
						Long.valueOf(regMap.get(key).get("cumulateAppointmentPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateWxAppointmentNoPayCount"))
								+ Long.valueOf(regMap.get(key).get("cumulateAliAppointmentNoPayCount"));
				regTypeScaleMap.put("values", Lists.newArrayList(dutyData, appointmentData));
				// 订单支付情况
				long payData =
						Long.valueOf(regMap.get(key).get("cumulateTotalCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateTotalCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateTotalCount"));
				long noPayData =
						Long.valueOf(regMap.get(key).get("cumulateNoPayCount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateNoPayCount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateNoPayCount"));
				orderPaymentScaleMap.put("values", Lists.newArrayList(payData, noPayData));
			} else if (statsField.equalsIgnoreCase("amount")) {
				// 折线图据
				for (String month : xData) {
					// 总订单
					orderCounts.add(Long.valueOf(regMap.get(month).get("cumulateTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("cumulateTotalAmount"))
							+ Long.valueOf(depositMap.get(month).get("cumulateTotalAmount")));
					// 挂号订单
					regCounts.add(Long.valueOf(regMap.get(month).get("cumulateTotalAmount")));
					// 门诊订单
					clinicCounts.add(Long.valueOf(clinicMap.get(month).get("cumulateTotalAmount")));
					// 押金订单
					depositCounts.add(Long.valueOf(depositMap.get(month).get("cumulateTotalAmount")));
					// 微信订单
					wxCounts.add(Long.valueOf(regMap.get(month).get("cumulateWxTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("cumulateWxTotalAmount"))
							+ Long.valueOf(depositMap.get(month).get("cumulateWxTotalAmount")));
					// 支付宝订单
					aliCounts.add(Long.valueOf(regMap.get(month).get("cumulateAliTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("cumulateAliTotalAmount"))
							+ Long.valueOf(depositMap.get(month).get("cumulateAliTotalAmount")));
					// 当班订单
					// 订单支付情况 -- 已支付
					payCounts.add(Long.valueOf(regMap.get(month).get("cumulateTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("cumulateTotalAmount"))
							+ Long.valueOf(depositMap.get(month).get("cumulateTotalAmount")));
					// 订单支付情况 -- 未支付 （未支付的，没有计算金额了，狗日的）
				}

				String key = xData.get(xData.size() - 1);
				// 饼图据 （累计，则选最后一个日期，如果是每月，则累计选中的几个月的据 ）
				// 订单类型
				long regData = Long.valueOf(regMap.get(key).get("cumulateTotalAmount"));
				long clinicData = Long.valueOf(clinicMap.get(key).get("cumulateTotalAmount"));
				long depositData = Long.valueOf(depositMap.get(key).get("cumulateTotalAmount"));
				orderTypeScaleMap.put("values", Lists.newArrayList(regData, clinicData, depositData));
				// 订单平台
				long wxData =
						Long.valueOf(regMap.get(key).get("cumulateWxTotalAmount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateWxTotalAmount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateWxTotalAmount"));
				long aliData =
						Long.valueOf(regMap.get(key).get("cumulateAliTotalAmount"))
								+ Long.valueOf(clinicMap.get(key).get("cumulateAliTotalAmount"))
								+ Long.valueOf(depositMap.get(key).get("cumulateAliTotalAmount"));
				orderPlatformScaleMap.put("values", Lists.newArrayList(wxData, aliData));
				// 挂号类型
			}
		} else if (statsType == 2) {
			if (statsField.equalsIgnoreCase("count")) {
				// 饼图据 （累计，则选最后一个日期，如果是每月，则累计选中的几个月的据 ）
				// 订单类型
				long regData = 0;
				long clinicData = 0;
				long depositData = 0;
				// 订单平台
				long wxData = 0;
				long aliData = 0;
				// 挂号类型
				long dutyData = 0;
				long appointmentData = 0;
				// 订单支付情况
				long payData = 0;
				long noPayData = 0;

				// 折线图据
				for (String month : xData) {
					// 总订单
					// 挂号支付
					long regWxDutyPayCount = Long.valueOf(regMap.get(month).get("monthDutyWxPayCount"));
					long regWxAppointmentPayCount = Long.valueOf(regMap.get(month).get("monthAppointmentWxPayCount"));
					long regAliDutyPayCount = Long.valueOf(regMap.get(month).get("monthDutyAliPayCount"));
					long regAliAppointmentPayCount = Long.valueOf(regMap.get(month).get("monthAppointmentAliPayCount"));
					// 挂号的未支付订单
					long regWxDutyNoPayCount = Long.valueOf(regMap.get(month).get("monthWxDutyNoPayCount"));
					long regWxAppointmentNoPayCount = Long.valueOf(regMap.get(month).get("monthWxAppointmentNoPayCount"));
					long regAliAppointmentNoPayCount = Long.valueOf(regMap.get(month).get("monthAliAppointmentNoPayCount"));
					long regAliDutyNoPayCount = Long.valueOf(regMap.get(month).get("monthAliDutyNoPayCount"));
					// 门诊支付
					long clinicWxPayCount = Long.valueOf(clinicMap.get(month).get("monthWxPayCount"));
					long clinicAliPayCount = Long.valueOf(clinicMap.get(month).get("monthAliPayCount"));
					// 门诊未支付订单
					long clinicWxNoPayCount = Long.valueOf(clinicMap.get(month).get("monthWxNoPayCount"));
					long clinicAliNoPayCount = Long.valueOf(clinicMap.get(month).get("monthAliNoPayCount"));
					// 押金支付
					long depositWxPayCount = Long.valueOf(depositMap.get(month).get("monthWxPayCount"));
					long depositAliPayCount = Long.valueOf(depositMap.get(month).get("monthAliPayCount"));
					// 押金未支付订单
					long depositWxNoPayCount = Long.valueOf(depositMap.get(month).get("monthWxNoPayCount"));
					long depositAliNoPayCount = Long.valueOf(depositMap.get(month).get("monthAliNoPayCount"));

					// 挂号订单
					regCounts.add(regWxAppointmentNoPayCount + regWxAppointmentPayCount + regWxDutyNoPayCount + regWxDutyPayCount
							+ regAliAppointmentNoPayCount + regAliAppointmentPayCount + regAliDutyNoPayCount + regAliDutyPayCount);
					// 门诊订单
					clinicCounts.add(clinicWxNoPayCount + clinicWxPayCount + clinicAliNoPayCount + clinicAliPayCount);
					// 押金订单
					depositCounts.add(depositAliNoPayCount + depositAliPayCount + depositWxNoPayCount + depositWxPayCount);
					// 微信订单
					wxCounts.add(regWxAppointmentNoPayCount + regWxAppointmentPayCount + regWxDutyNoPayCount + regWxDutyPayCount
							+ clinicWxNoPayCount + clinicWxPayCount + depositWxNoPayCount + depositWxPayCount);
					// 支付宝订单
					aliCounts.add(regAliAppointmentNoPayCount + regAliAppointmentPayCount + regAliDutyNoPayCount + regAliDutyPayCount
							+ clinicAliNoPayCount + clinicAliPayCount + depositAliNoPayCount + depositAliPayCount);
					// 当班订单
					dutyCounts.add(regWxDutyNoPayCount + regWxDutyPayCount + regAliDutyNoPayCount + regAliDutyPayCount);
					// 预约订单
					appointmentCounts.add(regWxAppointmentNoPayCount + regWxAppointmentPayCount + regAliAppointmentNoPayCount
							+ regAliAppointmentPayCount);

					// 订单支付情况 -- 已支付
					long payCount =
							regWxAppointmentPayCount + regWxDutyPayCount + regAliAppointmentPayCount + regAliDutyPayCount
									+ clinicWxPayCount + clinicAliPayCount + depositWxPayCount + depositAliPayCount;
					payCounts.add(payCount);
					// 订单支付情况 -- 未支付
					long noPayCount =
							regWxAppointmentNoPayCount + regWxDutyNoPayCount + regAliAppointmentNoPayCount + regAliDutyNoPayCount
									+ clinicWxNoPayCount + clinicAliNoPayCount + depositWxNoPayCount + depositAliNoPayCount;
					noPayCounts.add(noPayCount);

					// 总订单 -- 就是已支付+未支付
					orderCounts.add(payCount + noPayCount);

					// 下面是饼图数据
					regData +=
							regAliAppointmentNoPayCount + regAliAppointmentPayCount + regAliDutyNoPayCount + regAliDutyPayCount
									+ regWxAppointmentNoPayCount + regWxAppointmentPayCount + regWxDutyNoPayCount + regWxDutyPayCount;
					clinicData += clinicWxNoPayCount + clinicWxPayCount + clinicAliNoPayCount + clinicAliPayCount;
					depositData += depositAliNoPayCount + depositAliPayCount + depositWxNoPayCount + depositWxPayCount;

					wxData +=
							regWxAppointmentNoPayCount + regWxAppointmentPayCount + regWxDutyNoPayCount + regWxDutyPayCount
									+ clinicWxNoPayCount + clinicWxPayCount + depositWxNoPayCount + depositWxPayCount;
					aliData +=
							regAliAppointmentNoPayCount + regAliAppointmentPayCount + regAliDutyNoPayCount + regAliDutyPayCount
									+ clinicAliNoPayCount + clinicAliPayCount + depositAliNoPayCount + depositAliPayCount;

					dutyData += regWxDutyNoPayCount + regWxDutyPayCount + regAliDutyNoPayCount + regAliDutyPayCount;
					appointmentData +=
							regWxAppointmentNoPayCount + regWxAppointmentPayCount + regAliAppointmentNoPayCount + regAliAppointmentPayCount;

					payData +=
							regWxAppointmentPayCount + regWxDutyPayCount + regAliAppointmentPayCount + regAliDutyPayCount
									+ clinicWxPayCount + clinicAliPayCount + depositWxPayCount + depositAliPayCount;
					noPayData +=
							regWxAppointmentNoPayCount + regWxDutyNoPayCount + regAliAppointmentNoPayCount + regAliDutyNoPayCount
									+ clinicWxNoPayCount + clinicAliNoPayCount + depositWxNoPayCount + depositAliNoPayCount;
				}

				orderTypeScaleMap.put("values", Lists.newArrayList(regData, clinicData, depositData));
				orderPlatformScaleMap.put("values", Lists.newArrayList(wxData, aliData));
				regTypeScaleMap.put("values", Lists.newArrayList(dutyData, appointmentData));
				orderPaymentScaleMap.put("values", Lists.newArrayList(payData, noPayData));
			} else if (statsField.equalsIgnoreCase("amount")) {
				// 饼图据 （累计，则选最后一个日期，如果是每月，则累计选中的几个月的据 ）
				// 订单类型
				long regData = 0;
				long clinicData = 0;
				long depositData = 0;
				// 订单平台
				long wxData = 0;
				long aliData = 0;

				for (String month : xData) {
					// 总订单
					orderCounts.add(Long.valueOf(regMap.get(month).get("monthTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("monthPayAmount"))
							+ Long.valueOf(depositMap.get(month).get("monthPayAmount")));
					// 挂号订单
					regCounts.add(Long.valueOf(regMap.get(month).get("monthTotalAmount")));
					// 门诊订单
					clinicCounts.add(Long.valueOf(clinicMap.get(month).get("monthPayAmount")));
					// 押金订单
					depositCounts.add(Long.valueOf(depositMap.get(month).get("monthPayAmount")));
					// 微信订单
					wxCounts.add(Long.valueOf(regMap.get(month).get("monthWxTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("monthWxPayAmount"))
							+ Long.valueOf(depositMap.get(month).get("monthWxPayAmount")));
					// 支付宝订单
					aliCounts.add(Long.valueOf(regMap.get(month).get("monthAliTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("monthAliPayAmount"))
							+ Long.valueOf(depositMap.get(month).get("monthAliPayAmount")));
					// 当班订单
					// 订单支付情况 -- 已支付
					payCounts.add(Long.valueOf(regMap.get(month).get("monthTotalAmount"))
							+ Long.valueOf(clinicMap.get(month).get("monthPayAmount"))
							+ Long.valueOf(depositMap.get(month).get("monthPayAmount")));
					// 订单支付情况 -- 未支付 （未支付的，没有计算金额了，狗日的）

					regData += Long.valueOf(regMap.get(month).get("monthTotalAmount"));
					clinicData += Long.valueOf(clinicMap.get(month).get("monthPayAmount"));
					depositData += Long.valueOf(depositMap.get(month).get("monthPayAmount"));

					wxData =
							+Long.valueOf(regMap.get(month).get("monthWxTotalAmount"))
									+ Long.valueOf(clinicMap.get(month).get("monthWxPayAmount"))
									+ Long.valueOf(depositMap.get(month).get("monthWxPayAmount"));
					aliData =
							+Long.valueOf(regMap.get(month).get("monthAliTotalAmount"))
									+ Long.valueOf(clinicMap.get(month).get("monthAliPayAmount"))
									+ Long.valueOf(depositMap.get(month).get("monthAliPayAmount"));
				}

				orderTypeScaleMap.put("values", Lists.newArrayList(regData, clinicData, depositData));
				orderPlatformScaleMap.put("values", Lists.newArrayList(wxData, aliData));
			}
		}

		resultMap.put("order", orderCountMap);
		resultMap.put("orderType", orderTypeMap);
		resultMap.put("orderPlatform", orderPlatformMap);
		resultMap.put("orderTypeScale", orderTypeScaleMap);
		resultMap.put("orderPlatformScale", orderPlatformScaleMap);
		resultMap.put("regType", regTypeMap);
		resultMap.put("regTypeScale", regTypeScaleMap);
		resultMap.put("orderPayment", orderPaymentMap);
		resultMap.put("orderPaymentScale", orderPaymentScaleMap);

		return resultMap;
	}

}
