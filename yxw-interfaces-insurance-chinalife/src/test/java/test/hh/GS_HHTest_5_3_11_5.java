package test.hh;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeDetailInfo;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeItem;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalFeeDetailInfoResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;

public class GS_HHTest_5_3_11_5 {
	
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
		
		String userId = "421302200605090016";
		String orderNo = "reg147503411122783824983";
		
		JSONObject body = new JSONObject();
		HospitalFeeDetailInfo hospitalFeeDetailInfo = new HospitalFeeDetailInfo();
		//医疗机构代码
		//hospitalFeeDetailInfo.setMedicalInstituteCode("gzhszhyy");
		hospitalFeeDetailInfo.setMedicalInstituteCode("4401008000000044");
		//归属社保地区代码
		hospitalFeeDetailInfo.setBelongInstituteAreaCode("440100");  //120000 440100
		//hospitalFeeDetailInfo.setBelongInstituteAreaCode("120000");  //120000 440100
		//个人唯一识别码
		hospitalFeeDetailInfo.setPersonGUID(userId);
		//社会保障号/农合号
		hospitalFeeDetailInfo.setSocialInsuranceNumber(userId);
		//就医唯一识别码
		hospitalFeeDetailInfo.setMedicalGUID(orderNo);
		//姓名
		hospitalFeeDetailInfo.setName("邱鹿鸣");
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalFeeDetailInfo.setGenderCode("1");
		//证件类型
		hospitalFeeDetailInfo.setCredentialType("I");
		//证件号码
		hospitalFeeDetailInfo.setCredentialNum("421302200605090016");
		//出生日期
		hospitalFeeDetailInfo.setBirthday("2006-05-09");
		
		List<HospitalFeeItem> hospitalFeeItems = new ArrayList<>();
		HospitalFeeItem hospitalFeeItem1 = new HospitalFeeItem();
		//处方明细唯一编号
		hospitalFeeItem1.setMedicalFeeSerialNumber(orderNo+"_1");
		//医院处方明细唯一编号
		hospitalFeeItem1.setHospitalFeeSerialNumber(orderNo+"_1");
		//医疗项目类别
		hospitalFeeItem1.setMedicalClassification("2"); //诊疗项目
		//限价标识
		hospitalFeeItem1.setLimitedPriceSign("0");
		//自费比例
		hospitalFeeItem1.setSelfPaymentRatio("1");
		//医疗费用类别
		hospitalFeeItem1.setMedicalFeeType("7"); //B超
		//合规标识
		hospitalFeeItem1.setCompliantSign("1");
		//处方药标识
		hospitalFeeItem1.setPrescriptionIdentification("");
		//社保医疗目录编码
		hospitalFeeItem1.setMedicalCatalogCode("2");
		//社保医疗目录名称
		hospitalFeeItem1.setMedicalCatalogName("诊疗");
		//合规金额
		hospitalFeeItem1.setCompliantPrice("0.00");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2017-10-30 10:00:00");
		
		//单价
		hospitalFeeItem1.setUnitPrice("40.00");
		//数量
		hospitalFeeItem1.setQuantity("1");
		//总金额
		hospitalFeeItem1.setTotalAmount("40.00");
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		
		HospitalFeeItem hospitalFeeItem2 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem2);
		//处方明细唯一编号
		hospitalFeeItem2.setMedicalFeeSerialNumber(orderNo+"_2");
		//医院处方明细唯一编号
		hospitalFeeItem2.setHospitalFeeSerialNumber(orderNo+"_2");
		//医疗项目类别
		hospitalFeeItem2.setMedicalClassification("1"); //药品
		//医疗费用类别
		hospitalFeeItem2.setMedicalFeeType("1"); //西药
		//社保医疗目录编码
		hospitalFeeItem2.setMedicalCatalogCode("1");
		//社保医疗目录名称
		hospitalFeeItem2.setMedicalCatalogName("药品");
		//单价
		hospitalFeeItem2.setUnitPrice("1.49");
		//数量
		hospitalFeeItem2.setQuantity("20");
		//总金额
		hospitalFeeItem2.setTotalAmount("29.80");
		hospitalFeeItems.add(hospitalFeeItem2);
		
		HospitalFeeItem hospitalFeeItem3 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem2, hospitalFeeItem3);
		//处方明细唯一编号
		hospitalFeeItem3.setMedicalFeeSerialNumber(orderNo+"_3");
		//医院处方明细唯一编号
		hospitalFeeItem3.setHospitalFeeSerialNumber(orderNo+"_3");
		//单价
		hospitalFeeItem3.setUnitPrice("1.75");
		//数量
		hospitalFeeItem3.setQuantity("40");
		//总金额
		hospitalFeeItem3.setTotalAmount("70.00");
		hospitalFeeItems.add(hospitalFeeItem3);
		HospitalFeeItem hospitalFeeItem4 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem2, hospitalFeeItem4);
		//处方明细唯一编号
		hospitalFeeItem4.setMedicalFeeSerialNumber(orderNo+"_4");
		//医院处方明细唯一编号
		hospitalFeeItem4.setHospitalFeeSerialNumber(orderNo+"_4");
		//单价
		hospitalFeeItem4.setUnitPrice("1.95");
		//数量
		hospitalFeeItem4.setQuantity("20");
		//总金额
		hospitalFeeItem4.setTotalAmount("39.00");
		hospitalFeeItems.add(hospitalFeeItem4);
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
