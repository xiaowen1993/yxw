package com.yxw.insurance.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.constant.ClaimStatusEnum;
import com.yxw.insurance.biz.dto.inside.ApplyClaimParams;
import com.yxw.insurance.biz.dto.inside.ApplyMedicalSettlementParams;
import com.yxw.insurance.biz.dto.inside.ApplyPrescriptionParams;
import com.yxw.insurance.biz.entity.ClaimOrder;
import com.yxw.insurance.biz.entity.MzFeeData;
import com.yxw.insurance.biz.service.ClaimBlankService;
import com.yxw.insurance.biz.service.ClaimOrderService;
import com.yxw.insurance.biz.service.InsuranceBusinessService;
import com.yxw.insurance.biz.service.InsuranceService;
import com.yxw.insurance.biz.service.MzFeeDataService;
import com.yxw.insurance.init.InitDataServlet;
import com.yxw.utils.StringUtils;

@Service
public class InsuranceBusinessServiceImpl implements InsuranceBusinessService {

	InsuranceService insuranceService = SpringContextHolder.getBean(InsuranceService.class);

	ClaimOrderService claimOrderService = SpringContextHolder.getBean(ClaimOrderService.class);

	ClaimBlankService claimBlankService = SpringContextHolder.getBean(ClaimBlankService.class);

	MzFeeDataService mzFeeDataService = SpringContextHolder.getBean(MzFeeDataService.class);

	ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 申请理赔，生成订单
	 * @param params
	 * @return
	 */
	@Override
	public ClaimOrder applyClaim(PaidMZDetailReuslt params) {
		ApplyClaimParams claim = new ApplyClaimParams();
		claim.setBirthDay(params.getBirthDay());
		claim.setClaimType(params.getClaimType());
		claim.setDeptName(params.getDeptName());
		claim.setDoctorName(params.getDoctorName());
		claim.setMzFeeId(params.getMzFeeId());
		claim.setPatAddress(params.getPatAddress());
		claim.setPatIdNo(params.getPatIdNo());
		claim.setPatIdType(params.getPatIdType());
		claim.setPatMobile(params.getPatMobile());
		claim.setPatName(params.getPatName());
		claim.setPatSex(params.getPatSex());
		claim.setTime(params.getTime());
		claim.setOpenId(params.getOpenId());
		claim.setHospitalCode(params.getHospitalCode());

		claim.setMainDiagnosisCode(params.getMainDiagnosisCode());
		claim.setMainDiagnosisName(params.getMainDiagnosisName());

		// 调取国寿就医申请接口，返回流水号则申请成功，否则申请失败
		String claimNo = insuranceService.applyClaim(claim);
		ClaimOrder order = new ClaimOrder();
		if (!StringUtils.isEmpty(claimNo)) {
			// 申请中
			order.setFlowNumber(claimNo);//国寿接口返回的流水号
			order.setClaimStatus(ClaimStatusEnum.CLAIMING.getIndex());
		} else {
			// 无法在线理赔
			order.setClaimStatus(ClaimStatusEnum.CLAIM_ERROR.getIndex());
		}

		order.setId(PKGenerator.generateId());
		order.setAddress(claim.getPatAddress());
		order.setAccidentCause(claim.getClaimType());// 理赔原因
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		order.setClaimTime(date);
		order.setHospitalCode(params.getHospitalCode());// 医院代码
		order.setHospitalName("");// 医院名称
		order.setInsurer("中国人寿保险");// 保险公司
		order.setClaimOrderNo(params.getMzFeeId());
		order.setPatientName(claim.getPatName());
		order.setClaimProjectOrderNo(params.getOrderNo());
		order.setCreateTime(date);
		order.setUpdateTime(date);
		order.setAppCode(params.getAppCode());
		order.setAppId(params.getAppId());
		order.setOpenId(params.getOpenId());

		// 获取理赔项目和理赔金额
		MzFeeData mzFeeData = mzFeeDataService.findMzFeeData(order.getOpenId(), params.getMzFeeId());
		if (mzFeeData != null) {
			order.setClaimProject(ClinicConstant.DEFAULT_CLINIC_TITLE);
			order.setClaimFee(mzFeeData.getTotalAmout());
		}
		claimOrderService.saveClaimOrder(order);

		//更新理赔状态
		params.setIsClaim(1);
		insuranceService.updateClaimStatus(params);
		return order;
	}

	/**
	 * 上传就医处方明细
	 * @param order
	 */
	@Override
	public boolean uploadPrescriptionDetail(ClaimOrder order) {
		PaidMZDetailCommParams params = new PaidMZDetailCommParams();
		params.setAppCode(order.getAppCode());
		params.setAppId(order.getAppId());
		params.setHospitalCode(order.getHospitalCode());
		params.setMzFeeId(order.getClaimOrderNo());
		params.setOpenId(order.getOpenId());
		params.setOrderNo(order.getClaimProjectOrderNo());

		List<PrescriptionDetailReuslt> result = insuranceService.getCFRecordDetail(params);
		if (!CollectionUtils.isEmpty(result)) {
			// 调用国寿测试接口必须改变以下值
			List<ApplyPrescriptionParams> lsp = new ArrayList<ApplyPrescriptionParams>();

			for (PrescriptionDetailReuslt prescriptionDetailReuslt : result) {

				//TODO 临时修改项目名，因为his的名字和国寿的对不上，所以需要转换

				final String currItemName = prescriptionDetailReuslt.getItemName();
				Collection<String> rel1 = Collections2.filter(InitDataServlet.l1, new Predicate<String>() {
					@Override
					public boolean apply(String str) {
						List<String> tls = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(str);
						return tls.get(0).trim().equals(currItemName);
					}
				});

				//				String itemId = "";
				//				String itemName = "";
				String medicalCatalogCode = "";
				String medicalInsurancePaymentType = "";
				String selfPaymentRatio = "";

				if (!CollectionUtils.isEmpty(rel1)) {
					String newData = rel1.toArray(new String[rel1.size()])[0];

					List<String> res = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(newData);
					final String temp = res.get(1);
					Collection<String> rel = Collections2.filter(InitDataServlet.l2, new Predicate<String>() {
						@Override
						public boolean apply(String str) {
							List<String> tls = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(str);
							return tls.get(1).trim().equals(temp);
						}
					});

					if (!CollectionUtils.isEmpty(rel)) {
						String data = rel.toArray(new String[rel.size()])[0];
						List<String> datals = Splitter.onPattern("\\|_\\|").omitEmptyStrings().splitToList(data);
						//						itemId = datals.get(0);
						//						itemName = datals.get(1);
						medicalInsurancePaymentType = datals.get(2);
						selfPaymentRatio = datals.get(3);

						medicalCatalogCode = datals.get(0);
					}
				}

				ApplyPrescriptionParams p = new ApplyPrescriptionParams();
				p.setClaimType(order.getAccidentCause());
				p.setOpenId(order.getOpenId());
				p.setMzFeeId(prescriptionDetailReuslt.getMzFeeId());
				p.setPatName(prescriptionDetailReuslt.getPatName());
				p.setPatSex(prescriptionDetailReuslt.getPatSex());
				p.setPatIdType(prescriptionDetailReuslt.getPatIdType());
				p.setPatIdNo(prescriptionDetailReuslt.getPatIdNo());

				//				p.setItemId(itemId);
				//				p.setItemName(itemName);
				p.setMedicalCatalogCode(medicalCatalogCode);
				p.setMedicalInsurancePaymentType(medicalInsurancePaymentType);
				p.setSelfPaymentRatio(selfPaymentRatio);

				p.setItemId(prescriptionDetailReuslt.getItemId());
				p.setItemName(prescriptionDetailReuslt.getItemName());
				p.setItemPrice(prescriptionDetailReuslt.getItemPrice());
				p.setItemNumber(prescriptionDetailReuslt.getItemNumber());
				p.setItemType(prescriptionDetailReuslt.getItemType());
				p.setItemTotalFee(prescriptionDetailReuslt.getItemTotalFee());
				p.setItemUnit(prescriptionDetailReuslt.getItemUnit());
				p.setItemSpec(prescriptionDetailReuslt.getItemSpec());
				p.setDoctorName(prescriptionDetailReuslt.getDoctorName());
				p.setDoctorCode(prescriptionDetailReuslt.getDoctorCode());
				p.setItemTime(prescriptionDetailReuslt.getItemTime());
				p.setBirthDay(prescriptionDetailReuslt.getBirthDay());

				lsp.add(p);
			}

			return insuranceService.applyPrescription(lsp);
		}
		return false;
	}

	/**
	 * 结算交易
	 */
	@Override
	public boolean settlementTransaction(ClaimOrder order) {
		PaidMZDetailCommParams params = new PaidMZDetailCommParams();
		params.setAppCode(order.getAppCode());
		params.setAppId(order.getAppId());
		params.setHospitalCode(order.getHospitalCode());
		params.setMzFeeId(order.getClaimOrderNo());
		params.setOpenId(order.getOpenId());
		params.setOrderNo(order.getClaimProjectOrderNo());

		List<MedicalSettlementReuslt> result = insuranceService.getMedicalSettlement(params);//缺少hospitalCode字段
		if (!CollectionUtils.isEmpty(result)) {
			// 调用国寿测试接口必须改变以下值
			List<ApplyMedicalSettlementParams> lsp = new ArrayList<ApplyMedicalSettlementParams>();
			for (MedicalSettlementReuslt medicalSettlementReuslt : result) {

				ApplyMedicalSettlementParams p = new ApplyMedicalSettlementParams();
				p.setBankCardNo(order.getPatientCardNo());
				p.setBankName(order.getBankName());
				p.setBankCode(order.getBankCode());
				p.setClaimType(order.getAccidentCause());
				p.setOpenId(order.getOpenId());

				p.setMzFeeId(medicalSettlementReuslt.getMzFeeId());
				p.setPatName(medicalSettlementReuslt.getPatName());
				p.setPatSex(medicalSettlementReuslt.getPatSex());
				p.setPatIdType(medicalSettlementReuslt.getPatIdType());
				p.setPatIdNo(medicalSettlementReuslt.getPatIdNo());
				p.setHisOrdNum(medicalSettlementReuslt.getHisOrdNum());
				p.setTime(medicalSettlementReuslt.getTime());
				p.setPayTime(medicalSettlementReuslt.getPayTime());
				p.setTotalAmout(medicalSettlementReuslt.getTotalAmout());
				p.setPayType(medicalSettlementReuslt.getPayType());
				p.setMedicareAmout(medicalSettlementReuslt.getMedicareAmout());
				p.setBirthDay(medicalSettlementReuslt.getBirthDay());
				lsp.add(p);
			}

			return insuranceService.applyMedicalSettlement(lsp);
		}
		return false;
	}

	public RuleQuery getRuleQueryByHospitalCode(String hospitalCode) {
		RuleQuery rule = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_RULE_CACHE, "queryRuleQueryByHospitalCode", params);
		if (!CollectionUtils.isEmpty(objects)) {
			rule = (RuleQuery) objects.get(0);
		}
		return rule;
	}

	/**
	 * 获取医院的所有接入平台信息
	 * 
	 * @param hospitalId
	 * @return
	 */
	public HospIdAndAppSecretVo getHospitalEasyHealthAppInfo(String hospitalId, String appCode) {
		HospIdAndAppSecretVo vo = null;
		PlatformSettings settings = null;

		List<Object> params = new ArrayList<Object>();
		if (appCode == null) {
			appCode = "";
		}

		String appId = "";

		params.add(hospitalId);
		params.add(appCode);
		List<Object> hospitalPlatforms = serveComm.get(CacheType.HOSPITAL_CACHE, "findHospitalPlatform", params);
		if (!CollectionUtils.isEmpty(hospitalPlatforms)) {
			settings = (PlatformSettings) hospitalPlatforms.get(0);

			if (!StringUtils.isEmpty(settings.getAppId())) {
				appId = settings.getAppId();
			}
		}

		params.clear();
		params.add(hospitalId);
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getEasyHealthAppByHospitalId", params);
		if (!CollectionUtils.isEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
		}

		return vo;
	}
}
