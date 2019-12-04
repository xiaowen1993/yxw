package test.GS201802;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalRegistration;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalRegistrationResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;
import com.yxw.utils.DateUtils;
import com.yxw.utils.IdCardUtils;

public class GS_GSTest_5_3_09谭心爱 {
	
	public static void main(String[] args) throws Exception {
		String medicalRegistrationRequestJson = buildHospitalRegistrationRequestJson();
		System.out.println(medicalRegistrationRequestJson);
		
		System.out.println("----------------------");
		
		String responseJsonString = WebServiceUtil.invoke(medicalRegistrationRequestJson);
		if (StringUtils.isNotBlank(responseJsonString)) {
			JSONObject responseJson = JSONObject.parseObject(responseJsonString);
			HospitalRegistrationResponse response = JSONObject.toJavaObject(
					(JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalRegistrationResponse"), HospitalRegistrationResponse.class);
			
			if (response == null) {
				response = JSONObject.toJavaObject(
						(JSONObject) JSONPath.eval(responseJson, "$.root.body.Response"), HospitalRegistrationResponse.class);
			}
			
			System.out.println(JSONObject.toJSONString(response, true));
			System.out.println(responseJson.toJSONString());
		}
		
	}
	
	private static String buildHospitalRegistrationRequestJson() {
		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();
		
		//success 平台流水号：201844010090013309
		String name = "谭心爱";
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		String genderCode = "2";
		String medicalCard = "11262667";
		String idNo = "440111193912230926";
		String orderNo = "Y147503411122783824977";
		String mobilephone = "13660885989";
		String address = "广东省广州市白云区茅山刘家街96号";
		
		
		String hospitalCode = "4401008000000117";
		String hospitalName = "广州中医药大学第一附属医院";
		
		JSONObject body = new JSONObject();
		HospitalRegistration hospitalRegistration = new HospitalRegistration();
		//归属社保地区代码
		hospitalRegistration.setBelongInstituteAreaCode("440100");
		//医疗机构代码
		hospitalRegistration.setMedicalInstituteCode(hospitalCode);
		hospitalRegistration.setMedicalInstituteName(hospitalName);
		//个人唯一识别码
		hospitalRegistration.setPersonGUID(medicalCard);
		//社会保障号/农合号
		hospitalRegistration.setSocialInsuranceNumber(idNo);
		//姓名
		hospitalRegistration.setName(name);
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalRegistration.setGenderCode(genderCode);
		//证件类型
		hospitalRegistration.setCredentialType("I");
		//证件号码
		hospitalRegistration.setCredentialNum(idNo);
		//出生日期
		hospitalRegistration.setBirthday(DateUtils.formatDate(IdCardUtils.getBirthByIdCard(idNo), "yyyyMMdd", "yyyy-MM-dd"));
		hospitalRegistration.setMobilephone(mobilephone);
		hospitalRegistration.setAddress(address);
		//就医唯一识别码
		hospitalRegistration.setMedicalGUID(orderNo);
		//结算方式，即时、非即时
		hospitalRegistration.setSettlementWay("3");
		//就医类型 12急症 14门诊
		hospitalRegistration.setMedicalType("14");
		//出险原因 1疾病 2意外 3自杀
		hospitalRegistration.setAccidentReason("1");
		//就诊时间
		hospitalRegistration.setMedicalDate("2017-10-27 00:00:00");
		//社会医疗保险类别
		hospitalRegistration.setSocialMedicareType("JD");
		//患者类型 N本地人 O外地人 F境外人员
		hospitalRegistration.setPatientType("N");
		hospitalRegistration.setMainDiagnosisCode("R07400"); //主诊断疾病
		hospitalRegistration.setMainDiagnosisName("胸痛");
		hospitalRegistration.setSecondaryDiagnosisCode(hospitalRegistration.getMainDiagnosisCode());
		hospitalRegistration.setSecondaryDiagnosisName(hospitalRegistration.getMainDiagnosisName());

		body.put("HospitalRegistration", hospitalRegistration);
		
		
		root.put("head", hospitalRegistration.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
