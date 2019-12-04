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

public class GS_GSTest_5_3_11尹皓诚 {
	
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
		
		String name = "尹皓诚";
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		String genderCode = "1";
		String medicalCard = "6794375";
		String idNo = "44010520150123421";
		String orderNo = "Y147503411122783824975";
		//String mobilephone = "13622235229";
		//String address = "广东省广州市海珠区同福中路兆和新街4号504";
		
		
		String hospitalCode = "4401008000000044";
		//String hospitalName = "广州红十字会医院";
		
		JSONObject body = new JSONObject();
		HospitalFeeDetailInfo hospitalFeeDetailInfo = new HospitalFeeDetailInfo();
		//医疗机构代码
		hospitalFeeDetailInfo.setMedicalInstituteCode(hospitalCode);
		//归属社保地区代码
		hospitalFeeDetailInfo.setBelongInstituteAreaCode("440100");  //120000 440100
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
		hospitalFeeItem1.setHospitalFeeSerialNumber(hospitalFeeItem1.getMedicalFeeSerialNumber());
		
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1.setMedicalFeeType("1");
		
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
		hospitalFeeItem1.setMedicalCatalogName("医用胶（北京康派特）");
		hospitalFeeItem1.setMedicalInsurancePaymentType("2");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem1.setUnitPrice("484.00");
		//数量
		hospitalFeeItem1.setQuantity("1");
		//总金额
		hospitalFeeItem1.setTotalAmount("484.00");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2018-01-14 00:00:00");
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		
		HospitalFeeItem hospitalFeeItem2 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem2);
		//处方明细唯一编号
		hospitalFeeItem2.setMedicalFeeSerialNumber(orderNo+"_2");
		//医院处方明细唯一编号
		hospitalFeeItem2.setHospitalFeeSerialNumber(hospitalFeeItem2.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem2.setMedicalCatalogName("小清创");
		//单价
		hospitalFeeItem2.setUnitPrice("51.80");
		//数量
		hospitalFeeItem2.setQuantity("1");
		//总金额
		hospitalFeeItem2.setTotalAmount("51.80");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem2.setMedicalFeeType("8");
		hospitalFeeItem2.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem2.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem2.setMedicalClassification("2");
		hospitalFeeItems.add(hospitalFeeItem2);
		
		HospitalFeeItem hospitalFeeItem3 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem3);
		//处方明细唯一编号
		hospitalFeeItem3.setMedicalFeeSerialNumber(orderNo+"_3");
		//医院处方明细唯一编号
		hospitalFeeItem3.setHospitalFeeSerialNumber(hospitalFeeItem3.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem3.setMedicalCatalogName("一次性注射器");
		//单价
		hospitalFeeItem3.setUnitPrice("0.46");
		//数量
		hospitalFeeItem3.setQuantity("1");
		//总金额
		hospitalFeeItem3.setTotalAmount("0.46");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem3.setMedicalFeeType("21");
		hospitalFeeItem3.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem3.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem3.setMedicalClassification("1");
		hospitalFeeItems.add(hospitalFeeItem3);
		
		HospitalFeeItem hospitalFeeItem4 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem4);
		//处方明细唯一编号
		hospitalFeeItem4.setMedicalFeeSerialNumber(orderNo+"_4");
		//医院处方明细唯一编号
		hospitalFeeItem4.setHospitalFeeSerialNumber(hospitalFeeItem4.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem4.setMedicalCatalogName("肌肉注射");
		//单价
		hospitalFeeItem4.setUnitPrice("1.94");
		//数量
		hospitalFeeItem4.setQuantity("1");
		//总金额
		hospitalFeeItem4.setTotalAmount("1.94");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem4.setMedicalFeeType("8");
		hospitalFeeItem4.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem4.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem4.setMedicalClassification("2");
		hospitalFeeItems.add(hospitalFeeItem4);
		
		HospitalFeeItem hospitalFeeItem5 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem5);
		//处方明细唯一编号
		hospitalFeeItem5.setMedicalFeeSerialNumber(orderNo+"_5");
		//医院处方明细唯一编号
		hospitalFeeItem5.setHospitalFeeSerialNumber(hospitalFeeItem5.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem5.setMedicalCatalogName("皮肤注射");
		//单价
		hospitalFeeItem5.setUnitPrice("1.94");
		//数量
		hospitalFeeItem5.setQuantity("1");
		//总金额
		hospitalFeeItem5.setTotalAmount("1.94");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem5.setMedicalFeeType("8");
		hospitalFeeItem5.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem5.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem5.setMedicalClassification("2");
		hospitalFeeItems.add(hospitalFeeItem5);
		
		HospitalFeeItem hospitalFeeItem6 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem6);
		//处方明细唯一编号
		hospitalFeeItem6.setMedicalFeeSerialNumber(orderNo+"_6");
		//医院处方明细唯一编号
		hospitalFeeItem6.setHospitalFeeSerialNumber(hospitalFeeItem6.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem6.setMedicalCatalogName("一次性注射器");
		//单价
		hospitalFeeItem6.setUnitPrice("0.33");
		//数量
		hospitalFeeItem6.setQuantity("1");
		//总金额
		hospitalFeeItem6.setTotalAmount("0.33");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem6.setMedicalFeeType("21");
		hospitalFeeItem6.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem6.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem6.setMedicalClassification("1");
		hospitalFeeItems.add(hospitalFeeItem6);
		
		HospitalFeeItem hospitalFeeItem7 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem7);
		//处方明细唯一编号
		hospitalFeeItem7.setMedicalFeeSerialNumber(orderNo+"_7");
		//医院处方明细唯一编号
		hospitalFeeItem7.setHospitalFeeSerialNumber(hospitalFeeItem7.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem7.setMedicalCatalogName("CT 平扫");
		//单价
		hospitalFeeItem7.setUnitPrice("404.00");
		//数量
		hospitalFeeItem7.setQuantity("1");
		//总金额
		hospitalFeeItem7.setTotalAmount("404.00");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem7.setMedicalFeeType("5");
		hospitalFeeItem7.setMedicalInsurancePaymentType("2");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem7.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem7.setMedicalClassification("2");
		hospitalFeeItems.add(hospitalFeeItem7);
		
		HospitalFeeItem hospitalFeeItem8 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem8);
		//处方明细唯一编号
		hospitalFeeItem8.setMedicalFeeSerialNumber(orderNo+"_8");
		//医院处方明细唯一编号
		hospitalFeeItem8.setHospitalFeeSerialNumber(hospitalFeeItem8.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem8.setMedicalCatalogName("氯化钠注射液");
		//单价
		hospitalFeeItem8.setUnitPrice("1.26");
		//数量
		hospitalFeeItem8.setQuantity("1");
		//总金额
		hospitalFeeItem8.setTotalAmount("1.26");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem8.setMedicalFeeType("1");
		hospitalFeeItem8.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem8.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem8.setMedicalClassification("1");
		hospitalFeeItems.add(hospitalFeeItem8);
		
		HospitalFeeItem hospitalFeeItem9 = new HospitalFeeItem();
		org.springframework.beans.BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem9);
		//处方明细唯一编号
		hospitalFeeItem9.setMedicalFeeSerialNumber(orderNo+"_9");
		//医院处方明细唯一编号
		hospitalFeeItem9.setHospitalFeeSerialNumber(hospitalFeeItem9.getMedicalFeeSerialNumber());
		//社保医疗目录名称
		hospitalFeeItem9.setMedicalCatalogName("破伤风抗毒素注射液");
		//单价
		hospitalFeeItem9.setUnitPrice("2.736");
		//数量
		hospitalFeeItem9.setQuantity("1");
		//总金额
		hospitalFeeItem9.setTotalAmount("2.736");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem9.setMedicalFeeType("1");
		hospitalFeeItem9.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//社保医疗目录编码
		hospitalFeeItem9.setMedicalCatalogCode("NA");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem9.setMedicalClassification("1");
		hospitalFeeItems.add(hospitalFeeItem9);
		
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
