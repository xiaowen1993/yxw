package com.yxw.constants;

public class Cores {
	/*--------------------------------- 绑卡信息 ---------------------------------*/
	public final static String CORE_EH_CARD = "ehCard";
	public final static String CORE_STD_CARD = "stdCard";
	public final static String CORE_APP_CARD = "appCard";
	public final static String CORE_HIS_CARD = "hisCard";
	
	/*--------------------------------- 绑卡原始数据 ---------------------------------*/
	public final static String CORE_CARD_INFOS = "cardInfos";		// --合在一个core中
	
	/*--------------------------------- 门诊订单 ---------------------------------*/
	public final static String CORE_EH_CLINIC = "ehClinic";
	public final static String CORE_STD_CLINIC = "stdClinic";
	public final static String CORE_APP_CLINIC = "appClinic";
	public final static String CORE_HIS_CLINIC = "hisClinic";
	
	/*--------------------------------- 门诊订单原始数据 ---------------------------------*/
	public final static String CORE_CLINIC_INFOS = "clinicInfos";
	
	/*--------------------------------- 门诊部分退费订单 ---------------------------------*/
	public final static String CORE_EH_CLINIC_PART_REFUND = "ehClinicPartRefund";
	public final static String CORE_STD_CLINIC_PART_REFUND = "stdClinicPartRefund";
	
	/*--------------------------------- 挂号订单 ---------------------------------*/
	public final static String CORE_EH_REGISTER = "ehRegister";
	public final static String CORE_STD_REGISTER = "stdRegister";
	public final static String CORE_APP_REGISTER = "appRegister";
	public final static String CORE_HIS_REGISTER = "hisRegister";
	
	/*--------------------------------- 挂号订单原始数据 ---------------------------------*/
	public final static String CORE_REGISTER_INFOS = "regInfos";	// 合成的core，多平台数据，目前暂时不用
	public final static String CORE_STD_REG_INFOS = "stdRegInfos";
	public final static String CORE_HIS_REG_INFOS = "hisRegInfos";
	
	/*--------------------------------- 押金补交订单 ---------------------------------*/
	public final static String CORE_EH_DEPOSIT = "ehDeposit";
	public final static String CORE_STD_DEPOSIT = "stdDeposit";
	public final static String CORE_APP_DEPOSIT = "appDeposit";
	public final static String CORE_HIS_DEPOSIT = "hisDeposit";
	
	/*--------------------------------- 押金订单原始数据 ---------------------------------*/
	public final static String CORE_DEPOSIT_INFOS = "depositInfos";
	
	/*--------------------------------- 押金部分退费订单 ---------------------------------*/
	public final static String CORE_EH_DEPOSIT_PART_REFUND = "ehDepositPartRefund";
	public final static String CORE_STD_DEPOSIT_PART_REFUND = "stdDepositPartRefund";
	
	/*--------------------------------- 统计 ---------------------------------*/
	// 绑卡统计
	public final static String CORE_STATS_CARD = "statsCard";
	// 挂号统计
	public final static String CORE_STATS_REGISTER = "statsRegister";
	// 门诊统计
	public final static String CORE_STATS_CLINIC = "statsClinic";
	// 押金统计
	public final static String CORE_STATS_DEPOSIT = "statsDeposit";
	// 订单统计 -- 暂无
	public final static String CORE_STATS_ORDER = "statsOrder";
	// 关注数统计
	public final static String CORE_STATS_SUBSCRIBE = "statsSubscribe";
	// 号码归属地统计
	public final static String CORE_STATS_ATTRIBUTION = "statsAttribution";
	// 部门统计
	public final static String CORE_STATS_DEPT = "statsDept";
	// 性别统计
	public final static String CORE_STATS_GENDER = "statsGender";
	// 年龄段统计
	public final static String CORE_STATS_AGEGROUP = "statsAgeGroup";
	
	/*--------------------------------- 关注 ---------------------------------*/
	public final static String CORE_STD_SUBSCRIBE = "stdSubscribe";
	public final static String CORE_HIS_SUBSCRIBE = "hisSubscribe";
	
	/*--------------------------------- 关注 ---------------------------------*/
	public final static String CORE_ATTRIBUTION = "phoneNumAttribution";
}
