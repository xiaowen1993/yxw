package com.yxw.insurance.biz.service.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.MedicalSettlementReuslt;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.commons.dto.outside.PrescriptionDetailReuslt;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.insurance.biz.dto.inside.ApplyClaimParams;
import com.yxw.insurance.biz.dto.inside.ApplyMedicalSettlementParams;
import com.yxw.insurance.biz.dto.inside.ApplyPrescriptionParams;
import com.yxw.insurance.biz.service.InsuranceService;

public class InsuranceServiceImpTest extends Junit4SpringContextHolder {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMZPayFeeDetail() {
		InsuranceService insuranceService = SpringContextHolder.getBean(InsuranceService.class);

		PaidMZDetailCommParams params = new PaidMZDetailCommParams();
		params.setAppCode("alipay");
		params.setAppId("2014112600017754");
		params.setHospitalCode("gzhszhyy");
		params.setMzFeeId("17047522");
		params.setOpenId("r09niMnPyGUxg1Xjd1uuqgyL0uGgefzwCeo3qohCp35d3EGNYt3AGuQ1TZLFp5Hy01");
		params.setOrderNo("Y22018041615201020142972");

		PaidMZDetailReuslt result = insuranceService.getMZPayFeeDetail(params);

		if (result != null) {
			result.setTime("2008-09-20 10:00:00");
			result.setPatName("程文刚");
			result.setPatIdNo("372323198108172115");
			result.setBirthDay("1981-08-17");
			params.setOpenId("123456789");
			result.setMzFeeId("123456789");
			result.setPatSex("1");

			ApplyClaimParams p = new ApplyClaimParams();
			p.setClaimType("疾病");
			p.setDeptName(result.getDeptName());
			p.setDoctorName(result.getDoctorName());
			p.setMzFeeId(result.getMzFeeId());
			p.setOpenId(params.getOpenId());
			p.setPatAddress(result.getPatAddress());
			p.setBirthDay(result.getBirthDay());
			p.setPatIdNo(result.getPatIdNo());
			p.setPatIdType(result.getPatIdType());
			p.setPatMobile(result.getPatMobile());
			p.setPatName(result.getPatName());
			p.setPatSex(result.getPatSex());
			p.setTime(result.getTime());

			System.err.println("返回结果：" + insuranceService.applyClaim(p));
		}

	}

	@Test
	public void testApplyClaim() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCFRecordDetail() {

		InsuranceService insuranceService = SpringContextHolder.getBean(InsuranceService.class);

		PaidMZDetailCommParams params = new PaidMZDetailCommParams();
		params.setAppCode("alipay");
		params.setAppId("2014112600017754");
		params.setHospitalCode("gzhszhyy");
		params.setMzFeeId("17047522");
		params.setOpenId("r09niMnPyGUxg1Xjd1uuqgyL0uGgefzwCeo3qohCp35d3EGNYt3AGuQ1TZLFp5Hy01");
		params.setOrderNo("Y22018041615201020142972");

		List<PrescriptionDetailReuslt> result = insuranceService.getCFRecordDetail(params);

		if (!CollectionUtils.isEmpty(result)) {
			//调用国寿测试接口必须改变以下值
			List<ApplyPrescriptionParams> lsp = new ArrayList<ApplyPrescriptionParams>();

			for (PrescriptionDetailReuslt prescriptionDetailReuslt : result) {
				prescriptionDetailReuslt.setItemTime("2008-09-20 10:00:00");
				prescriptionDetailReuslt.setPatName("程文刚");
				prescriptionDetailReuslt.setPatIdNo("372323198108172115");
				prescriptionDetailReuslt.setBirthDay("1981-08-17");
				prescriptionDetailReuslt.setMzFeeId("123456789");
				prescriptionDetailReuslt.setPatSex("1");

				ApplyPrescriptionParams p = new ApplyPrescriptionParams();
				p.setBankCardNo("1");
				p.setBankName("中国工商银行");
				p.setClaimType("疾病");
				p.setOpenId("123456789");

				p.setMzFeeId(prescriptionDetailReuslt.getMzFeeId());
				p.setPatName(prescriptionDetailReuslt.getPatName());
				p.setPatSex(prescriptionDetailReuslt.getPatSex());
				p.setPatIdType(prescriptionDetailReuslt.getPatIdType());
				p.setPatIdNo(prescriptionDetailReuslt.getPatIdNo());
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

			System.err.println("返回结果：" + insuranceService.applyPrescription(lsp));
		}
	}

	/**
	 * 测试就医结算
	 */
	@Test
	public void testGetMedicalSettlement() {

		InsuranceService insuranceService = SpringContextHolder.getBean(InsuranceService.class);

		PaidMZDetailCommParams params = new PaidMZDetailCommParams();
		params.setAppCode("alipay");
		params.setAppId("2014112600017754");
		params.setHospitalCode("gzhszhyy");
		params.setMzFeeId("17047522");
		params.setOpenId("r09niMnPyGUxg1Xjd1uuqgyL0uGgefzwCeo3qohCp35d3EGNYt3AGuQ1TZLFp5Hy01");
		params.setOrderNo("Y22018041615201020142972");

		List<MedicalSettlementReuslt> result = insuranceService.getMedicalSettlement(params);

		if (!CollectionUtils.isEmpty(result)) {
			//调用国寿测试接口必须改变以下值
			List<ApplyMedicalSettlementParams> lsp = new ArrayList<ApplyMedicalSettlementParams>();
			for (MedicalSettlementReuslt medicalSettlementReuslt : result) {

				medicalSettlementReuslt.setPatName("程文刚");
				medicalSettlementReuslt.setPatIdNo("372323198108172115");
				medicalSettlementReuslt.setBirthDay("1981-08-17");
				medicalSettlementReuslt.setMzFeeId("123456789");
				medicalSettlementReuslt.setPatSex("1");
				medicalSettlementReuslt.setPayTime("2008-09-20 10:00:00");
				medicalSettlementReuslt.setTime("2008-09-20 10:00:00");
				medicalSettlementReuslt.setTotalAmout("15.00");
				ApplyMedicalSettlementParams p = new ApplyMedicalSettlementParams();
				p.setBankCardNo("1");
				p.setBankName("中国工商银行");
				p.setClaimType("疾病");
				p.setOpenId("123456789");
				p.setHospitalCode("gzhszhyy");

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
				p.setHospitalCode(p.getHospitalCode());
				lsp.add(p);
			}

			System.err.println("返回结果：" + insuranceService.applyMedicalSettlement(lsp));
		}
	}
}
