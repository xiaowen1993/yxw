package test.GS201802;

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
import com.yxw.utils.DateUtils;
import com.yxw.utils.IdCardUtils;

public class GS_GSTest_5_3_11陈淑碧2 {
	
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
		
		String name = "陈淑碧";
		//0 未知的性别; 1 男性; 2 女性; 9 未说明的性别
		String genderCode = "2";
		String medicalCard = "2001898335";
		String idNo = "511023197302106829";
		String orderNo = "Y147503411122783824979";
		
		String hospitalCode = "4401008000000041";
		//String hospitalName = "广东省第二人民医院";
		
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
		hospitalFeeItem1.setHospitalFeeSerialNumber(orderNo+"_1");
		
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
		hospitalFeeItem1.setMedicalCatalogName("氯化钠注射液");
		hospitalFeeItem1.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem1.setUnitPrice("4.75");
		//数量
		hospitalFeeItem1.setQuantity("3");
		//总金额
		hospitalFeeItem1.setTotalAmount("14.25");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2017-11-21 00:00:00");
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		
		HospitalFeeItem hospitalFeeItem2 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem2);
		//处方明细唯一编号
		hospitalFeeItem2.setMedicalFeeSerialNumber(orderNo+"_2");
		//医院处方明细唯一编号
		hospitalFeeItem2.setHospitalFeeSerialNumber(orderNo+"_2");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem2.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem2.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem2.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem2.setMedicalCatalogName("氯化钠注射液");
		hospitalFeeItem2.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem2.setUnitPrice("4.75");
		//数量
		hospitalFeeItem2.setQuantity("3");
		//总金额
		hospitalFeeItem2.setTotalAmount("14.25");
		hospitalFeeItems.add(hospitalFeeItem2);
		
		HospitalFeeItem hospitalFeeItem3 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem3);
		//处方明细唯一编号
		hospitalFeeItem3.setMedicalFeeSerialNumber(orderNo+"_3");
		//医院处方明细唯一编号
		hospitalFeeItem3.setHospitalFeeSerialNumber(orderNo+"_3");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem3.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem3.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem3.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem3.setMedicalCatalogName("头孢克肟分散片");
		hospitalFeeItem3.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem3.setUnitPrice("6.695");
		//数量
		hospitalFeeItem3.setQuantity("20");
		//总金额
		hospitalFeeItem3.setTotalAmount("133.9");
		hospitalFeeItems.add(hospitalFeeItem3);
		
		HospitalFeeItem hospitalFeeItem4 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem4);
		//处方明细唯一编号
		hospitalFeeItem4.setMedicalFeeSerialNumber(orderNo+"_4");
		//医院处方明细唯一编号
		hospitalFeeItem4.setHospitalFeeSerialNumber(orderNo+"_4");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem4.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem4.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem4.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem4.setMedicalCatalogName("曲克芦丁脑蛋白水解物注射液");
		hospitalFeeItem4.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem4.setUnitPrice("47.423");
		//数量
		hospitalFeeItem4.setQuantity("15");
		//总金额
		hospitalFeeItem4.setTotalAmount("711.35");
		hospitalFeeItems.add(hospitalFeeItem4);
		
		HospitalFeeItem hospitalFeeItem5 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem5);
		//处方明细唯一编号
		hospitalFeeItem5.setMedicalFeeSerialNumber(orderNo+"_5");
		//医院处方明细唯一编号
		hospitalFeeItem5.setHospitalFeeSerialNumber(orderNo+"_5");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem5.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem5.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem5.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem5.setMedicalCatalogName("骨疏康颗粒（无糖型）");
		hospitalFeeItem5.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem5.setUnitPrice("4.397");
		//数量
		hospitalFeeItem5.setQuantity("18");
		//总金额
		hospitalFeeItem5.setTotalAmount("79.15");
		hospitalFeeItems.add(hospitalFeeItem5);
		
		HospitalFeeItem hospitalFeeItem6 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem6);
		//处方明细唯一编号
		hospitalFeeItem6.setMedicalFeeSerialNumber(orderNo+"_6");
		//医院处方明细唯一编号
		hospitalFeeItem6.setHospitalFeeSerialNumber(orderNo+"_6");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem6.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem6.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem6.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem6.setMedicalCatalogName("醒脑静注射液");
		hospitalFeeItem6.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem6.setUnitPrice("69.37");
		//数量
		hospitalFeeItem6.setQuantity("6");
		//总金额
		hospitalFeeItem6.setTotalAmount("416.22");
		hospitalFeeItems.add(hospitalFeeItem6);
		
		HospitalFeeItem hospitalFeeItem7 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem7);
		//处方明细唯一编号
		hospitalFeeItem7.setMedicalFeeSerialNumber(orderNo+"_7");
		//医院处方明细唯一编号
		hospitalFeeItem7.setHospitalFeeSerialNumber(orderNo+"_7");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem7.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem7.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem7.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem7.setMedicalCatalogName("大活络胶囊");
		hospitalFeeItem7.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem7.setUnitPrice("0.815");
		//数量
		hospitalFeeItem7.setQuantity("72");
		//总金额
		hospitalFeeItem7.setTotalAmount("58.68");
		hospitalFeeItems.add(hospitalFeeItem7);
		
		HospitalFeeItem hospitalFeeItem8 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem8);
		//处方明细唯一编号
		hospitalFeeItem8.setMedicalFeeSerialNumber(orderNo+"_8");
		//医院处方明细唯一编号
		hospitalFeeItem8.setHospitalFeeSerialNumber(orderNo+"_8");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem8.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem8.setMedicalFeeType("19");
		//社保医疗目录编码
		hospitalFeeItem8.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem8.setMedicalCatalogName("其他处方-门急诊留观诊查费");
		hospitalFeeItem8.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem8.setUnitPrice("20.00");
		//数量
		hospitalFeeItem8.setQuantity("1");
		//总金额
		hospitalFeeItem8.setTotalAmount("20.00");
		hospitalFeeItems.add(hospitalFeeItem8);
		
		HospitalFeeItem hospitalFeeItem9 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem9);
		//处方明细唯一编号
		hospitalFeeItem9.setMedicalFeeSerialNumber(orderNo+"_9");
		//医院处方明细唯一编号
		hospitalFeeItem9.setHospitalFeeSerialNumber(orderNo+"_9");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem9.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem9.setMedicalFeeType("8");
		//社保医疗目录编码
		hospitalFeeItem9.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem9.setMedicalCatalogName("门诊静脉输液");
		hospitalFeeItem9.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem9.setUnitPrice("15.54");
		//数量
		hospitalFeeItem9.setQuantity("3");
		//总金额
		hospitalFeeItem9.setTotalAmount("46.62");
		hospitalFeeItems.add(hospitalFeeItem9);
		
		HospitalFeeItem hospitalFeeItem10 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem10);
		//处方明细唯一编号
		hospitalFeeItem10.setMedicalFeeSerialNumber(orderNo+"_10");
		//医院处方明细唯一编号
		hospitalFeeItem10.setHospitalFeeSerialNumber(orderNo+"_10");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem10.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem10.setMedicalFeeType("8");
		//社保医疗目录编码
		hospitalFeeItem10.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem10.setMedicalCatalogName("静脉输液");
		hospitalFeeItem10.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem10.setUnitPrice("1.30");
		//数量
		hospitalFeeItem10.setQuantity("3");
		//总金额
		hospitalFeeItem10.setTotalAmount("3.90");
		hospitalFeeItems.add(hospitalFeeItem10);
		
		HospitalFeeItem hospitalFeeItem11 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem11);
		//处方明细唯一编号
		hospitalFeeItem11.setMedicalFeeSerialNumber(orderNo+"_11");
		//医院处方明细唯一编号
		hospitalFeeItem11.setHospitalFeeSerialNumber(orderNo+"_11");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem11.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem11.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem11.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem11.setMedicalCatalogName("一次性注射器");
		hospitalFeeItem11.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem11.setUnitPrice("0.73");
		//数量
		hospitalFeeItem11.setQuantity("3");
		//总金额
		hospitalFeeItem11.setTotalAmount("2.19");
		hospitalFeeItems.add(hospitalFeeItem11);
		
		HospitalFeeItem hospitalFeeItem12 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem12);
		//处方明细唯一编号
		hospitalFeeItem12.setMedicalFeeSerialNumber(orderNo+"_12");
		//医院处方明细唯一编号
		hospitalFeeItem12.setHospitalFeeSerialNumber(orderNo+"_12");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem12.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem12.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem12.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem12.setMedicalCatalogName("超低密度聚乙烯输液器");
		hospitalFeeItem12.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem12.setUnitPrice("12.98");
		//数量
		hospitalFeeItem12.setQuantity("3");
		//总金额
		hospitalFeeItem12.setTotalAmount("38.94");
		hospitalFeeItems.add(hospitalFeeItem12);
		
		HospitalFeeItem hospitalFeeItem13 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem13);
		//处方明细唯一编号
		hospitalFeeItem13.setMedicalFeeSerialNumber(orderNo+"_13");
		//医院处方明细唯一编号
		hospitalFeeItem13.setHospitalFeeSerialNumber(orderNo+"_13");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem13.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem13.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem13.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem13.setMedicalCatalogName("一次性注射器");
		hospitalFeeItem13.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem13.setUnitPrice("0.73");
		//数量
		hospitalFeeItem13.setQuantity("3");
		//总金额
		hospitalFeeItem13.setTotalAmount("2.19");
		hospitalFeeItems.add(hospitalFeeItem13);
		
		HospitalFeeItem hospitalFeeItem14 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem14);
		//处方明细唯一编号
		hospitalFeeItem14.setMedicalFeeSerialNumber(orderNo+"_14");
		//医院处方明细唯一编号
		hospitalFeeItem14.setHospitalFeeSerialNumber(orderNo+"_14");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem14.setMedicalClassification("3");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem14.setMedicalFeeType("19");
		//社保医疗目录编码
		hospitalFeeItem14.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem14.setMedicalCatalogName("其他处方-门急诊观察床位费");
		hospitalFeeItem14.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem14.setUnitPrice("17.00");
		//数量
		hospitalFeeItem14.setQuantity("1");
		//总金额
		hospitalFeeItem14.setTotalAmount("17.00");
		hospitalFeeItems.add(hospitalFeeItem14);
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
