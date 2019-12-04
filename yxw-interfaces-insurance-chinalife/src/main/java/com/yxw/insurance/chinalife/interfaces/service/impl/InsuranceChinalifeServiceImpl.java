package com.yxw.insurance.chinalife.interfaces.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.constants.CommonConstant;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeDetailInfo;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeItem;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalRegistration;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalSettlement;
import com.yxw.insurance.chinalife.interfaces.entity.request.SocialInsuranceAmount;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalFeeDetailInfoResponse;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalRegistrationResponse;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalSettlementResponse;
import com.yxw.insurance.chinalife.interfaces.service.InsuranceChinalifeService;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalFeeDetailInfoRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalFeeItemRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalRegistrationRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalSettlementRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.SocialInsuranceAmountRequest;
import com.yxw.insurance.chinalife.interfaces.vo.response.InsuranceResponse;

public class InsuranceChinalifeServiceImpl implements InsuranceChinalifeService {

	private Logger logger = LoggerFactory.getLogger(InsuranceChinalifeServiceImpl.class);

	/**
	 * 就医登记
	 */
	@Override
	public InsuranceResponse hospitalRegistration(HospitalRegistrationRequest hospitalRegistrationRequest) {
		HospitalRegistration hospitalRegistration = new HospitalRegistration();
		BeanUtils.copyProperties(hospitalRegistrationRequest, hospitalRegistration);

		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("HospitalRegistration", hospitalRegistration);

		root.put("head", hospitalRegistration.getHead());
		root.put("body", body);
		requestJson.put("root", root);

		InsuranceResponse response = new InsuranceResponse();
		response.setResultCode(CommonConstant.ERROR);
		try {
			String responseJsonString = WebServiceUtil.invoke(requestJson.toJSONString());
			logger.info("国寿就医登记返回结果：" + responseJsonString);
			if (StringUtils.isNotBlank(responseJsonString)) {
				JSONObject responseJson = JSONObject.parseObject(responseJsonString);
				HospitalRegistrationResponse hospitalRegistrationResponse =
						JSONObject.toJavaObject((JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalRegistrationResponse"),
								HospitalRegistrationResponse.class);
				response.addSuccess(hospitalRegistrationResponse.getBusinessProcessStatus().getBusinessMessage());
				if (CommonConstant.MESSAGE_STATUS_CODE_SUCCESS.equals(hospitalRegistrationResponse.getMessageStatusCode())) {
					if (CommonConstant.BUSINESS_STATUS_SUCCESS.equals(hospitalRegistrationResponse.getBusinessProcessStatus()
							.getBusinessStatus())) {
						response.setResultCode(CommonConstant.SUCCESS);
					}
				}
			}
		} catch (DocumentException e) {
			response.addError(e.getMessage());
			logger.error("调取国寿就医登记接口出现异常：", e);
		}
		return response;
	}

	@Override
	public InsuranceResponse hospitalFeeDetailInfo(HospitalFeeDetailInfoRequest hospitalFeeDetailInfoRequest) {
		HospitalFeeDetailInfo hospitalFeeDetailInfo = new HospitalFeeDetailInfo();
		BeanUtils.copyProperties(hospitalFeeDetailInfoRequest, hospitalFeeDetailInfo);

		List<HospitalFeeItem> hospitalFeeItems = new ArrayList<HospitalFeeItem>();
		for (HospitalFeeItemRequest hospitalFeeItemRequest : hospitalFeeDetailInfoRequest.getHospitalFeeItems()) {
			HospitalFeeItem hospitalFeeItem = new HospitalFeeItem();
			BeanUtils.copyProperties(hospitalFeeItemRequest, hospitalFeeItem);
			hospitalFeeItems.add(hospitalFeeItem);
		}
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);

		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);

		InsuranceResponse response = new InsuranceResponse();
		response.setResultCode(CommonConstant.ERROR);
		try {
			String responseJsonString = WebServiceUtil.invoke(requestJson.toJSONString());
			if (StringUtils.isNotBlank(responseJsonString)) {
				JSONObject responseJson = JSONObject.parseObject(responseJsonString);
				HospitalFeeDetailInfoResponse hospitalFeeDetailInfoResponse =
						JSONObject.toJavaObject((JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalFeeDetailInfoResponse"),
								HospitalFeeDetailInfoResponse.class);

				logger.debug(JSONObject.toJSONString(hospitalFeeDetailInfoResponse, true));
				logger.info(responseJson.toJSONString());
				response.addSuccess(hospitalFeeDetailInfoResponse.getBusinessProcessStatus().getBusinessMessage());
				if (CommonConstant.MESSAGE_STATUS_CODE_SUCCESS.equals(hospitalFeeDetailInfoResponse.getMessageStatusCode())) {
					if (CommonConstant.BUSINESS_STATUS_SUCCESS.equals(hospitalFeeDetailInfoResponse.getBusinessProcessStatus()
							.getBusinessStatus())) {
						response.setResultCode(CommonConstant.SUCCESS);
					}
				}
			}
		} catch (DocumentException e) {
			response.addError(e.getMessage());
			logger.error("调取国寿就医处方明细上传交易接口出现异常：", e);
		}
		return response;
	}

	@Override
	public InsuranceResponse hospitalSettlement(HospitalSettlementRequest hospitalSettlementRequest) {
		HospitalSettlement hospitalSettlement = new HospitalSettlement();
		BeanUtils.copyProperties(hospitalSettlementRequest, hospitalSettlement);

		List<SocialInsuranceAmount> socialInsuranceAmounts = new ArrayList<SocialInsuranceAmount>();
		for (SocialInsuranceAmountRequest socialInsuranceAmountRequest : hospitalSettlementRequest.getSocialInsuranceAmounts()) {
			SocialInsuranceAmount socialInsuranceAmount = new SocialInsuranceAmount();
			BeanUtils.copyProperties(socialInsuranceAmountRequest, socialInsuranceAmount);
			socialInsuranceAmounts.add(socialInsuranceAmount);
		}
		hospitalSettlement.setSocialInsuranceAmounts(socialInsuranceAmounts);

		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("HospitalSettlement", hospitalSettlement);

		root.put("head", hospitalSettlement.getHead());
		root.put("body", body);
		requestJson.put("root", root);

		InsuranceResponse response = new InsuranceResponse();
		response.setResultCode(CommonConstant.ERROR);
		try {
			String responseJsonString = WebServiceUtil.invoke(requestJson.toJSONString());
			if (StringUtils.isNotBlank(responseJsonString)) {
				JSONObject responseJson = JSONObject.parseObject(responseJsonString);
				HospitalSettlementResponse hospitalSettlementResponse =
						JSONObject.toJavaObject((JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalSettlementResponse"),
								HospitalSettlementResponse.class);

				logger.debug(JSONObject.toJSONString(hospitalSettlementResponse, true));
				logger.info(responseJson.toJSONString());
				response.addSuccess(hospitalSettlementResponse.getInsuranceCompensation().getBusinessMessage());
				if (CommonConstant.MESSAGE_STATUS_CODE_SUCCESS.equals(hospitalSettlementResponse.getMessageStatusCode())) {
					if (CommonConstant.BUSINESS_STATUS_SUCCESS.equals(hospitalSettlementResponse.getInsuranceCompensation()
							.getBusinessStatus())) {
						response.setResultCode(CommonConstant.SUCCESS);
					}
				}
			}
		} catch (DocumentException e) {
			response.addError(e.getMessage());
			logger.error("调取国寿就医结算接口出现异常：", e);
		}
		return response;
	}

}
