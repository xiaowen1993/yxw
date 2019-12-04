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

public class GS_GSTest_5_3_11陈淑碧 {
	
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
		hospitalFeeItem1.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1.setMedicalFeeType("5");
		
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
		hospitalFeeItem1.setMedicalCatalogName("CT检查项目头颅CT平扫+三维重建");
		hospitalFeeItem1.setMedicalInsurancePaymentType("2");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem1.setUnitPrice("500.00");
		//数量
		hospitalFeeItem1.setQuantity("1");
		//总金额
		hospitalFeeItem1.setTotalAmount("500.00");
		//医嘱执行时间
		hospitalFeeItem1.setOrderExecutedDate("2017-11-20 00:00:00");
		
		hospitalFeeItems.add(hospitalFeeItem1);
		
		
		HospitalFeeItem hospitalFeeItem2 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1, hospitalFeeItem2);
		//处方明细唯一编号
		hospitalFeeItem2.setMedicalFeeSerialNumber(orderNo+"_2");
		//医院处方明细唯一编号
		hospitalFeeItem2.setHospitalFeeSerialNumber(orderNo+"_2");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem2.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem2.setMedicalFeeType("19");
		//社保医疗目录编码
		hospitalFeeItem2.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem2.setMedicalCatalogName("诊查费");
		hospitalFeeItem2.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem2.setUnitPrice("21.00");
		//数量
		hospitalFeeItem2.setQuantity("1");
		//总金额
		hospitalFeeItem2.setTotalAmount("21.00");
		hospitalFeeItems.add(hospitalFeeItem2);
		
		
		HospitalFeeItem hospitalFeeItem1_1 = new HospitalFeeItem();
		//处方明细唯一编号
		hospitalFeeItem1_1.setMedicalFeeSerialNumber(orderNo+"_1");
		//医院处方明细唯一编号
		hospitalFeeItem1_1.setHospitalFeeSerialNumber(orderNo+"_1");
		
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_1.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_1.setMedicalFeeType("1");
		
		//限价标识
		hospitalFeeItem1_1.setLimitedPriceSign("0");
		//自费比例
		hospitalFeeItem1_1.setSelfPaymentRatio("1");
		//合规标识
		hospitalFeeItem1_1.setCompliantSign("1");
		//处方药标识
		hospitalFeeItem1_1.setPrescriptionIdentification("");
		//合规金额
		hospitalFeeItem1_1.setCompliantPrice("");
		
		//社保医疗目录编码
		hospitalFeeItem1_1.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_1.setMedicalCatalogName("氯化钠注射液");
		hospitalFeeItem1_1.setMedicalInsurancePaymentType("1");  //甲乙丙丁-1234
		//单价
		hospitalFeeItem1_1.setUnitPrice("4.75");
		//数量
		hospitalFeeItem1_1.setQuantity("3");
		//总金额
		hospitalFeeItem1_1.setTotalAmount("14.25");
		//医嘱执行时间
		hospitalFeeItem1_1.setOrderExecutedDate("2017-11-21 00:00:00");
		
		hospitalFeeItems.add(hospitalFeeItem1_1);
		
		
		HospitalFeeItem hospitalFeeItem1_2 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_2);
		//处方明细唯一编号
		hospitalFeeItem1_2.setMedicalFeeSerialNumber(orderNo+"_2");
		//医院处方明细唯一编号
		hospitalFeeItem1_2.setHospitalFeeSerialNumber(orderNo+"_2");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_2.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_2.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem1_2.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_2.setMedicalCatalogName("氯化钠注射液");
		hospitalFeeItem1_2.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_2.setUnitPrice("4.75");
		//数量
		hospitalFeeItem1_2.setQuantity("3");
		//总金额
		hospitalFeeItem1_2.setTotalAmount("14.25");
		hospitalFeeItems.add(hospitalFeeItem1_2);
		
		HospitalFeeItem hospitalFeeItem1_3 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_3);
		//处方明细唯一编号
		hospitalFeeItem1_3.setMedicalFeeSerialNumber(orderNo+"_3");
		//医院处方明细唯一编号
		hospitalFeeItem1_3.setHospitalFeeSerialNumber(orderNo+"_3");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_3.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_3.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem1_3.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_3.setMedicalCatalogName("头孢克肟分散片");
		hospitalFeeItem1_3.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_3.setUnitPrice("6.695");
		//数量
		hospitalFeeItem1_3.setQuantity("20");
		//总金额
		hospitalFeeItem1_3.setTotalAmount("133.9");
		hospitalFeeItems.add(hospitalFeeItem1_3);
		
		HospitalFeeItem hospitalFeeItem1_4 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_4);
		//处方明细唯一编号
		hospitalFeeItem1_4.setMedicalFeeSerialNumber(orderNo+"_4");
		//医院处方明细唯一编号
		hospitalFeeItem1_4.setHospitalFeeSerialNumber(orderNo+"_4");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_4.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_4.setMedicalFeeType("1");
		//社保医疗目录编码
		hospitalFeeItem1_4.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_4.setMedicalCatalogName("曲克芦丁脑蛋白水解物注射液");
		hospitalFeeItem1_4.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem1_4.setUnitPrice("47.423");
		//数量
		hospitalFeeItem1_4.setQuantity("15");
		//总金额
		hospitalFeeItem1_4.setTotalAmount("711.35");
		hospitalFeeItems.add(hospitalFeeItem1_4);
		
		HospitalFeeItem hospitalFeeItem1_5 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_5);
		//处方明细唯一编号
		hospitalFeeItem1_5.setMedicalFeeSerialNumber(orderNo+"_5");
		//医院处方明细唯一编号
		hospitalFeeItem1_5.setHospitalFeeSerialNumber(orderNo+"_5");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_5.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_5.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem1_5.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_5.setMedicalCatalogName("骨疏康颗粒（无糖型）");
		hospitalFeeItem1_5.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem1_5.setUnitPrice("4.397");
		//数量
		hospitalFeeItem1_5.setQuantity("18");
		//总金额
		hospitalFeeItem1_5.setTotalAmount("79.15");
		hospitalFeeItems.add(hospitalFeeItem1_5);
		
		HospitalFeeItem hospitalFeeItem1_6 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_6);
		//处方明细唯一编号
		hospitalFeeItem1_6.setMedicalFeeSerialNumber(orderNo+"_6");
		//医院处方明细唯一编号
		hospitalFeeItem1_6.setHospitalFeeSerialNumber(orderNo+"_6");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_6.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_6.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem1_6.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_6.setMedicalCatalogName("醒脑静注射液");
		hospitalFeeItem1_6.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_6.setUnitPrice("69.37");
		//数量
		hospitalFeeItem1_6.setQuantity("6");
		//总金额
		hospitalFeeItem1_6.setTotalAmount("416.22");
		hospitalFeeItems.add(hospitalFeeItem1_6);
		
		HospitalFeeItem hospitalFeeItem1_7 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_7);
		//处方明细唯一编号
		hospitalFeeItem1_7.setMedicalFeeSerialNumber(orderNo+"_7");
		//医院处方明细唯一编号
		hospitalFeeItem1_7.setHospitalFeeSerialNumber(orderNo+"_7");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_7.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_7.setMedicalFeeType("2");
		//社保医疗目录编码
		hospitalFeeItem1_7.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_7.setMedicalCatalogName("大活络胶囊");
		hospitalFeeItem1_7.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_7.setUnitPrice("0.815");
		//数量
		hospitalFeeItem1_7.setQuantity("72");
		//总金额
		hospitalFeeItem1_7.setTotalAmount("58.68");
		hospitalFeeItems.add(hospitalFeeItem1_7);
		
		HospitalFeeItem hospitalFeeItem1_8 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_8);
		//处方明细唯一编号
		hospitalFeeItem1_8.setMedicalFeeSerialNumber(orderNo+"_8");
		//医院处方明细唯一编号
		hospitalFeeItem1_8.setHospitalFeeSerialNumber(orderNo+"_8");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_8.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_8.setMedicalFeeType("19");
		//社保医疗目录编码
		hospitalFeeItem1_8.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_8.setMedicalCatalogName("其他处方-门急诊留观诊查费");
		hospitalFeeItem1_8.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_8.setUnitPrice("20.00");
		//数量
		hospitalFeeItem1_8.setQuantity("1");
		//总金额
		hospitalFeeItem1_8.setTotalAmount("20.00");
		hospitalFeeItems.add(hospitalFeeItem1_8);
		
		HospitalFeeItem hospitalFeeItem1_9 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_9);
		//处方明细唯一编号
		hospitalFeeItem1_9.setMedicalFeeSerialNumber(orderNo+"_9");
		//医院处方明细唯一编号
		hospitalFeeItem1_9.setHospitalFeeSerialNumber(orderNo+"_9");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_9.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_9.setMedicalFeeType("8");
		//社保医疗目录编码
		hospitalFeeItem1_9.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_9.setMedicalCatalogName("门诊静脉输液");
		hospitalFeeItem1_9.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_9.setUnitPrice("15.54");
		//数量
		hospitalFeeItem1_9.setQuantity("3");
		//总金额
		hospitalFeeItem1_9.setTotalAmount("46.62");
		hospitalFeeItems.add(hospitalFeeItem1_9);
		
		HospitalFeeItem hospitalFeeItem1_10 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_10);
		//处方明细唯一编号
		hospitalFeeItem1_10.setMedicalFeeSerialNumber(orderNo+"_10");
		//医院处方明细唯一编号
		hospitalFeeItem1_10.setHospitalFeeSerialNumber(orderNo+"_10");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_10.setMedicalClassification("2");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_10.setMedicalFeeType("8");
		//社保医疗目录编码
		hospitalFeeItem1_10.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_10.setMedicalCatalogName("静脉输液");
		hospitalFeeItem1_10.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_10.setUnitPrice("1.30");
		//数量
		hospitalFeeItem1_10.setQuantity("3");
		//总金额
		hospitalFeeItem1_10.setTotalAmount("3.90");
		hospitalFeeItems.add(hospitalFeeItem1_10);
		
		HospitalFeeItem hospitalFeeItem1_11 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_11);
		//处方明细唯一编号
		hospitalFeeItem1_11.setMedicalFeeSerialNumber(orderNo+"_11");
		//医院处方明细唯一编号
		hospitalFeeItem1_11.setHospitalFeeSerialNumber(orderNo+"_11");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_11.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_11.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem1_11.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_11.setMedicalCatalogName("一次性注射器");
		hospitalFeeItem1_11.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_11.setUnitPrice("0.73");
		//数量
		hospitalFeeItem1_11.setQuantity("3");
		//总金额
		hospitalFeeItem1_11.setTotalAmount("2.19");
		hospitalFeeItems.add(hospitalFeeItem1_11);
		
		HospitalFeeItem hospitalFeeItem1_12 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_12);
		//处方明细唯一编号
		hospitalFeeItem1_12.setMedicalFeeSerialNumber(orderNo+"_12");
		//医院处方明细唯一编号
		hospitalFeeItem1_12.setHospitalFeeSerialNumber(orderNo+"_12");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_12.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_12.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem1_12.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_12.setMedicalCatalogName("超低密度聚乙烯输液器");
		hospitalFeeItem1_12.setMedicalInsurancePaymentType("2");
		//单价
		hospitalFeeItem1_12.setUnitPrice("12.98");
		//数量
		hospitalFeeItem1_12.setQuantity("3");
		//总金额
		hospitalFeeItem1_12.setTotalAmount("38.94");
		hospitalFeeItems.add(hospitalFeeItem1_12);
		
		HospitalFeeItem hospitalFeeItem1_13 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_13);
		//处方明细唯一编号
		hospitalFeeItem1_13.setMedicalFeeSerialNumber(orderNo+"_13");
		//医院处方明细唯一编号
		hospitalFeeItem1_13.setHospitalFeeSerialNumber(orderNo+"_13");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_13.setMedicalClassification("1");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_13.setMedicalFeeType("21");
		//社保医疗目录编码
		hospitalFeeItem1_13.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_13.setMedicalCatalogName("一次性注射器");
		hospitalFeeItem1_13.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_13.setUnitPrice("0.73");
		//数量
		hospitalFeeItem1_13.setQuantity("3");
		//总金额
		hospitalFeeItem1_13.setTotalAmount("2.19");
		hospitalFeeItems.add(hospitalFeeItem1_13);
		
		HospitalFeeItem hospitalFeeItem1_14 = new HospitalFeeItem();
		BeanUtils.copyProperties(hospitalFeeItem1_1, hospitalFeeItem1_14);
		//处方明细唯一编号
		hospitalFeeItem1_14.setMedicalFeeSerialNumber(orderNo+"_14");
		//医院处方明细唯一编号
		hospitalFeeItem1_14.setHospitalFeeSerialNumber(orderNo+"_14");
		//医疗项目类别 1药品 2诊疗项目 3医疗服务设施 9其他
		hospitalFeeItem1_14.setMedicalClassification("3");
		//医疗费用类别
		//1西药 2中成药 3中草药 4常规检查 5CT 6核磁 7B超 8治疗费 9化验 10手术费 11输氧费 12放射 13输血费 14注射费 15透析 16化疗 17住院费用 18材料费 19其他费用 20生活护理费 21医疗耗材
		hospitalFeeItem1_14.setMedicalFeeType("19");
		//社保医疗目录编码
		hospitalFeeItem1_14.setMedicalCatalogCode("NA");
		//社保医疗目录名称
		hospitalFeeItem1_14.setMedicalCatalogName("其他处方-门急诊观察床位费");
		hospitalFeeItem1_14.setMedicalInsurancePaymentType("1");
		//单价
		hospitalFeeItem1_14.setUnitPrice("17.00");
		//数量
		hospitalFeeItem1_14.setQuantity("1");
		//总金额
		hospitalFeeItem1_14.setTotalAmount("17.00");
		hospitalFeeItems.add(hospitalFeeItem1_14);
		
		hospitalFeeDetailInfo.setHospitalFeeItems(hospitalFeeItems);

		body.put("HospitalFeeDetailInfo", hospitalFeeDetailInfo);
		
		
		root.put("head", hospitalFeeDetailInfo.getHead());
		root.put("body", body);
		requestJson.put("root", root);
		
		System.out.println(JSONObject.toJSONString(requestJson, true));
		
		return requestJson.toJSONString();
	}

}
