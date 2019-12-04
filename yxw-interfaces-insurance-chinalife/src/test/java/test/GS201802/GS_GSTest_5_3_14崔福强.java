package test.GS201802;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalSettlement;
import com.yxw.insurance.chinalife.interfaces.entity.request.SocialInsuranceAmount;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalSettlementResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;
import com.yxw.utils.DateUtils;
import com.yxw.utils.IdCardUtils;

public class GS_GSTest_5_3_14崔福强 {
	
	public static void main(String[] args) throws Exception {
		String hospitalSettlementRequestJson = buildHospitalSettlementRequestJson();
		System.out.println(hospitalSettlementRequestJson);
		
		System.out.println("----------------------");
		
		String responseJsonString = WebServiceUtil.invoke(hospitalSettlementRequestJson);
		if (StringUtils.isNotBlank(responseJsonString)) {
			JSONObject responseJson = JSONObject.parseObject(responseJsonString);
			HospitalSettlementResponse response = JSONObject.toJavaObject(
					(JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalSettlementResponse"), HospitalSettlementResponse.class);
			
			BeanUtils.copyProperties(response.getInsuranceCompensation(), response.getBusinessProcessStatus());
			
			System.out.println(JSONObject.toJSONString(response, true));
			System.out.println(responseJson.toJSONString());
		}
		
	}
	
	private static String buildHospitalSettlementRequestJson() {
		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();
		
		String name = "崔福强";
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		String genderCode = "1";
		String medicalCard = "2001898335";
		String idNo = "50024219840516613X";
		String orderNo = "Y147503411122783824978";
		
		String hospitalCode = "4401008000000250";
		//String hospitalName = "增城区人民医院";
		
		String bankCode = "PSBC";
		String bankName = "中国邮政储蓄银行";
		String bankAccountHolder = "崔福强";
		String bankAccountNumber = "6217996900006713748";
		
		//521+1558.6
		String tatalAmount = "672.00";
		
		JSONObject body = new JSONObject();
		HospitalSettlement hospitalSettlement = new HospitalSettlement();
		//医疗机构代码
		//hospitalSettlement.setMedicalInstituteCode("gzhszhyy");
		hospitalSettlement.setMedicalInstituteCode(hospitalCode);
		//归属社保地区代码
		hospitalSettlement.setBelongInstituteAreaCode("440100");  //120000 440100
		//hospitalSettlement.setBelongInstituteAreaCode("120000");  //120000 440100
		//个人唯一识别码
		hospitalSettlement.setPersonGUID(medicalCard);
		//社会保障号/农合号
		hospitalSettlement.setSocialInsuranceNumber(idNo);
		//就医唯一识别码
		hospitalSettlement.setMedicalGUID(orderNo);
		//姓名
		hospitalSettlement.setName(name);
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalSettlement.setGenderCode(genderCode);
		//证件类型
		hospitalSettlement.setCredentialType("I");
		//证件号码
		hospitalSettlement.setCredentialNum(idNo);
		//出生日期
		hospitalSettlement.setBirthday(DateUtils.formatDate(IdCardUtils.getBirthByIdCard(idNo), "yyyyMMdd", "yyyy-MM-dd"));
		//补偿结算方式, 即时、非即时
		hospitalSettlement.setSettlementWay("3");
		//医疗补偿类型
		hospitalSettlement.setMedicalPaymentType("N");
		//就医结算唯一编号
		hospitalSettlement.setSettlementSerialNumber(orderNo);
		//就医类型 14-门诊 21-普通住院
		hospitalSettlement.setMedicalType("14");
		//就诊时间
		hospitalSettlement.setMedicalDate("2017-12-23 00:00:00");
		//离院方式
		hospitalSettlement.setLeaveHospitalStyle("9");
		//离院状态
		hospitalSettlement.setLeaveHospitalState("9");
		//异地就医标识
		hospitalSettlement.setOffsiteMedicalSign("0");
		//转诊补偿比例
		hospitalSettlement.setReferralPaymentRatio("1");
		//离院诊断疾病
		hospitalSettlement.setLeaveHospitalDiagnosisCode("NA");
		hospitalSettlement.setLeaveHospitalDiagnosisName("右前臂肌腱软组织挫伤");
		//医疗总费用
		hospitalSettlement.setTotalAmount(tatalAmount);
		//离院时间
		hospitalSettlement.setLeaveHospitalDate("2017-12-23 00:00:00");
		//社保结算时间
		hospitalSettlement.setMedicalInsuranceSettleDate("2017-12-23 00:00:00");
		//医院结算时间
		hospitalSettlement.setHospitalSettleDate("2017-12-23 00:00:00");
		//付款对象
		hospitalSettlement.setPaymentTarget("0");
		//结算年度
		hospitalSettlement.setSettleYear("2017");
		//银行代码
		hospitalSettlement.setBankCode(bankCode);
		//银行名称
		hospitalSettlement.setBankName(bankName);
		//开户人姓名
		hospitalSettlement.setAccountHolder(bankAccountHolder);
		//银行账号
		hospitalSettlement.setAccountNumber(bankAccountNumber);
		//乙类自付费用
		hospitalSettlement.setPartialSelfPayment("0");
		//丙类全自费费用
		hospitalSettlement.setPaymentBySelf("0");
		
		List<SocialInsuranceAmount> socialInsuranceAmounts = new ArrayList<>();
		SocialInsuranceAmount socialInsuranceAmount1 = new SocialInsuranceAmount();
		//社保险种代码
		socialInsuranceAmount1.setSocialInsuranceCode("");
		//社保合规费用
		socialInsuranceAmount1.setMedicalInsuranceCompliantAmount("0");
		//社保补偿金额
		socialInsuranceAmount1.setMedicalInsurancePayment("0");
		//社保不报销金额
		socialInsuranceAmount1.setMedicalInsuranceDeduction(tatalAmount);
		//社保起付线
		socialInsuranceAmount1.setMedicalInsuranceStartPayLine("0");
		
		socialInsuranceAmounts.add(socialInsuranceAmount1);
		
		hospitalSettlement.setSocialInsuranceAmounts(socialInsuranceAmounts);

		body.put("HospitalSettlement", hospitalSettlement);
		
		//InsuranceCompensation insuranceCompensation = new InsuranceCompensation();
		//body.put("InsuranceCompensation", insuranceCompensation);
		
		root.put("head", hospitalSettlement.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
