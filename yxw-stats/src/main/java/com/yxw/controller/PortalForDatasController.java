package com.yxw.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.utils.DateUtils;

/**
 * 定制化的数据展示
 * @author jonf
 *
 */
@Controller
public class PortalForDatasController {

	private static Logger logger = LoggerFactory.getLogger(PortalForDatasController.class);

	@RequestMapping(value = "/portal")
	public ModelAndView portalIndex(Model model, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("userName");
		return new ModelAndView("/portalForDatas").addObject("userName", userName);
	}

	/**
	 * 关注数相关统计数据【累计、新增】（截止到当前月，按月份统计）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/portal/getSubscribeDatas")
	public RespBody getSubscribeDatas(HttpServletRequest request) {

		SubscribeController subscribeController = new SubscribeController();

		String areaCode = "-1";
		String hospitalCode = "-1";
		int dateCode = 6;

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
		case 6:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -11);//近1年
			break;
		default:
			break;
		}

		return new RespBody(Status.OK, subscribeController.getSubscribeDatas(areaCode, hospitalCode, beginMonth, endMonth));

	}

	/**
	 * 绑卡用户数相关统计数据【累计、新增】（截止到当前月，按月份统计）
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/portal/getCardDatas")
	public RespBody getCardDatas(HttpServletRequest request) {

		CardController cardController = new CardController();

		String areaCode = "-1";
		String hospitalCode = "-1";
		int dateCode = 6;

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
		case 6:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -11);//近1年
			break;
		default:
			break;
		}

		return new RespBody(Status.OK, cardController.getCardDatas(areaCode, hospitalCode, beginMonth, endMonth));
	}

	/**
	 * 订单量【累计、新增】,订单缴费金额【累计、新增】（截止到当前月，按月份统计）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/portal/getOrderDatas")
	public RespBody getOrderDatas(HttpServletRequest request) {

		OrderController orderController = new OrderController();

		String areaCode = "-1";
		String hospitalCode = "-1";

		int dateCode = 6;//近一年
		int statsType = Integer.valueOf(request.getParameter("statsType"));//1,累计；2,每月
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
			break;
		case 6:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -11);//近1年
			break;
		default:
			break;
		}

		return new RespBody(Status.OK, orderController.getOrderDatas(areaCode, hospitalCode, statsType, statsField, beginMonth, endMonth));

	}

	/**
	 * 上月使用人群男女占比（上月）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/portal/getSexProportionOrderDatas")
	public RespBody getSexProportionOrderDatas(HttpServletRequest request) {
		GenderController genderController = new GenderController();

		String areaCode = "-1";
		String hospitalCode = "-1";
		int dateCode = 6;//上月

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
		case 6:
			beginMonth = DateUtils.getFirstDayOfMonth(endMonth, -11);//近1年
			break;
		default:
			break;
		}

		return new RespBody(Status.OK, genderController.getGenderDatas(areaCode, hospitalCode, beginMonth, endMonth));
	}

}
