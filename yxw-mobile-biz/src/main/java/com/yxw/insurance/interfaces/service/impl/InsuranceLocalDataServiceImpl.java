package com.yxw.insurance.interfaces.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.yxw.commons.constants.biz.InsuranceLocalDataMethodConstant;
import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.interfaces.dto.Request;
import com.yxw.insurance.interfaces.dto.Response;
import com.yxw.insurance.interfaces.service.InsuranceLocalDataService;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;
import com.yxw.mobileapp.biz.insure.service.InsureDataService;

/**
 * 获取本地数据门诊数据提供给商保数据调用服务
 * @author Administrator
 *
 */
public class InsuranceLocalDataServiceImpl implements InsuranceLocalDataService {

	private static Logger logger = LoggerFactory.getLogger(InsuranceLocalDataServiceImpl.class);

	private InsureDataService insureDataService = SpringContextHolder.getBean(InsureDataService.class);

	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	@Override
	public Response localService(Request request) {

		logger.info("请求参数：{}", JSONObject.toJSONString(request));

		if (StringUtils.isBlank(request.getMethodName())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "methodCode cannot be null");
		}

		if (request.getParams() == null) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "params cannot be null");
		}

		boolean responseType = true;
		if (StringUtils.isNotBlank(request.getResponseType()) && request.getResponseType().equals("1")) {
			responseType = false;
		}

		logger.info("methodCode: {} ,responseType:{} , params(): {}.", new Object[] { request.getMethodName(),
				responseType ? "xml" : "json",
				JSONObject.toJSONString(request.getParams()) });

		Response response = new Response();

		if (request.getMethodName().trim().equals(InsuranceLocalDataMethodConstant.PAID_MZ_DETAIL_QUERY)) {//缴费记录详情查询

			response = paidMZDetailQuery((PaidMZDetailCommParams) request.getParams(), responseType);

		} else if (request.getMethodName().trim().equals(InsuranceLocalDataMethodConstant.PRESCRIPTION_DETAIL_QUERY)) {// 处方明细

			response = prescriptionDetailQuery((PaidMZDetailCommParams) request.getParams(), responseType);

		} else if (request.getMethodName().trim().equals(InsuranceLocalDataMethodConstant.MEDICAL_SETTLEMENT_QUERY)) {// 结算信息

			response = medicalSettlementQuery((PaidMZDetailCommParams) request.getParams(), responseType);

		} else if (request.getMethodName().trim().equals(InsuranceLocalDataMethodConstant.PAID_MZ_LIST_QUERY)) {//缴费记录列表

			response = paidMZListQuery((PaidMZDetailCommParams) request.getParams(), responseType);

		} else if (request.getMethodName().trim().equals(InsuranceLocalDataMethodConstant.CLINICRECORD_UPDATE)) {//更新理赔状态
			response = clinicRecordUpdate((PaidMZDetailReuslt) request.getParams(), responseType);
		} else {
			response = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "methodCode does not exist");
		}

		return response;
	}

	/**
	 * 查询缴费记录
	 * @param payDetailParams 请求参数
	 * @param responseType 返回格式，xml或者json
	 * @return
	 */
	public Response paidMZDetailQuery(PaidMZDetailCommParams paidMZDetailCommParams, boolean responseType) {
		try {
			logger.info("paidDetailParams:{}.", com.alibaba.fastjson.JSON.toJSONString(paidMZDetailCommParams));
			// 检查参数是否为空
			Response result = checkPayDetailParams(paidMZDetailCommParams);
			if (result != null) {
				return result;
			}

			//查询缴费记录

			PaidMZDetailReuslt paidMZDetailReuslt = insureDataService.getPaidMZDetail(paidMZDetailCommParams);

			if (paidMZDetailReuslt != null) {
				//判断返回格式是XML或者json
				if (responseType) {
					XStream stream = new XStream();
					stream.alias("paidMZDetail", PaidMZDetailReuslt.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(paidMZDetailReuslt));
				} else {
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", com.alibaba.fastjson.JSON.toJSONString(paidMZDetailReuslt));
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_ERROR, "data not found");
			}

			logger.info("paidDetailQuery.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(),
					result.getResultMessage(), result.getResult() == null ? "result is null" : "result is not null");
			return result;
		} catch (Exception e) {
			logger.error("payDetailQuery方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	/**
	 * 查询处方记录
	 * @param payDetailParams 请求参数
	 * @param responseType 返回格式，xml或者json
	 * @return
	 */
	public Response prescriptionDetailQuery(PaidMZDetailCommParams paidMZDetailCommParams, boolean responseType) {
		try {
			logger.info("PaidMZDetailCommParams:{}.", com.alibaba.fastjson.JSON.toJSONString(paidMZDetailCommParams));
			// 检查参数是否为空
			Response result = checkPayDetailParams(paidMZDetailCommParams);
			if (result != null) {
				return result;
			}

			//查询处方记录

			List<PrescriptionDetailReuslt> prescriptionDetailReuslts = insureDataService.getPrescriptionDetail(paidMZDetailCommParams);

			if (!CollectionUtils.isEmpty(prescriptionDetailReuslts)) {
				//判断返回格式是XML或者json
				if (responseType) {
					XStream stream = new XStream();
					stream.alias("prescriptionDetail", PrescriptionDetailReuslt.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(prescriptionDetailReuslts));
				} else {
					result =
							new Response(OutsideConstant.OUTSIDE_SUCCESS, "",
									com.alibaba.fastjson.JSON.toJSONString(prescriptionDetailReuslts));
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "data not found");
			}

			logger.info("OrderQuery.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(), result.getResultMessage(),
					result.getResult() == null ? "result is null" : "result is not null");
			return result;
		} catch (Exception e) {
			logger.error("prescriptionDetailQuery方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	public Response medicalSettlementQuery(PaidMZDetailCommParams paidMZDetailCommParams, boolean responseType) {
		try {
			logger.info("PaidMZDetailCommParams:{}.", com.alibaba.fastjson.JSON.toJSONString(paidMZDetailCommParams));
			Response result = checkPayDetailParams(paidMZDetailCommParams);
			if (result != null) {
				return result;
			}

			List<MedicalSettlementReuslt> medicalSettlementReuslts = insureDataService.getMedicalSettlement(paidMZDetailCommParams);

			if (!CollectionUtils.isEmpty(medicalSettlementReuslts)) {
				//判断返回格式是XML或者json
				if (responseType) {
					XStream stream = new XStream();
					stream.alias("medicalSettlement", MedicalSettlementReuslt.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(medicalSettlementReuslts));
				} else {
					result =
							new Response(OutsideConstant.OUTSIDE_SUCCESS, "",
									com.alibaba.fastjson.JSON.toJSONString(medicalSettlementReuslts));
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "data not found");
			}

			logger.info("medicalSettlementQuery.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(),
					result.getResultMessage(), result.getResult() == null ? "result is null" : "result is not null");
			return result;
		} catch (Exception e) {
			logger.error("medicalSettlementQuery方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	/**
	 * 缴费列表查询
	 * @param paidMZDetailCommParams
	 * @param responseType
	 * @return
	 */
	public Response paidMZListQuery(PaidMZDetailCommParams paidMZDetailCommParams, boolean responseType) {
		try {
			logger.info("paidDetailParams:{}.", com.alibaba.fastjson.JSON.toJSONString(paidMZDetailCommParams));
			// 检查参数是否为空
			if (StringUtils.isBlank(paidMZDetailCommParams.getOpenId())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "openId cannot be null");
			}
			//			if (StringUtils.isBlank(paidMZDetailCommParams.getHospitalCode())) {
			//				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "hospitalCode cannot be null");
			//			}
			//			if (StringUtils.isBlank(paidMZDetailCommParams.getCardNo())) {
			//				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "cardNo cannot be null");
			//			}
			Response result = null;
			//查询缴费记录
			ClinicQueryVo vo = new ClinicQueryVo();
			vo.setOpenId(paidMZDetailCommParams.getOpenId());
			vo.setCardNo(paidMZDetailCommParams.getCardNo());
			vo.setHospitalCode(paidMZDetailCommParams.getHospitalCode());
			//			vo.setHashTableName("BIZ_CLINIC_RECORD_1");
			List<ClinicRecord> list = clinicService.findPaidRecords(vo);
			if (list != null && list.size() > 0) {
				//判断返回格式是XML或者json
				if (responseType) {
					XStream stream = new XStream();
					stream.alias("paidMZList", PaidMZDetailReuslt.class);
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(list));
				} else {
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", com.alibaba.fastjson.JSON.toJSONString(list));
				}
			} else {
				result = new Response(OutsideConstant.OUTSIDE_ERROR, "data not found");
			}

			logger.info("paidMZListQuery.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(),
					result.getResultMessage(), result.getResult() == null ? "result is null" : "result is not null");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("paidMZListQuery方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	/**
	 * 更新理赔状态
	 * @param params
	 * @param responseType
	 * @return
	 */
	public Response clinicRecordUpdate(PaidMZDetailReuslt params, boolean responseType) {
		try {
			logger.info("clinicRecordUpdateParams:{}.", com.alibaba.fastjson.JSON.toJSONString(params));
			if (StringUtils.isBlank(params.getOpenId())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "openId cannot be null");
			}
			if (StringUtils.isBlank(params.getOrderNo())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "orderNo cannot be null");
			}
			if (params.getIsClaim() == null) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "IsClaim cannot be null");
			}
			Response result = null;
			ClinicRecord record = new ClinicRecord();
			record.setOpenId(params.getOpenId());
			record.setOrderNo(params.getOrderNo());
			record.setIsClaim(params.getIsClaim());
			clinicService.updateExceptionRecord(record);
			result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "更新理赔状态成功");
			logger.info("clinicRecordUpdate.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(),
					result.getResultMessage(), result.getResult() == null ? "result is null" : "result is not null");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("clinicRecordUpdate方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	/**
	 * 检查参数
	 * 
	 * @param op
	 * @return
	 */
	private Response checkPayDetailParams(PaidMZDetailCommParams op) {

		if (StringUtils.isBlank(op.getAppCode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appCode cannot be null");
		}

		if (StringUtils.isBlank(op.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}

		if (StringUtils.isBlank(op.getHospitalCode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "hospitalCode cannot be null");
		}

		if (StringUtils.isBlank(op.getMzFeeId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "mzFeeId cannot be null");
		}

		if (StringUtils.isBlank(op.getOrderNo())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "orderNo cannot be null");
		}
		if (StringUtils.isBlank(op.getOpenId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "openId cannot be null");
		}

		return null;
	}

}
