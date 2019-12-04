package test.GS201802;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeDetailInfo;
import com.yxw.insurance.chinalife.interfaces.entity.request.HospitalFeeItem;
import com.yxw.insurance.chinalife.interfaces.entity.response.HospitalFeeDetailInfoResponse;
import com.yxw.insurance.chinalife.interfaces.utils.WebServiceUtil;
import com.yxw.utils.DateUtils;
import com.yxw.utils.IdCardUtils;

public class GS_GSTest_5_3_11崔福强 {
	
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
		
		String name = "崔福强";
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		String genderCode = "1";
		String medicalCard = "2001898335";
		String idNo = "50024219840516613X";
		String orderNo = "Y147503411122783824978";
		
		String hospitalCode = "4401008000000250";
		//String hospitalName = "增城区人民医院";
		
		JSONObject body = new JSONObject();
		HospitalFeeDetailInfo hospitalFeeDetailInfo = new HospitalFeeDetailInfo();
		//医疗机构代码
		//hospitalFeeDetailInfo.setMedicalInstituteCode("gzhszhyy");
		hospitalFeeDetailInfo.setMedicalInstituteCode(hospitalCode);
		//归属社保地区代码
		hospitalFeeDetailInfo.setBelongInstituteAreaCode("440100");  //120000 440100
		//hospitalFeeDetailInfo.setBelongInstituteAreaCode("120000");  //120000 440100
		//个人唯一识别码
		hospitalFeeDetailInfo.setPersonGUID(medicalCard);
		//社会保障号/农合号
		hospitalFeeDetailInfo.setSocialInsuranceNumber(idNo);
		//就医唯一识别码
		hospitalFeeDetailInfo.setMedicalGUID(orderNo);
		//姓名
		hospitalFeeDetailInfo.setName(name);
		//性别代码
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		hospitalFeeDetailInfo.setGenderCode(genderCode);
		//证件类型
		hospitalFeeDetailInfo.setCredentialType("I");
		//证件号码
		hospitalFeeDetailInfo.setCredentialNum(idNo);
		//出生日期
		hospitalFeeDetailInfo.setBirthday(DateUtils.formatDate(IdCardUtils.getBirthByIdCard(idNo), "yyyyMMdd", "yyyy-MM-dd"));
		
		List<HospitalFeeItem> hospitalFeeItems = new ArrayList<>();
		HospitalFeeItem hospitalFeeItem1 = new HospitalFeeItem();
		//处方明细唯一编号
		hospitalFeeItem1.setMedicalFeeSerialNumber(orderNo+"_1");
		//医院处方明细唯一编号
		hospitalFeeItem1.setHospitalFeeSerialNumber(orderNo+"_1");
		
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1.setMedicalFeeType("6");
		
		//限价标识
		hospitalFeeItem1.setLimitedPriceSign("0");
		//自费比例
		hospitalFeeItem1.setSelfPaymentRatio("1");
		//合规标识
		hospitalFeeItem1.setCompliantSign("1");
		//处方药标识
		hospitalFeeItem1.setPrescriptionIdentification("");
		//合规金额
		hospitalFeeItem1.setCompliantPrice("");
		
		//社保医疗目录编码
		hospitalFeeItem1.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1.setMedicalCatalogName("磁共振平扫");
		hospitalFeeItem1.setMedicalInsurancePaymentType("2");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem1.setUnitPrice("672.00");
		//数量
		hospitalFeeItem1.setQuantity("1");
		//总金额
		hospitalFeeItem1.setTotalAmount("672.00");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2017-12-23 00:00:00");
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		
//		HospitalFeeItem hospitalFeeItem2 = new HospitalFeeItem();
//		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem2);
//		//处方明细唯一编号
//		hospitalFeeItem2.setMedicalFeeSerialNumber(orderNo+"_2");
//		//医院处方明细唯一编号
//		hospitalFeeItem2.setHospitalFeeSerialNumber(orderNo+"_2");
//		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
//		hospitalFeeItem2.setMedicalClassification("2");
//		//医疗费用类别
//		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
//		hospitalFeeItem2.setMedicalFeeType("19");
//		//社保医疗目录编码
//		hospitalFeeItem2.setMedicalCatalogCode("NA");
//		//社保医疗目录名称
//		hospitalFeeItem2.setMedicalCatalogName("诊查费");
//		hospitalFeeItem2.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
//		//单价
//		hospitalFeeItem2.setUnitPrice("21.00");
//		//数量
//		hospitalFeeItem2.setQuantity("1");
//		//总金额
//		hospitalFeeItem2.setTotalAmount("21.00");
//		hospitalFeeItems.add(hospitalFeeItem2);
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
