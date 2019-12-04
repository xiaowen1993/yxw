package com.yxw.easyhealth.biz.clinic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeDetailService;
import com.yxw.easyhealth.biz.datastorage.clinic.thread.SaveDataPayFeeExRunnable;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.clinicpay.Detail;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetail;
import com.yxw.mobileapp.biz.clinic.service.ClinicPartRefundService;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;

@Controller
@RequestMapping("app/clinic/paid/detail")
public class ClinicPaidDetailController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(ClinicPaidDetailController.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);

	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	private ClinicPartRefundService partRefundService = SpringContextHolder.getBean(ClinicPartRefundService.class);

	private DataPayFeeDetailService dataPayFeeDetailService = SpringContextHolder.getBean(DataPayFeeDetailService.class);

	/**
	 * 已缴费明细
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView toPaidDetail(ClinicPayVo vo, HttpServletRequest request) {
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		ClinicRecord record = clinicService.findRecordByOrderNo(vo.getOrderNo());
		vo = record.convertToClinicPayVo();

		// 测试
		// vo.setReceiptNum("abcdefghijk123456789");

		ModelAndView modelAndView = null;

		try {
			RuleQuery rule = ruleConfigManager.getRuleQueryByHospitalCode(vo.getHospitalCode());
			modelAndView = new ModelAndView("/easyhealth/biz/clinic/paidDetail");
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
			modelAndView.addObject("rule", rule);

			Integer refundFee = 0;
			if (vo.getClinicStatus().equals(ClinicConstant.STATE_PART_REFUND)) {
				logger.info("该订单部分退费.orderNo: {}", vo.getOrderNo());
				refundFee = partRefundService.getRefundedFee(vo.getOrderNo(), vo.getHospitalCode());
			}
			modelAndView.addObject("refundFee", refundFee);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("待缴费明细获取异常, 错误信息：{}, 原因： {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return modelAndView;
	}

	/**
	 * 异步查询已缴费明细
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	public Object getDatas(ClinicPayVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		DataPayFee dataPayFee = new DataPayFee();
		BeanUtils.copyProperties(vo, dataPayFee);

		List<DataPayFeeDetail> list = dataPayFeeDetailService.findPayFeeDetails(dataPayFee);

		if (CollectionUtils.isEmpty(list)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("hospitalCode", vo.getHospitalCode());
			params.put("branchHospitalCode", vo.getBranchHospitalCode());
			params.put("hisOrdNum", vo.getHisOrdNum());
			params.put("receiptNum", vo.getReceiptNum());
			params.put("mzFeeId", vo.getMzFeeId());

			logger.info("getPaidDetail.hospitalCode:{} ,branchHospitalCode:{}", vo.getHospitalCode(), vo.getBranchHospitalCode());

			List<PayFeeDetail> payFeeDetails = clinicBizManager.getPayDetail(params);
			resultMap.put("list", payFeeDetails);

			Thread dataStorageThead = new Thread(new SaveDataPayFeeExRunnable(payFeeDetails, vo));
			dataStorageThead.start();
		} else {

			List<Detail> details = Lists.transform(list, new Function<DataPayFeeDetail, Detail>() {
				@Override
				public Detail apply(DataPayFeeDetail dataPayFeeDetail) {
					// TODO Auto-generated method stub
					Detail detail = new Detail();
					BeanUtils.copyProperties(dataPayFeeDetail, detail);

					return detail;
				}
			});

			List<PayFeeDetail> payFeeDetails = new ArrayList<PayFeeDetail>(1);
			PayFeeDetail payFeeDetail = new PayFeeDetail();
			payFeeDetail.setDetails(details);
			payFeeDetail.setMzFeeId(list.get(0).getMzFeeId());
			payFeeDetails.add(payFeeDetail);

			resultMap.put("list", payFeeDetails);
		}

		return new RespBody(Status.OK, resultMap);
	}
}
