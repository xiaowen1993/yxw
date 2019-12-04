package com.yxw.insurance.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.InsuranceLocalDataMethodConstant;
import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.utils.PropertiesUtils;
import com.yxw.insurance.biz.constant.IdCardTypeEnum;
import com.yxw.insurance.biz.constant.MedicalFeeTypeEnum;
import com.yxw.insurance.biz.dto.inside.ApplyClaimParams;
import com.yxw.insurance.biz.dto.inside.ApplyMedicalSettlementParams;
import com.yxw.insurance.biz.dto.inside.ApplyPrescriptionParams;
import com.yxw.insurance.biz.service.InsuranceService;
import com.yxw.insurance.chinalife.interfaces.service.InsuranceChinalifeService;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalFeeDetailInfoRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalFeeItemRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalRegistrationRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.HospitalSettlementRequest;
import com.yxw.insurance.chinalife.interfaces.vo.request.SocialInsuranceAmountRequest;
import com.yxw.insurance.chinalife.interfaces.vo.response.InsuranceResponse;
import com.yxw.insurance.interfaces.dto.Request;
import com.yxw.insurance.interfaces.dto.Response;
import com.yxw.insurance.interfaces.service.InsuranceLocalDataService;
import com.yxw.utils.DateUtils;

@Service(value = "insuranceService")
public class InsuranceServiceImp implements InsuranceService {

	private static Logger logger = LoggerFactory.getLogger(InsuranceServiceImp.class);
	ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	InsuranceLocalDataService insuranceLocalDataService = SpringContextHolder.getBean(InsuranceLocalDataService.class);
	InsuranceChinalifeService insuranceService = SpringContextHolder.getBean(InsuranceChinalifeService.class);
	//	private final static String BelongInstituteAreaCode = "120000";//归属社保地区代码
	private final static String BelongInstituteAreaCode = "440100";//归属社保地区代码
	PropertiesUtils registration = new PropertiesUtils("/properties/registration.properties");
	PropertiesUtils prescription = new PropertiesUtils("/properties/prescription.properties");
	PropertiesUtils settlement = new PropertiesUtils("/properties/settlement.properties");

	/**
	 * 获取门诊缴费记录详情
	 */
	@Override
	public PaidMZDetailReuslt getMZPayFeeDetail(PaidMZDetailCommParams params) {
		try {
			Request request = new Request();
			request.setMethodName(InsuranceLocalDataMethodConstant.PAID_MZ_DETAIL_QUERY);
			request.setResponseType("1");

			Response response = insuranceLocalDataService.localService(request);
			logger.info("获取缴费记录返回结果：" + JSONObject.toJSONString(response));
			if (response.getResultCode().equals(OutsideConstant.OUTSIDE_SUCCESS)) {
				PaidMZDetailReuslt result = JSON.parseObject(response.getResult(), PaidMZDetailReuslt.class);
				result.setAppCode(params.getAppCode());
				result.setAppId(params.getAppId());
				result.setOpenId(params.getOpenId());
				result.setOrderNo(params.getOrderNo());
				result.setHospitalCode(params.getHospitalCode());
				return result;
			}

		} catch (Exception e) {
			logger.error("获取门诊缴费详情：", e);
			throw new SystemException("获取门诊缴费详情：", e);
		}

		return null;
	}

	@Override
	public List<PrescriptionDetailReuslt> getCFRecordDetail(PaidMZDetailCommParams params) {
		try {
			Request request = new Request();
			request.setMethodName(InsuranceLocalDataMethodConstant.PRESCRIPTION_DETAIL_QUERY);
			request.setParams(params);
			request.setResponseType("1");

			Response response = insuranceLocalDataService.localService(request);
			if (response.getResultCode().equals(OutsideConstant.OUTSIDE_SUCCESS)) {
				List<PrescriptionDetailReuslt> result = JSONObject.parseArray(response.getResult(), PrescriptionDetailReuslt.class);
				return result;
			}

		} catch (Exception e) {
			logger.error("获取门诊缴费明细：", e);
			throw new SystemException("获取门诊缴费明细：", e);
		}

		return null;
	}

	/**
	 * 就医结算交易
	 * @return
	 */
	@Override
	public List<MedicalSettlementReuslt> getMedicalSettlement(PaidMZDetailCommParams params) {

		Request request = new Request();
		request.setMethodName(InsuranceLocalDataMethodConstant.MEDICAL_SETTLEMENT_QUERY);
		request.setParams(params);
		request.setResponseType("1");

		Response response = insuranceLocalDataService.localService(request);
		if (response.getResultCode().equals(OutsideConstant.OUTSIDE_SUCCESS)) {
			List<MedicalSettlementReuslt> result = JSONObject.parseArray(response.getResult(), MedicalSettlementReuslt.class);
			return result;
		}
		return null;
	}

	/**
	 * 理赔申请
	 * 返回国寿流水号
	 */
	@Override
	public String applyClaim(ApplyClaimParams params) {
		//		params.setTime("2008-09-20 10:00:00");
		//		params.setPatName("程文刚");
		//		params.setPatIdNo("372323198108172115");
		//		params.setBirthDay("1981-08-17");
		//		params.setOpenId("123456789");
		//		params.setMzFeeId("123456789");
		//		params.setPatSex("1");
		try {
			HospitalRegistrationRequest hospitalRegistrationRequest = new HospitalRegistrationRequest();

			//归属社保地区代码
			hospitalRegistrationRequest.setBelongInstituteAreaCode(BelongInstituteAreaCode); //120000 440100
			//医疗机构代码
			hospitalRegistrationRequest.setMedicalInstituteCode("4401008000000044");
			//			hospitalRegistrationRequest.setMedicalInstituteCode(params.getHospitalCode());
			//个人唯一识别码
			hospitalRegistrationRequest.setPersonGUID(params.getOpenId());
			//社会保障号/农合号
			hospitalRegistrationRequest.setSocialInsuranceNumber(params.getOpenId());
			//姓名
			hospitalRegistrationRequest.setName(params.getPatName());
			//性别代码
			//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
			hospitalRegistrationRequest.setGenderCode(params.getPatSex() != null ? params.getPatSex() : "9");
			//证件类型
			hospitalRegistrationRequest.setCredentialType(IdCardTypeEnum.getIdCardType(params.getPatIdType()));
			//证件号码
			hospitalRegistrationRequest.setCredentialNum(params.getPatIdNo());
			//联系电话
			hospitalRegistrationRequest.setMobilephone(params.getPatMobile());
			//出生日期
			hospitalRegistrationRequest.setBirthday(params.getBirthDay());
			//通讯地址
			hospitalRegistrationRequest.setAddress(params.getPatAddress());
			//就医唯一识别码
			hospitalRegistrationRequest.setMedicalGUID(params.getMzFeeId());
			//结算方式，即时、非即时
			hospitalRegistrationRequest.setSettlementWay(registration.getProperty("SettlementWay"));
			//就医类型
			hospitalRegistrationRequest.setMedicalType(registration.getProperty("MedicalType"));
			//出险原因
			hospitalRegistrationRequest.setAccidentReason(params.getClaimType());
			//就诊时间
			hospitalRegistrationRequest.setMedicalDate(params.getTime());
			//就诊部门
			hospitalRegistrationRequest.setMedicalDepartment(params.getDeptName());
			//就诊医生
			hospitalRegistrationRequest.setMasterDoctor(params.getDoctorName());
			//社会医疗保险类别
			hospitalRegistrationRequest.setSocialMedicareType(registration.getProperty("SocialMedicareType"));
			//患者类型
			hospitalRegistrationRequest.setPatientType(registration.getProperty("PatientType"));
			//主诊断疾病
			//			hospitalRegistrationRequest.setMainDiagnosisCode(registration.getProperty("MainDiagnosisCode"));
			//			hospitalRegistrationRequest.setMainDiagnosisName(registration.getProperty("MainDiagnosisName"));
			hospitalRegistrationRequest.setMainDiagnosisCode(params.getMainDiagnosisCode());
			hospitalRegistrationRequest.setMainDiagnosisName(params.getMainDiagnosisName());
			//第二诊断疾病
			hospitalRegistrationRequest.setSecondaryDiagnosisCode(params.getMainDiagnosisCode());
			hospitalRegistrationRequest.setSecondaryDiagnosisName(params.getMainDiagnosisName());
			//			hospitalRegistrationRequest.setSecondaryDiagnosisCode(registration.getProperty("SecondaryDiagnosisCode"));
			//			hospitalRegistrationRequest.setSecondaryDiagnosisName(registration.getProperty("SecondaryDiagnosisName"));
			hospitalRegistrationRequest.setMedicalInstituteName("广州红十字会医院");//医疗机构名称
			//			hospitalRegistrationRequest.setPersonalIdentification("99");

			//调取国泰就医登记接口
			InsuranceResponse response = insuranceService.hospitalRegistration(hospitalRegistrationRequest);
			logger.info("国寿就医登记返回结果：" + JSONObject.toJSONString(response));

			if (response.getResultCode().equals("0")) {
				String orderNo = response.getResultMessage();
				return StringUtils.substringAfter(orderNo, "平台流水号：");
			}
		} catch (Exception e) {
			logger.error("调取国寿就医登记接口出错：", e);
			throw new SystemException("调取国寿就医登记接口出错：", e);
		}
		return "";
	}

	/**
	 * 国寿处方接口参数组装
	 * 返回国寿流水号
	 */
	@Override
	public boolean applyPrescription(List<ApplyPrescriptionParams> params) {
		try {
			HospitalFeeDetailInfoRequest hospitalFeeDetailInfoRequest = new HospitalFeeDetailInfoRequest();

			ApplyPrescriptionParams applyPrescriptionParams = params.get(0);

			//			applyPrescriptionParams.setItemTime("2008-09-20 10:00:00");
			//			applyPrescriptionParams.setPatName("程文刚");
			//			applyPrescriptionParams.setPatIdNo("372323198108172115");
			//			applyPrescriptionParams.setBirthDay("1981-08-17");
			//			applyPrescriptionParams.setMzFeeId("123456789");
			//			applyPrescriptionParams.setPatSex("1");

			//医疗机构代码
			hospitalFeeDetailInfoRequest.setMedicalInstituteCode("4401008000000044");
			//			hospitalFeeDetailInfoRequest.setMedicalInstituteCode(applyPrescriptionParams.getHospitalCode());
			//归属社保地区代码
			hospitalFeeDetailInfoRequest.setBelongInstituteAreaCode(BelongInstituteAreaCode); //120000 440100
			//个人唯一识别码
			hospitalFeeDetailInfoRequest.setPersonGUID(applyPrescriptionParams.getOpenId());
			//就医唯一识别码
			hospitalFeeDetailInfoRequest.setMedicalGUID(applyPrescriptionParams.getMzFeeId());
			//姓名
			hospitalFeeDetailInfoRequest.setName(applyPrescriptionParams.getPatName());
			//社会保障号/农合号
			hospitalFeeDetailInfoRequest.setSocialInsuranceNumber(applyPrescriptionParams.getOpenId());
			//性别代码
			//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
			hospitalFeeDetailInfoRequest.setGenderCode(applyPrescriptionParams.getPatSex() != null ? applyPrescriptionParams.getPatSex()
					: "9");
			//证件类型
			hospitalFeeDetailInfoRequest.setCredentialType(IdCardTypeEnum.getIdCardType(applyPrescriptionParams.getPatIdType()));
			//证件号码
			hospitalFeeDetailInfoRequest.setCredentialNum(applyPrescriptionParams.getPatIdNo());
			//出生日期
			hospitalFeeDetailInfoRequest.setBirthday(applyPrescriptionParams.getBirthDay());

			List<HospitalFeeItemRequest> hospitalFeeItem = new ArrayList<>();

			for (ApplyPrescriptionParams applyParams : params) {
				HospitalFeeItemRequest hospitalFeeItem1 = new HospitalFeeItemRequest();

				//处方明细唯一编号
				hospitalFeeItem1.setMedicalFeeSerialNumber(applyParams.getItemId());
				//医院处方明细唯一编号
				hospitalFeeItem1.setHospitalFeeSerialNumber(applyParams.getItemId());
				//社保医疗目录编码
				//				hospitalFeeItem1.setMedicalCatalogCode(prescription.getProperty("MedicalCatalogCode"));
				hospitalFeeItem1.setMedicalCatalogCode(applyParams.getMedicalCatalogCode());
				//社保医疗目录名称
				hospitalFeeItem1.setMedicalCatalogName(applyParams.getItemName());
				//社保报销类别
				//				hospitalFeeItem1.setMedicalInsurancePaymentType(prescription.getProperty("MedicalInsurancePaymentType"));

				/*final String itemName = applyParams.getItemName();
				Collection<Object> result = Collections2.filter(InitDataServlet.medicalInsurancePaymentTypes, new Predicate<Object>() {
					@Override
					public boolean apply(Object obj) {
						return obj.toString().trim().contains(itemName);
					}
				});

				if (!CollectionUtils.isEmpty(result)) {
					String rel = (String) result.toArray()[0];
					String medicalInsurancePaymentType = rel.split("=")[1];
					hospitalFeeItem1.setMedicalInsurancePaymentType(medicalInsurancePaymentType);
				}*/

				hospitalFeeItem1.setMedicalInsurancePaymentType(applyParams.getMedicalInsurancePaymentType());
				//				hospitalFeeItem1.setMedicalInsurancePaymentType(prescription.getProperty("MedicalInsurancePaymentType"));
				//医疗项目类别
				hospitalFeeItem1.setMedicalClassification(prescription.getProperty("MedicalClassification"));
				//单价
				hospitalFeeItem1.setUnitPrice(applyParams.getItemPrice());
				//限价标识
				hospitalFeeItem1.setLimitedPriceSign(prescription.getProperty("LimitedPriceSign"));
				//数量
				hospitalFeeItem1.setQuantity(applyParams.getItemNumber());
				//自费比例
				//				hospitalFeeItem1.setSelfPaymentRatio(prescription.getProperty("SelfPaymentRatio"));
				hospitalFeeItem1.setSelfPaymentRatio(applyParams.getSelfPaymentRatio());
				//				hospitalFeeItem1.setSelfPaymentRatio(prescription.getProperty("SelfPaymentRatio"));
				//医疗费用类别
				hospitalFeeItem1.setMedicalFeeType(MedicalFeeTypeEnum.getMedicalFeeType(applyParams.getItemType()));
				//总金额
				hospitalFeeItem1.setTotalAmount(applyParams.getItemTotalFee());
				//合规标识
				hospitalFeeItem1.setCompliantSign(prescription.getProperty("CompliantSign"));
				//处方药标识
				hospitalFeeItem1.setPrescriptionIdentification(prescription.getProperty("PrescriptionIdentification"));
				//单位
				hospitalFeeItem1.setUnit(applyParams.getItemUnit());
				//规格
				hospitalFeeItem1.setSpecification(applyParams.getItemSpec());
				//处方医生
				hospitalFeeItem1.setMasterDoctor(StringUtils.defaultString(applyParams.getDoctorName(), ""));
				//处方医生编码
				hospitalFeeItem1.setProviderID(StringUtils.defaultString(applyParams.getDoctorCode(), ""));

				//医嘱执行时间
				hospitalFeeItem1.setOrderExecutedDate(applyParams.getItemTime());

				hospitalFeeItem.add(hospitalFeeItem1);
			}

			hospitalFeeDetailInfoRequest.setHospitalFeeItems(hospitalFeeItem);

			//调取国泰处方上传接口
			InsuranceChinalifeService insuranceService = SpringContextHolder.getBean(InsuranceChinalifeService.class);
			InsuranceResponse response = insuranceService.hospitalFeeDetailInfo(hospitalFeeDetailInfoRequest);
			logger.info("国寿处方上传返回结果：" + JSONObject.toJSONString(response));

			if (response.getResultCode().equals("0")) {
				return true;
			}
		} catch (Exception e) {
			logger.error("调取国寿处方上传接口出错：", e);
			throw new SystemException("调取国寿处方上传接口出错：", e);
		}
		return false;
	}

	/**
	 * 国寿就医结算接口参数组装
	 */
	@Override
	public boolean applyMedicalSettlement(List<ApplyMedicalSettlementParams> params) {
		try {
			HospitalSettlementRequest hospitalSettlementRequest = new HospitalSettlementRequest();
			ApplyMedicalSettlementParams applyMedicalSettlementParams = params.get(0);

			//			applyMedicalSettlementParams.setPatName("程文刚");
			//			applyMedicalSettlementParams.setPatSex("1");
			//			applyMedicalSettlementParams.setPatIdType("1");
			//			applyMedicalSettlementParams.setPatIdNo("372323198108172115");
			//			applyMedicalSettlementParams.setBirthDay("1981-08-17");
			//			applyMedicalSettlementParams.setTotalAmount("15.00");
			//			applyMedicalSettlementParams.setPayTime("2008-09-20 10:00:00");
			//			applyMedicalSettlementParams.setMzFeeId("123456789");

			//医疗机构代码
			hospitalSettlementRequest.setMedicalInstituteCode("4401008000000044");
			//			hospitalSettlementRequest.setMedicalInstituteCode(applyMedicalSettlementParams.getHospitalCode());
			//归属社保地区代码
			hospitalSettlementRequest.setBelongInstituteAreaCode(BelongInstituteAreaCode); //120000 440100
			//个人唯一识别码
			hospitalSettlementRequest.setPersonGUID(applyMedicalSettlementParams.getOpenId());
			//社会保障号/农合号
			hospitalSettlementRequest.setSocialInsuranceNumber(applyMedicalSettlementParams.getOpenId());
			//就医唯一识别码
			hospitalSettlementRequest.setMedicalGUID(applyMedicalSettlementParams.getMzFeeId());
			//姓名
			hospitalSettlementRequest.setName(applyMedicalSettlementParams.getPatName()); //程文刚
			//性别代码
			//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
			hospitalSettlementRequest.setGenderCode(applyMedicalSettlementParams.getPatSex() != null ? applyMedicalSettlementParams
					.getPatSex() : "9"); //1
			//证件类型
			hospitalSettlementRequest.setCredentialType(IdCardTypeEnum.getIdCardType(applyMedicalSettlementParams.getPatIdType())); //I
			//证件号码
			hospitalSettlementRequest.setCredentialNum(applyMedicalSettlementParams.getPatIdNo()); //372323198108172115
			//出生日期
			hospitalSettlementRequest.setBirthday(applyMedicalSettlementParams.getBirthDay()); //"1981-08-17"
			//补偿结算方式, 即时、非即时
			hospitalSettlementRequest.setSettlementWay("3");
			//医疗补偿类型
			hospitalSettlementRequest.setMedicalPaymentType(settlement.getProperty("MedicalPaymentType"));
			//就医结算唯一编号
			hospitalSettlementRequest.setSettlementSerialNumber(applyMedicalSettlementParams.getMzFeeId());
			//就医类型 14-门诊 21-普通住院
			hospitalSettlementRequest.setMedicalType("14");
			//就诊时间
			hospitalSettlementRequest
					.setMedicalDate(DateUtils.dateToString(new Date(Long.parseLong(applyMedicalSettlementParams.getTime())))); //"2008-09-20 10:00:00"
			//离院方式
			hospitalSettlementRequest.setLeaveHospitalStyle(settlement.getProperty("LeaveHospitalStyle"));
			//离院状态
			hospitalSettlementRequest.setLeaveHospitalState(settlement.getProperty("LeaveHospitalState"));
			//异地就医标识
			hospitalSettlementRequest.setOffsiteMedicalSign(settlement.getProperty("OffsiteMedicalSign"));
			//转诊补偿比例
			hospitalSettlementRequest.setReferralPaymentRatio(settlement.getProperty("ReferralPaymentRatio"));
			//离院诊断疾病
			hospitalSettlementRequest.setLeaveHospitalDiagnosisCode(settlement.getProperty("LeaveHospitalDiagnosisCode")); //J06.901
			//医疗总费用
			hospitalSettlementRequest.setTotalAmount(com.yxw.utils.StringUtils.cent2Yuan(applyMedicalSettlementParams.getTotalAmout(),
					false)); // "15.00"
			//离院时间
			hospitalSettlementRequest.setLeaveHospitalDate(applyMedicalSettlementParams.getPayTime()); //"2008-09-20 10:00:00"
			//社保结算时间
			hospitalSettlementRequest.setMedicalInsuranceSettleDate(applyMedicalSettlementParams.getPayTime()); //"2008-09-20 10:00:00"
			//医院结算时间
			hospitalSettlementRequest.setHospitalSettleDate(applyMedicalSettlementParams.getPayTime()); //"2008-09-20 10:00:00"
			//付款对象
			hospitalSettlementRequest.setPaymentTarget(settlement.getProperty("PaymentTarget"));
			//结算年度
			hospitalSettlementRequest.setSettleYear(settlement.getProperty("SettleYear"));
			//银行代码
			hospitalSettlementRequest.setBankCode(applyMedicalSettlementParams.getBankCode());
			//银行名称
			hospitalSettlementRequest.setBankName(applyMedicalSettlementParams.getBankName());
			//银行账号
			hospitalSettlementRequest.setAccountNumber(applyMedicalSettlementParams.getBankCardNo());
			//乙类自付费用
			hospitalSettlementRequest.setPartialSelfPayment(settlement.getProperty("PartialSelfPayment")); //0
			//丙类全自费费用
			hospitalSettlementRequest.setPaymentBySelf(settlement.getProperty("PaymentBySelf")); //0
			/*

			List<SocialInsuranceAmountRequest> socialInsuranceAmounts = new ArrayList<>();

			for (ApplyMedicalSettlementParams applyParams : params) {

				SocialInsuranceAmountRequest socialInsuranceAmountRequest1 = new SocialInsuranceAmountRequest();
				//社保险种代码
				socialInsuranceAmountRequest1.setSocialInsuranceCode("yxw");
				//社保险种名称 结算方式类型
				socialInsuranceAmountRequest1.setPayType(applyParams.getPayType());
				//社保合规费用
				socialInsuranceAmountRequest1.setMedicalInsuranceCompliantAmount("0");
				//社保补偿金额
				socialInsuranceAmountRequest1.setMedicalInsurancePayment("0"); //applyParams.getMedicareAmout()
				//社保起付线
				socialInsuranceAmountRequest1.setMedicalInsuranceStartPayLine("0");

				//社保不报销金额
				socialInsuranceAmountRequest1.setMedicalInsuranceDeduction("15.00");

				socialInsuranceAmounts.add(socialInsuranceAmountRequest1);

			}*/

			//----------------------------------------------开始------------------------------------
			List<SocialInsuranceAmountRequest> socialInsuranceAmounts = new ArrayList<>();
			SocialInsuranceAmountRequest socialInsuranceAmount1 = new SocialInsuranceAmountRequest();
			//社保险种代码
			socialInsuranceAmount1.setSocialInsuranceCode(settlement.getProperty("SocialInsuranceCode"));
			//社保合规费用
			socialInsuranceAmount1.setMedicalInsuranceCompliantAmount(settlement.getProperty("MedicalInsuranceCompliantAmoun"));
			//社保补偿金额
			socialInsuranceAmount1.setMedicalInsurancePayment(settlement.getProperty("MedicalInsurancePayment"));
			//社保不报销金额
			socialInsuranceAmount1.setMedicalInsuranceDeduction("");//不填
			//社保起付线
			socialInsuranceAmount1.setMedicalInsuranceStartPayLine(settlement.getProperty("MedicalInsuranceStartPayLine"));

			socialInsuranceAmounts.add(socialInsuranceAmount1);

			hospitalSettlementRequest.setSocialInsuranceAmounts(socialInsuranceAmounts);
			//调取国泰处方上传接口
			InsuranceChinalifeService insuranceService = SpringContextHolder.getBean(InsuranceChinalifeService.class);
			InsuranceResponse response = insuranceService.hospitalSettlement(hospitalSettlementRequest);
			logger.info("国寿就医结算上传返回结果：" + JSONObject.toJSONString(response));

			if (response.getResultCode().equals("0")) {
				return true;
			}
		} catch (Exception e) {
			logger.error("调取国寿就医结算上传接口出错：", e);
			throw new SystemException("调取国寿就医结算上传接口出错：", e);
		}
		return false;
	}

	/**
	 * 查询缴费列表
	 * @param parmas
	 * @return
	 */
	@Override
	public List<ClinicRecord> getMZPayFeeList(PaidMZDetailCommParams params) {
		try {
			Request request = new Request();
			request.setMethodName(InsuranceLocalDataMethodConstant.PAID_MZ_LIST_QUERY);
			request.setParams(params);
			request.setResponseType("1");

			Response response = insuranceLocalDataService.localService(request);
			logger.info("获取查询缴费列表返回结果：" + JSONObject.toJSONString(response));
			if (response.getResultCode().equals(OutsideConstant.OUTSIDE_SUCCESS)) {
				List<ClinicRecord> result = JSON.parseArray(response.getResult(), ClinicRecord.class);
				return result;
			}

		} catch (Exception e) {
			logger.error("查询缴费列表：", e);
			throw new SystemException("查询缴费列表出现异常：", e);
		}

		return null;
	}

	/**
	 * 更新理赔状态
	 */
	@Override
	public boolean updateClaimStatus(PaidMZDetailReuslt params) {
		try {
			Request request = new Request();
			request.setMethodName(InsuranceLocalDataMethodConstant.CLINICRECORD_UPDATE);
			request.setParams(params);
			request.setResponseType("1");

			Response response = insuranceLocalDataService.localService(request);
			logger.info("更新理赔状态返回结果：" + JSONObject.toJSONString(response));
			if (response.getResultCode().equals(OutsideConstant.OUTSIDE_SUCCESS)) {
				ClinicRecord clinicRecord = new ClinicRecord();
				clinicRecord.setOrderNo(params.getOrderNo());
				clinicRecord.setIsClaim(params.getIsClaim());
				List<Object> cache = new ArrayList<Object>();
				cache.add(clinicRecord);
				serveComm.pop(CacheType.CLINIC_RECORD_CACHE, "updateRecord", cache);
				return true;
			}

		} catch (Exception e) {
			logger.error("更新理赔状态：", e);
			throw new SystemException("更新理赔状态出现异常：", e);
		}
		return false;
	}
}
