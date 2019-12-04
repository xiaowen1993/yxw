package com.yxw.insurance.biz.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.constant.ClaimStatusEnum;
import com.yxw.insurance.biz.entity.ClaimOrder;
import com.yxw.insurance.biz.entity.MzFeeData;
import com.yxw.insurance.biz.service.ClaimBlankService;
import com.yxw.insurance.biz.service.ClaimOrderService;
import com.yxw.insurance.biz.service.ClinicPartRefundService;
import com.yxw.insurance.biz.service.ClinicService;
import com.yxw.insurance.biz.service.InsuranceBusinessService;
import com.yxw.insurance.biz.service.InsuranceService;
import com.yxw.insurance.biz.service.MedicalCardService;
import com.yxw.insurance.biz.service.MzFeeDataService;
import com.yxw.insurance.init.InitDataServlet;
import com.yxw.utils.StringUtils;

/**
 * 理赔跳转
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api")
public class InsuranceController {

	private static Logger logger = LoggerFactory.getLogger(InsuranceController.class);

	//	RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	InsuranceService insuranceService = SpringContextHolder.getBean(InsuranceService.class);

	ClaimOrderService claimOrderService = SpringContextHolder.getBean(ClaimOrderService.class);

	ClaimBlankService claimBlankService = SpringContextHolder.getBean(ClaimBlankService.class);

	MzFeeDataService mzFeeDataService = SpringContextHolder.getBean(MzFeeDataService.class);

	InsuranceBusinessService insuranceBusinessService = SpringContextHolder.getBean(InsuranceBusinessService.class);

	ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);

	ClinicPartRefundService partRefundService = SpringContextHolder.getBean(ClinicPartRefundService.class);

	ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	@RequestMapping(value = "/matchMainDiagnosisName")
	@ResponseBody
	public String matchMainDiagnosisName(final String keyword) {

		List<Object> ICDs = InitDataServlet.ICDConfigMapVals;

		Collection<Object> result = Collections2.filter(ICDs, new Predicate<Object>() {
			@Override
			public boolean apply(Object obj) {
				return obj.toString().trim().contains(keyword);
			}
		});

		Ordering<Object> byLengthOrdering = new Ordering<Object>() {
			public int compare(Object left, Object right) {
				return Ints.compare(left.toString().trim().length(), right.toString().trim().length());
			}
		};

		List<Object> ret = Lists.newArrayList(result);

		Collections.sort(ret, byLengthOrdering);

		return JSONObject.toJSONString(ret);
	}

	/**
	 * 选择理赔类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/claimType")
	public ModelAndView claimType(HttpServletRequest request, PaidMZDetailCommParams params) {
		ModelAndView view = new ModelAndView("/insurance/claimType");

		view.addObject("payDetail", insuranceService.getMZPayFeeDetail(params));

		return view;
	}

	/**
	 * 申请理赔，与国寿进行碰对，碰对成功跳转填写账户信息，失败则跳到理赔详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addClaim")
	public ModelAndView addClaim(HttpServletRequest request, PaidMZDetailReuslt params) {
		ModelAndView view = new ModelAndView();

		//申请理赔，生成订单
		ClaimOrder order = insuranceBusinessService.applyClaim(params);
		if (!StringUtils.isEmpty(order.getFlowNumber())) {
			view.addObject("blanks", claimBlankService.findAllBlank());
			view.setViewName("/insurance/addClaim");
		} else {
			view.setViewName("/insurance/claimDetail");
		}
		view.addObject("order", order);
		return view;
	}

	/**
	 * 未填写账户信息，跳转到账户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/account")
	public ModelAndView account(String id) {
		ModelAndView view = new ModelAndView();
		ClaimOrder order = claimOrderService.findClaimOrder(id);
		view.addObject("order", order);
		view.addObject("blanks", claimBlankService.findAllBlank());
		view.setViewName("/insurance/addClaim");
		return view;
	}

	/**
	 * 填写账户信息后跳至详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addClaimOrder")
	public ModelAndView addClaimOrder(HttpServletRequest request, ClaimOrder order) {
		ModelAndView view = new ModelAndView("/insurance/claimDetail");
		ClaimOrder o = claimOrderService.findClaimOrder(order.getId());
		if (o != null) {
			o.setAddress(order.getAddress());
			o.setPatientName(order.getPatientName());
			o.setBankCode(order.getBankCode());
			o.setBankName(order.getBankName());
			o.setPatientCardNo(order.getPatientCardNo());

			//上传就医药方明细
			boolean uploadFlag = insuranceBusinessService.uploadPrescriptionDetail(o);
			//就医结算交易
			boolean flag = insuranceBusinessService.settlementTransaction(o);
			if (uploadFlag && flag) {
				o.setClaimStatus(ClaimStatusEnum.CLAIM_SUCCESS.getIndex());// 申请成功
				claimOrderService.udpateClaimOrder(o);
			} else {
				o.setClaimStatus(ClaimStatusEnum.CLAIM_ERROR.getIndex());//申请不成功
				claimOrderService.udpateClaimOrder(o);
			}

			view.addObject("order", o);
		}
		return view;
	}

	/**
	 * 理赔详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/claimDetail")
	public ModelAndView claimDetail(String id) {
		ModelAndView view = new ModelAndView("/insurance/claimDetail");
		ClaimOrder order = claimOrderService.findClaimOrder(id);
		view.addObject("order", order);
		return view;
	}

	/**
	 * 理赔列表
	 * @param openId
	 * @param appCode
	 * @param areaCode
	 * @return
	 */
	@RequestMapping(value = "/claimIndex")
	public ModelAndView claimIndex(String openId, String appCode, String areaCode) {
		ModelAndView view = new ModelAndView("/insurance/claimIndex");
		view.addObject("orders", claimOrderService.findClaimOrderList(openId));
		view.addObject("appCode", appCode);
		view.addObject("areaCode", areaCode);
		view.addObject("openId", openId);
		return view;
	}

	/**
	 * 缴费列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userOrderList")
	public ModelAndView userOrderList() {
		ModelAndView view = new ModelAndView("/insurance/userOrderList");
		return view;
	}

	/**
	 * 选择就诊人
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectUser")
	public ModelAndView selectUser(String openId, String appCode, String areaCode) {
		ModelAndView view = new ModelAndView("/insurance/selectUser");
		view.addObject("openId", openId);

		List<HospitalInfoByEasyHealthVo> hospitalInfos = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appCode);
		params.add(BizConstant.OPTION_CLINIC_PAY);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);

		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitalInfos = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		if (hospitalInfos != null && hospitalInfos.size() > 0) {
			for (int i = hospitalInfos.size() - 1; i >= 0; i--) {
				HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitalInfos.get(i);
				params.clear();
				params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
				List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
				if (!CollectionUtils.isEmpty(result)) {
					CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

					if (codeAndInterfaceVo.getStatus() == 0) {
						hospitalInfos.remove(i);
					}
				}
			}
		}

		view.addObject("hospitals", hospitalInfos);
		view.addObject("cards", medicalCardService.findAllMedicalCards(openId));
		return view;
	}

	/**
	 * 缴费详情
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toPaidDetail")
	public ModelAndView toPaidDetail(ClinicPayVo vo, HttpServletRequest request) {
		ClinicRecord record = clinicService.findRecordByOrderNo(vo.getOrderNo());
		if (record != null) {
			vo = record.convertToClinicPayVo();
		}

		ModelAndView modelAndView = null;
		try {
			RuleQuery rule = insuranceBusinessService.getRuleQueryByHospitalCode(vo.getHospitalCode());
			modelAndView = new ModelAndView("/insurance/paidDetail");
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
	 * 获取申请详情中缴费信息（此方法暂时不用）
	 * 
	 * @param openId
	 * @param mzFeeId
	 * @return
	 */
	@RequestMapping(value = "/getMzFeeData")
	@ResponseBody
	public MzFeeData getMzFeeData(String openId, String mzFeeId) {
		return mzFeeDataService.findMzFeeData(openId, mzFeeId);
	}

}
