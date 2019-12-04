package test.hh;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalRegistration;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalRegistrationResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;

public class GS_HHTest_5_3_09_2 {
	
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
		
		String userId = "440103194505292139";
		String orderNo = "reg147503411122783824980";
		
		JSONObject body = new JSONObject();
		HospitalRegistration hospitalRegistration = new HospitalRegistration();
		//归属社保地区代码
		hospitalRegistration.setBelongInstituteAreaCode("440100");
		//hospitalRegistration.setBelongInstituteAreaCode("120000");
		//医疗机构代码
		//hospitalRegistration.setMedicalInstituteCode("gzhszhyy");
		hospitalRegistration.setMedicalInstituteCode("4401008000000044");
		//个人唯一识别码
		hospitalRegistration.setPersonGUID(userId);
		//社会保障号/农合号
		hospitalRegistration.setSocialInsuranceNumber(userId);
		//姓名
		hospitalRegistration.setName("陈兆銮");
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalRegistration.setGenderCode("1");
		//证件类型
		hospitalRegistration.setCredentialType("I");
		//证件号码
		hospitalRegistration.setCredentialNum("440103194505292139");
		//出生日期
		hospitalRegistration.setBirthday("1945-05-29");
		//就医唯一识别码
		hospitalRegistration.setMedicalGUID(orderNo);
		//结算方式，即时、非即时
		hospitalRegistration.setSettlementWay("3");
		//就医类型
		hospitalRegistration.setMedicalType("14");
		//出险原因
		hospitalRegistration.setAccidentReason("1");
		//就诊时间
		hospitalRegistration.setMedicalDate("2017-10-30 10:00:00");
		//社会医疗保险类别
		hospitalRegistration.setSocialMedicareType("JD");
		//患者类型
		hospitalRegistration.setPatientType("O");
		hospitalRegistration.setMainDiagnosisCode("R10.102"); //主诊断疾病
		hospitalRegistration.setMainDiagnosisName("胃痛");
		hospitalRegistration.setSecondaryDiagnosisCode(hospitalRegistration.getMainDiagnosisCode()); //第二诊断疾病
		hospitalRegistration.setSecondaryDiagnosisName(hospitalRegistration.getMainDiagnosisName());

		body.put("HospitalRegistration", hospitalRegistration);
		
		
		root.put("head", hospitalRegistration.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
