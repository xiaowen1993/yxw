package com.yxw.easyhealth.biz.clinic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.TradeParamsVo;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.ServerNoGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetail;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;
import com.yxw.payrefund.utils.TradeCommonHoder;

@Controller
@RequestMapping("/app/clinic/detail")
public class ClinicPayDetailController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(ClinicPayDetailController.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);

	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 待缴费明细
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView toPayDetail(ClinicPayVo vo, HttpServletRequest request) {
		if (logger.isDebugEnabled()) {
			logger.debug("[pay detail index].. clinicPayVo: {}", JSON.toJSONString(vo));
		}
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		// 补全个人信息
		FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
		Family family = familyService.findFamilyInfo(vo.getFamilyId(), vo.getOpenId());
		vo.setPatientMobile(family.getMobile());
		vo.setPatientName(family.getName());

		ModelAndView modelAndView = null;

		try {
			CodeAndInterfaceVo codeAndInterfaceVo = null;
			List<Object> params = new ArrayList<Object>();
			params.add(vo.getHospitalCode());
			params.add(vo.getBranchHospitalCode());
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getCodeAndInterfaceVo", params);

			if (!CollectionUtils.isEmpty(results)) {
				codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);
			} else {
				params.clear();
				params.add(vo.getHospitalCode());
				results = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
				if (!CollectionUtils.isEmpty(results)) {
					codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);
				}
			}

			String branchId = codeAndInterfaceVo.getBranchHospitalId();
			vo.setBranchHospitalId(branchId);

			modelAndView = new ModelAndView("/easyhealth/biz/clinic/payDetail");

			RulePayment payRule = ruleConfigManager.getRulePaymentByHospitalCode(vo.getHospitalCode());
			modelAndView.addObject("tradeTypes", payRule.getTradeTypesMap().get(vo.getAppCode()));
			modelAndView.addObject("defaultTradeType", payRule.getDefaultTradeTypesMap().get(vo.getAppCode()));

			RuleClinic ruleClinic = ruleConfigManager.getRuleClinicByHospitalCode(vo.getHospitalCode());
			modelAndView.addObject("ruleClinic", ruleClinic);

			RuleFriedExpress ruleFriedExpress = ruleConfigManager.getRuleFriedExpressByHospitalCode(vo.getHospitalCode());
			modelAndView.addObject("ruleFriedExpress", ruleFriedExpress);

			modelAndView.addObject(BizConstant.TRADE_URL_KEY, TradeCommonHoder.getTradeUrl());
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);

			// 支付信息
			BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
			List<PayMode> payModes = baseDatasManager.getPayModesByPlatformCode(vo.getAppCode());

			modelAndView.addObject("payModes", payModes);
		} catch (Exception e) {
			logger.error("待缴费明细获取异常, 错误信息：{}, 原因： {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return modelAndView;
	}

	/**
	 * 异步查询待缴费明细
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	public Object getDatas(ClinicPayVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("hospitalId", vo.getHospitalId());
		reqMap.put("hospitalCode", vo.getHospitalCode());
		reqMap.put("branchHospitalCode", vo.getBranchHospitalCode());

		if (StringUtils.isEmpty(vo.getBranchHospitalCode()) || "null".equalsIgnoreCase(vo.getBranchHospitalCode())) {
			CodeAndInterfaceVo codeAndInterfaceVo = null;
			List<Object> params = new ArrayList<Object>();
			params.add(vo.getHospitalId());
			//			params.add(vo.getHospitalCode());
			//			params.add(vo.getBranchHospitalCode());
			//			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getCodeAndInterfaceVo", params);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getHospitalInfoById", params);

			if (!CollectionUtils.isEmpty(results)) {
				codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);
			}

			reqMap.put("branchHospitalCode", codeAndInterfaceVo.getBranchHospitalCode());
		}

		reqMap.put("mzFeeId", vo.getMzFeeId());
		reqMap.put("appCode", vo.getAppCode());
		reqMap.put("cardNo", vo.getCardNo());
		reqMap.put("cardType", vo.getCardType());
		List<MZFeeDetail> list = clinicBizManager.getMZFeeDetail(reqMap);

		resultMap.put("list", list);
		return new RespBody(Status.OK, resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
	public RespBody generateOrder(HttpServletRequest request, ClinicRecord record) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(record.getOpenId())) {
			record.setOpenId(getOpenId(request));
		}

		if (StringUtils.isBlank(record.getAppUrl())) {
			record.setAppUrl(getAppUrl(request));
		}

		ClinicRecord entity = clinicService.findSavedUnpaidClinicRecord(record); // 旧单
		Object pay = null;

		// 确定交易方式
		int tradeMode = record.getTradeMode();
		int platformMode = ModeTypeUtil.getPlatformModeType(record.getAppCode());
		RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(record.getHospitalCode());
		if (ruleEdit != null && ruleEdit.getIsCompatibleOtherPlatform().intValue() == RuleConstant.IS_COMPATIBLE_OTHER_PLATFORM_YES) {//兼容医院不修改接口
			/*String tradeModeCode = ModeTypeUtil.getTradeModeCode(tradeMode);
			if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
				platformMode = BizConstant.MODE_TYPE_WECHAT_VAL;
				tradeMode = TradeConstant.TRADE_MODE_WECHAT_VAL;
			} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
				platformMode = BizConstant.MODE_TYPE_ALIPAY_VAL;
				tradeMode = TradeConstant.TRADE_MODE_ALIPAY_VAL;
			} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
				// TODO 新增
			}*/

		}

		if (entity != null) { // 原有了未支付的订单
			String newPayFee = record.getPayFee();
			String originalPayFee = entity.getPayFee();
			if (newPayFee != null && originalPayFee != null && !originalPayFee.equals(newPayFee)) { // 金额发生了变化
				// 金额改变，删掉旧单，并清除缓存数据
				ClinicRecord newOrder = new ClinicRecord(); // 新单
				BeanUtils.copyProperties(entity, newOrder);
				// 新订单号
				String orderNo = "";
				try {
					orderNo =
							OrderNoGenerator.genOrderNo(String.valueOf(platformMode), String.valueOf(tradeMode),
									OrderConstant.ORDER_TYPE_PAYMENT, BizConstant.BIZ_TYPE_CLINIC, ServerNoGenerator.getServerNoByIp(),
									record.getOpenId());
				} catch (Exception e) {
					throw new SystemException(e.getCause());
				}
				//				String orderNo = OrderNoGenerator.genOrderNo(record.getOrderType(), BizConstant.ORDER_TYPE_PAYMENT, BizConstant.BIZ_TYPE_CLINIC);
				// 把订单数据弄好，放到缓存
				newOrder.setOrderNo(orderNo);
				newOrder.setOrderNoHashVal(Math.abs(orderNo.hashCode()));
				Long currentTime = new Date().getTime();
				newOrder.setUpdateTime(currentTime);
				newOrder.setPayIds(record.getPayIds());
				newOrder.setAppUrl(getAppUrl(request));
				newOrder.setMedicareType(record.getMedicareType());
				newOrder.setMedicareFee(record.getMedicareFee());
				newOrder.setTotalFee(record.getTotalFee());
				newOrder.setPayFee(record.getPayFee());

				// tradeMode可能会改变了
				newOrder.setTradeMode(tradeMode);
				newOrder.setPlatformMode(platformMode);

				// 处理下handlelog
				StringBuffer sbLog = new StringBuffer();
				sbLog.append(entity.getHandleLog());
				sbLog.append(".;");
				sbLog.append(BizConstant.YYYYMMDDHHMMSS.format(new Date()));
				sbLog.append(":原单[").append(entity.getOrderNo()).append("]订单金额改变，重新下单。(未支付未缴费)");
				newOrder.setHandleLog(sbLog.toString());
				newOrder.setHandleCount(0);
				clinicService.coverUnpaidRecord(newOrder, entity);

				pay = clinicService.buildPayInfo(newOrder);
				logger.info("支付信息：" + JSON.toJSONString(pay));
			} else { // 金额一致
				// 金额没改变
				// tradeMode可能会改，要判断后，如果有改动则重新入库了
				// 若改动tradeMode必须要
				if (entity.getTradeMode() != tradeMode || entity.getPlatformMode() != platformMode) {
					entity.setTradeMode(tradeMode);
					entity.setPlatformMode(platformMode);
					// 保存数据
					clinicService.coverUnpaidRecord(entity, entity);
				}

				entity.setAppUrl(getAppUrl(request));

				pay = clinicService.buildPayInfo(entity);
				logger.info("支付信息：" + JSON.toJSONString(pay));
			}
		} else { // 新增部分
			String orderNo = "";
			Long currentTime;
			try {
				orderNo =
						OrderNoGenerator.genOrderNo(String.valueOf(platformMode), String.valueOf(tradeMode),
								OrderConstant.ORDER_TYPE_PAYMENT, BizConstant.BIZ_TYPE_CLINIC, ServerNoGenerator.getServerNoByIp(),
								record.getOpenId());

				currentTime = OrderNoGenerator.getDateTime(orderNo).getTime();
			} catch (Exception e) {
				throw new SystemException(e.getCause());
			}

			//			String orderNo =
			//					OrderNoGenerator.genOrderNo(record.getOrderType(), BizConstant.ORDER_TYPE_PAYMENT, BizConstant.BIZ_TYPE_CLINIC);
			// 把订单数据弄好，放到缓存
			record.setOrderNo(orderNo);
			record.setOrderNoHashVal(Math.abs(orderNo.hashCode()));

			record.setUpdateTime(currentTime);
			record.setCreateTime(currentTime);
			record.setAppUrl(getAppUrl(request));
			record.setTradeMode(tradeMode);
			record.setPlatformMode(platformMode);

			//生成订单、缓存订单信息
			clinicService.saveRecordInfo(record);

			//构建支付信息
			pay = clinicService.buildPayInfo(record);
			logger.info("支付信息：" + JSON.toJSONString(pay));
		}

		// 返回数据
		if (pay != null) {
			resultMap.put(BizConstant.TRADE_PAY_KEY, pay);
			resultMap.put(BizConstant.TRADE_MODE, tradeMode);
			resultMap.put("tradeUrl", TradeCommonHoder.getTradeUrl());

			return new RespBody(Status.OK, resultMap);
		} else {
			return new RespBody(Status.OK, "");
		}
	}

	/**
	 * 订单信息确认页面
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/clinicOrderInfo")
	public ModelAndView clinicOrderInfo(String orderNo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/clinic/confirmOrderInfo");
		// 从缓存中拿回订单
		ClinicRecord record = clinicService.findRecordByOrderNo(orderNo);
		// 规则信息
		RuleClinic rule = ruleConfigManager.getRuleClinicByHospitalCode(record.getHospitalCode());
		// 将订单放到页面中
		modelAndView.addObject("record", record);
		modelAndView.addObject("rule", rule);

		return modelAndView;
	}

	/**
	 * 订单信息确认页面
	 * 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/clinicOrderInfoByJsonp")
	@ResponseBody
	public String clinicOrderInfo(String orderNo, String openId, String callback) {
		StringBuilder sb = new StringBuilder();

		ClinicRecord record = clinicService.findRecordByOrderNo(orderNo);
		sb.append("<div id='guoHao'><div class='box-list'><ul class='yx-list'>");
		sb.append("<li><div class='label'>医院名称</div><div class='values color666'>" + record.getHospitalName() + "</div></li>");
		sb.append("<li><div class='label'>业务名称</div><div class='values color666'>门诊缴费</div></li>");
		sb.append("<li><div class='label'>患者姓名</div><div class='values color666'>" + record.getEncryptedPatientName() + "</div></li>");
		sb.append("<li><div class='label' style='width: 3em'>卡号</div><div class='values color666'>" + record.getCardNo() + "</div></li>");

		if ("自费".equals(record.getMedicareType())) {
			sb.append("<li><div class='label'>付款金额</div><div class='values color666'><span class='price'>"
					+ ( Integer.valueOf(record.getPayFee()) / 100 ) + "</span> 元</div></li>");
		} else {
			sb.append("<li><div class='label'>总金额</div><div class='values color666'><span class='price'>"
					+ ( Integer.valueOf(record.getTotalFee()) / 100 ) + "</span> 元</div></li>");
			sb.append("<li><div class='label'>实付金额</div><div class='values color666'><span class='price'>"
					+ ( Integer.valueOf(record.getPayFee()) / 100 ) + "</span> 元</div></li>");
		}
		sb.append("</ul></div></div>");

		// 规则信息
		RuleClinic rule = ruleConfigManager.getRuleClinicByHospitalCode(record.getHospitalCode());
		if ("自费".equals(record.getMedicareType())) {
			sb.append("<div class='section box-tips icontips'><i class='iconfont'>&#xe60d;</i>" + rule.getNotSupportHealthPaymentsTip()
					+ "</div>");
		} else {
			sb.append("<div class='section box-tips icontips'><i class='iconfont'>&#xe60d;</i>" + rule.getSupportHealthPaymentsTip()
					+ "</div>");
		}

		RespBody respBody = new RespBody(Status.OK, sb.toString());

		return callback + "(" + JSON.toJSONString(respBody) + ")";
	}

	/**
	 * 支付成功后跳转页面
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/payMentSuccess")
	public ModelAndView payMentSuccess(HttpServletRequest request, TradeParamsVo vo) {

		if (StringUtils.isBlank(vo.getOrderNo())) {
			vo.setOrderNo(request.getParameter("out_trade_no"));
		}

		ClinicRecord record = clinicService.findRecordByOrderNo(vo.getOrderNo());

		if (record.getPlatformMode() == null) {
			record.setPlatformMode(BizConstant.MODE_TYPE_WECHAT_VAL);
			logger.info("this clinic record orderNo[{}] has something warn.. the payMode is null.", vo.getOrderNo());
		}

		vo.setTradeMode(record.getTradeMode() + "");
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/clinic/payMentSuccess", "payMentSuccess", true);
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	/**
	 * 支付后确认his订单
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clinicOrderConfirm", method = RequestMethod.POST)
	public RespBody clinicOrderConfirm(HttpServletRequest request, TradeParamsVo vo) {
		int count = 0;
		ClinicRecord record = null;
		boolean isContinue = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		int step = 1000;
		do {
			// 睡眠1秒 以保证回调能及时写入缓存
			try {
				Thread.sleep(step);
				// logger.info("查询订单号: {}, openId: {}", new Object[] {
				// vo.getOrderNo(), vo.getOpenId() });
				record = clinicService.findRecordByOrderNo(vo.getOrderNo());

				// 等待回调处理完。
				if (record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == BizConstant.IS_HAD_CALLBACK_YES) {
					isContinue = false;
					break;
				}
				count++;
				if (count > 15) {
					break;
				} else if (count > 2) {
					step = 2000;
				} else if (count > 4) {
					step = 3000;
				} else if (count > 6) {
					step = 4000;
				} else if (count > 8) {
					step = 6000;
				} else if (count > 10) {
					step = 9000;
				} else if (count > 12) {
					step = 15000;
				} else if (count > 14) {
					step = 30000;
				}

			} catch (Exception e) {
			}
		} while (isContinue);

		logger.info("hisPayConfirm payStatus : {} , record.getIsHadCallBack:{}",
				new Object[] { record.getPayStatus(), record.getIsHadCallBack() });

		if (record.getIsHadCallBack() == null || record.getIsHadCallBack().intValue() == BizConstant.IS_HAD_CALLBACK_NO
				|| record.getClinicStatus() == ClinicConstant.STATE_EXCEPTION_PAY) {
			record.setClinicStatus(ClinicConstant.STATE_EXCEPTION_PAY);
			record.setPayStatus(OrderConstant.STATE_PAYMENTING);
			// 之前漏掉了更新时间(2015-11-10)
			record.setUpdateTime(System.currentTimeMillis());
			record.setIsHadCallBack(BizConstant.IS_HAD_CALLBACK_YES);
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
			resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
			resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "网络异常,系统后台自动处理中,请稍后查看已缴费记录的处理结果。");
			logger.info("orderNo[]无回调/第三方支付异常...", record.getOrderNo());
			clinicService.updateOrderPayInfo(record);
		} else {
			if (record.getClinicStatus().intValue() == ClinicConstant.STATE_PAY_SUCCESS) {
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "门诊缴费成功,<span id='timeCount'>3</span> 秒后系统将自动跳转到缴费详情页。");
			} else if (record.getClinicStatus().intValue() == ClinicConstant.STATE_EXCEPTION_HIS
					|| record.getClinicStatus().intValue() == ClinicConstant.STATE_EXCEPTION_NEED_HOSPITAL_HANDLE
					|| record.getClinicStatus().intValue() == ClinicConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE
					|| record.getClinicStatus().intValue() == ClinicConstant.STATE_EXCEPTION_REFUND) {
				// 异常的提示信息
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, "网络异常,系统后台自动处理中,请稍后查看已缴费记录的处理结果。");
				logger.info("orderNo[]His处理异常，转入我们的异常处理流程...", record.getOrderNo());
			} else {
				// 失败的提示信息
				resMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
				// 获取规则配置的失败提示语
				/*
				 * RulePayment rulePayment = ruleConfigManager.getRulePaymentByHospitalCode(record.getHospitalCode()); String failTips =
				 * rulePayment.getOutpatientPaymentFailedTip();
				 */

				RuleClinic ruleClinic = ruleConfigManager.getRuleClinicByHospitalCode(record.getHospitalCode());
				String failTips = ruleClinic.getOutpatientPaymentFailedTip();

				if (StringUtils.isBlank(failTips)) {
					failTips = "门诊缴费失败,系统后台自动退费中";
				}
				resMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, failTips);
			}
		}

		return new RespBody(Status.OK, resMap);
	}

}
