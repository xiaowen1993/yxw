package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeDetailInfo;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeItem;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalFeeDetailInfoResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;

public class GSTest_5_3_11 {
	
	public static void main(String[] args) throws Exception {
		String hospitalFeeDetailInfoRequestJson = buildHospitalFeeDetailInfoRequestJson();
		System.out.println(hospitalFeeDetailInfoRequestJson);
		
		System.out.println("----------------------");
		
		String responseJsonString = WebServiceUtil.invoke(hospitalFeeDetailInfoRequestJson);
		if (StringUtils.isNotBlank(responseJsonString)) {
			JSONObject responseJson = JSONObject.parseObject(responseJsonString);
			HospitalFeeDetailInfoResponse response = JSONObject.toJavaObject(
					(JSONObject) JSONPath.eval(responseJson, "$.root.body.HospitalFeeDetailInfoResponse"), HospitalFeeDetailInfoResponse.class);
			
			System.out.println(JSONObject.toJSONString(response, true));
			System.out.println(responseJson.toJSONString());
		}
		
	}
	
	private static String buildHospitalFeeDetailInfoRequestJson() {
		JSONObject requestJson = new JSONObject();
		JSONObject root = new JSONObject();
		
		String userId = "123456789";
		String orderNo = "123456789";
		
		JSONObject body = new JSONObject();
		HospitalFeeDetailInfo hospitalFeeDetailInfo = new HospitalFeeDetailInfo();
		//医疗机构代码
		hospitalFeeDetailInfo.setMedicalInstituteCode("gzhszhyy");
		//归属社保地区代码
		hospitalFeeDetailInfo.setBelongInstituteAreaCode("120000");  //120000 440100
		//个人唯一识别码
		hospitalFeeDetailInfo.setPersonGUID(userId);
		//社会保障号/农合号
		hospitalFeeDetailInfo.setSocialInsuranceNumber(userId);
		//就医唯一识别码
		hospitalFeeDetailInfo.setMedicalGUID(orderNo);
		//姓名
		hospitalFeeDetailInfo.setName("程文刚");
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalFeeDetailInfo.setGenderCode("1");
		//证件类型
		hospitalFeeDetailInfo.setCredentialType("I");
		//证件号码
		hospitalFeeDetailInfo.setCredentialNum("372323198108172115");
		//出生日期
		hospitalFeeDetailInfo.setBirthday("1981-08-17");
		
		List<HospitalFeeItem> hospitalFeeItems = new ArrayList<>();
		HospitalFeeItem hospitalFeeItem1 = new HospitalFeeItem();
		//处方明细唯一编号
		hospitalFeeItem1.setMedicalFeeSerialNumber(orderNo+"_1");
		//医院处方明细唯一编号
		hospitalFeeItem1.setHospitalFeeSerialNumber(orderNo+"_1");
		//医疗项目类别
		hospitalFeeItem1.setMedicalClassification("9");
		//限价标识
		hospitalFeeItem1.setLimitedPriceSign("0");
		//自费比例
		hospitalFeeItem1.setSelfPaymentRatio("1");
		//医疗费用类别
		hospitalFeeItem1.setMedicalFeeType("01");
		//合规标识
		hospitalFeeItem1.setCompliantSign("1");
		//处方药标识
		hospitalFeeItem1.setPrescriptionIdentification("");
		//社保医疗目录编码
		hospitalFeeItem1.setMedicalCatalogCode("yxw");
		//社保医疗目录名称
		hospitalFeeItem1.setMedicalCatalogName("yxw");
		//合规金额
		hospitalFeeItem1.setCompliantPrice("0.00");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2008-09-20 10:00:00");
		
		//单价
		hospitalFeeItem1.setUnitPrice("5.00");
		//数量
		hospitalFeeItem1.setQuantity("1");
		//总金额
		hospitalFeeItem1.setTotalAmount("5.00");
		
		
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
