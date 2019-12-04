package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleRegister
 * @Statement: <p>挂号规则实体类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-1
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleRegister extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2366986579993088391L;

	/**
	 * 关联医院id
	 */
	private String hospitalId;

	/**
	 * 是否有分院  1：有  0：没有
	 */
	private Integer isHasBranch;

	/**
	 * 是否有二级科室 1：有   0 ：没有
	 */
	private Integer isHasGradeTwoDept;

	/**
	 * 是否有当班挂号
	 */
	private Integer isHasOnDutyReg;

	/**
	 * 取值，0：都不支持; 1: 都支持; 2:支持当班 ; 3:支持预约;
	 * -- 是否有搜素医生   1：有   0 ：没有(作废)
	 */
	private Integer isHasSearChDoctor;

	/**
	 * 是否有预约挂号    1：有   0 ：没有
	 */
	private Integer isHasAppointmentReg;

	/**
	 * 预约挂号日历显示天数  1：7天  2：15天 3：30天
	 */
	private Integer calendarDaysType;

	/**
	 * 号源的缓存天数
	 */
	private Integer sourceCacheDays;

	/**
	 * 在线支付控制  :1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 */
	private Integer onlinePaymentControl;

	/**
	 * 当班挂号是否支持退费   0：不支持    1：支持
	 */
	private Integer isSupportRefundOnDuty;

	/**
	 * 当班挂号开始时间点
	 */
	private Date onDutyRegStartTime;

	/**
	 * 当班挂号截止时间点
	 */
	private Date onDutyRegEndTime;

	/**
	 * 允许预约明天号的截止时间点
	 */
	private Date appointmentTomorrowEndTime;

	/**
	 * 挂号超时支付时间  单位分钟
	 */
	private Integer paymentTimeOutTime;

	/**
	 * 每个人每天允许挂同一医生号最大次数
	 */
	private Integer regMaximumSameDoctor;

	/**
	 * 每个人每天允许最大挂号次数
	 */
	private Integer regMaximumInDay;

	/**
	 * 每个人每周允许挂号次数
	 */
	private Integer regMaximumInWeek;

	/**
	 * 每个人每月允许挂号次数
	 */
	private Integer regMaximumInMonth;

	/**
	 * 每个人每天允许取消挂号次数
	 */
	private Integer regCancelMaxnumInDay;

	/**
	 * 暂不支付模式支付截止时间点
	 */
	private Integer notPaidpayOffTime;

	/**
	 * 暂不支付模式支付截止时间类型
	 * 1、就诊前一天几点
	 * 2、就诊当天几点
	 * 3、就诊时间段开始前几小时
	 * 4、就诊时间段开始前多少分钟
	 * 5、就诊时间段开始的时间
	 * 6、就诊时间段结束的时间
	 * 7、就诊时间结束前多少分钟
	 * 8、不限制
	 */
	private Integer notPaidpayOffType;

	/**
	 * 取消预约挂号截止时间点的计算类型 
	 * 1、就诊前一天几点
	 * 2、就诊当天几点
	 * 3、就诊时间段开始前几小时
	 * 4、就诊时间段开始前多少分钟
	 * 5、就诊开始时间
	 * 6、就诊结束时间
	 * 7、就诊结束前多少分钟
	 * 8、不限制
	 */
	private Integer cancelBespeakCloseType;

	/**
	 * 取消预约挂号截至时间点
	 */
	private Integer cancelBespeakEndTime;

	/**
	 * 取消当班挂号截止类型
	 * 1、就诊当天几点
	 * 2、就诊时间段开始前几小时
	 * 3、就诊时间段开始前多少分钟
	 * 4、就诊时间段开始的时间
	 * 5、就诊时间段结束的时间
	 * 6、就诊时间段结束前多少分钟
	 * 7、不限制
	 */
	private Integer cancelOnDutyCloseType;

	/**
	 * 取消当班挂号的截至时间  类型为1是单位为小时  类型为2是单位为分钟
	 */
	private Integer cancelOnDutyEndTimes;

	/**
	 * 所选日期没有号源信息提示语
	 */
	private String noSourceTipContent;

	/**
	 * 当班挂号温馨提示语
	 */
	private String onDutyRegTipContent;

	/**
	 * 超过当班挂号截止时间点的提示语
	 */
	private String onDutyRegOverTimeTip;

	/**
	 * 超过预约明天号的截止时间点提示语
	 */
	private String bespeakRegOverTimeTip;

	/**
	 * 挂号失败提示语
	 */
	private String regFailedTip;

	/**
	 * 可享受医院挂号优惠提示语
	 */
	private String regDiscountTip;

	/**
	 * 超过每天挂同一医生号次数提示语
	 */
	private String overMaximumSameDoctorTip;

	/**
	 * 超过每天允许挂号次数提示语
	 */
	private String overMaximumInDayTip;

	/**
	 * 超过每周允许挂号次数提示语
	 */
	private String overMaximumInWeekTip;

	/**
	 * 超过每月允许挂号次数提示语
	 */
	private String overMaximumInMonthTip;

	/**
	 * 超过每天允许取消挂号次数提示语
	 */
	private String overCancelMaxnumInDayTip;

	/**
	 * 超过取消预约挂号截止时间点提示语
	 */
	private String cancelBespeakTimeOutTip;

	/**
	 * 超过取消当班挂号截止时间点提示语
	 */
	private String cancelOnDutyTimeOutTip;

	/**
	 * 黑名单用户挂号提示语
	 */
	private String blacklistUserRegTip;

	/**
	 * 确认挂号信息（暂不支付）页面提示语
	 */
	private String confirmRegInfoTip;

	/**
	 * 挂号支付超时提示语
	 */
	private String regPayTimeOutTip;

	/**
	 * 挂号成功（已缴费）时提示语
	 */
	private String regSuccessHadPayTip;

	/**
	 * 挂号成功（未缴费）时提示语
	 */
	private String regSuccessNoPayTip;

	/**
	 * 挂号成功（当班挂号）时提示语
	 */
	private String regSuccessOnDutyTip;

	/**
	 * 是否显示病情描述    0不显示    1显示
	 */
	private Integer isViewDiseaseDesc;

	/**
	 * 挂号前温馨提示（确认挂号选择就诊人页面）
	 */
	private String preRegisterWarmTip;

	/**
	 * ------
	 */
	/**
	 * 当班是否走医保流程 1：是  0：否
	 */
	private Integer isBasedOnMedicalInsuranceToday;
	/**
	 * 预约是否走医保流程 1：是  0：否
	 */
	private Integer isBasedOnMedicalInsuranceAppoint;
	/**
	 * 自定义的预约挂号日历显示天数
	 */
	private Integer customCalendarDays;
	/**
	 * 预约支付控制：1-必须在线支付 2-不支付 3-暂不支付
	 */
	private Integer appointmentPaymentControl;
	/**
	 * 预约挂号是否支持退费 0：不支持 1：支持
	 */
	private Integer isSupportRefundAppointment;
	/**
	 * 选择医生页面的样式  样式1 样式2 
	 * */
	private Integer chooseDoctorStyle;
	/**
	 * 每个人每月允许取消挂号次数，超过则无法再挂号
	 */
	private Integer regCancelMaxnumInMonth;
	/**
	 * 达到每月允许取消挂号次数，超过则无法再挂号提示语(挂号时)
	 */
	private String overCancelMaxnumInMonthTip;
	/**
	 * 即将达到每天取消次数上限时提醒信息(取消时)
	 */
	private String willReachCancelMaxnumInDayTip;
	/**
	 * 即将达到每月取消次数上限时提醒信息(取消时)
	 */
	private String willReachCancelMaxnumInMonthTip;
	/**
	 * 已经达到每天取消次数上限时提醒信息(取消时)
	 */
	private String hadReachCancelMaxnumInDayCancelTip;
	/**
	 * 已经达到每月取消次数上限时提醒信息(取消时)
	 */
	private String hadReachCancelMaxnumInMonthCancelTip;
	/**
	 * 是否显示挂号费 0不显示 1显示 默认不显示
	 */
	private Integer isViewRegFee;
	/**
	 * 是否需要显示人群类型 0不显示 1显示
	 */
	private Integer isViewPopulationType;
	/**
	 * 人群类型 本地-1 外地 -2
	 */
	private String populationType;
	/**
	 * 是否需要显示预约类型 0不显示 1显示
	 */
	private Integer isViewAppointmentType;
	/**
	 * 预约类型 一般1 出院后复查 2 社区转诊 3 术后复查 4 产前检查 5
	 */
	private String appointmentType;
	/**
	 * 是否显示剩余号源数【当班】
	 */
	private Integer isViewSourceNum;
	/**
	 * 是否显示剩余号源数【预约】
	 */
	private Integer isViewSourceNumReg;
	/**
	 * 是否查询患者类型
	 */
	private Integer isQueryPatientType;
	/**
	 * 跨平台取号-跨平台天数,-1为无限制
	 */
	private Integer unpaidRegDays;
	/**
	 * 是否允许异常订单退费
	 */
	private Integer isExceptionRefundOrder;
	/**
	 * 排队号提示信息
	 * */
	private String serialNumTip;
	/**
	 * 患者类型查询提示语
	 * */
	private String patientTypeTip;

	/**
	 * 是否显示就诊时间段
	 */
	private Integer showRegPeriod;
	/**
	 * 是否按照医生职称排序
	 * */
	private Integer isOrderByDoctorTitle;
	/**
	 * 职称顺序
	 * */
	private String doctorTitleOrder;
	/**
	 * 取号-未支付
	 * */
	private String takeNoNeedPayTip;
	/**
	 * 取号-未到当天
	 * */
	private String takeNoUntimelyTip;
	/**
	 * 取号详情页温馨提示
	 * */
	private String takeNoDetailTip;
	/**
	 * 确认挂号弹框提示语
	 * */
	private String confirmTipMedicareWechat;
	private String confirmTipMedicareAlipay;
	private String confirmTipSelfPayWechat;
	private String confirmTipSelfPayAlipay;
	/**
	 * 确认挂号是否弹框 0-否 1-是
	 * */
	private Integer isconfirmTipMedicareWechat;
	private Integer isconfirmTipMedicareAlipay;
	private Integer isconfirmTipSelfPayWechat;
	private Integer isconfirmTipSelfPayAlipay;
	/**
	 * his支付异常后退费的延迟时间（分钟）
	 */
	private Integer refundDelayAfterException;

	/**
	 * 未支付情况下his能否查得到订单(2 未支付查不到 1 未支付能查到) 默认是1
	 */
	private Integer canQueryUnpaidRecord;

	/**
	 * 挂号费别名
	 */
	private String regFeeAlias;

	public RuleRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RuleRegister(String hospitalId) {
		super();
		this.hospitalId = hospitalId;
	}

	public RuleRegister(String id, String hospitalId, Integer isHasBranch, Integer isHasGradeTwoDept, Integer calendarDaysType,
			Integer onlinePaymentControl, Integer isSupportRefundOnDuty, Date onDutyRegEndTime, Date appointmentTomorrowEndTime,
			Integer paymentTimeOutTime, Integer regMaximumSameDoctor, Integer regMaximumInDay, Integer regMaximumInWeek,
			Integer regMaximumInMonth, Integer regCancelMaxnumInDay, Integer cancelBespeakCloseType, Integer cancelBespeakEndTime,
			Integer cancelOnDutyCloseType, Integer cancelOnDutyEndTimes, String noSourceTipContent, String onDutyRegTipContent,
			String onDutyRegOverTimeTip, String bespeakRegOverTimeTip, String regFailedTip, String regDiscountTip,
			String overMaximumSameDoctorTip, String overMaximumInDayTip, String overMaximumInWeekTip, String overMaximumInMonthTip,
			String overCancelMaxnumInDayTip, String cancelBespeakTimeOutTip, String cancelOnDutyTimeOutTip, String blacklistUserRegTip,
			String confirmRegInfoTip, String regPayTimeOutTip, String regSuccessHadPayTip, String regSuccessNoPayTip,
			String regSuccessOnDutyTip) {
		super();
		this.id = id;
		this.hospitalId = hospitalId;
		this.isHasBranch = isHasBranch;
		this.isHasGradeTwoDept = isHasGradeTwoDept;
		this.calendarDaysType = calendarDaysType;
		this.onlinePaymentControl = onlinePaymentControl;
		this.isSupportRefundOnDuty = isSupportRefundOnDuty;
		this.onDutyRegEndTime = onDutyRegEndTime;
		this.appointmentTomorrowEndTime = appointmentTomorrowEndTime;
		this.paymentTimeOutTime = paymentTimeOutTime;
		this.regMaximumSameDoctor = regMaximumSameDoctor;
		this.regMaximumInDay = regMaximumInDay;
		this.regMaximumInWeek = regMaximumInWeek;
		this.regMaximumInMonth = regMaximumInMonth;
		this.regCancelMaxnumInDay = regCancelMaxnumInDay;
		this.cancelBespeakCloseType = cancelBespeakCloseType;
		this.cancelBespeakEndTime = cancelBespeakEndTime;
		this.cancelOnDutyCloseType = cancelOnDutyCloseType;
		this.cancelOnDutyEndTimes = cancelOnDutyEndTimes;
		this.noSourceTipContent = noSourceTipContent;
		this.onDutyRegTipContent = onDutyRegTipContent;
		this.onDutyRegOverTimeTip = onDutyRegOverTimeTip;
		this.bespeakRegOverTimeTip = bespeakRegOverTimeTip;
		this.regFailedTip = regFailedTip;
		this.regDiscountTip = regDiscountTip;
		this.overMaximumSameDoctorTip = overMaximumSameDoctorTip;
		this.overMaximumInDayTip = overMaximumInDayTip;
		this.overMaximumInWeekTip = overMaximumInWeekTip;
		this.overMaximumInMonthTip = overMaximumInMonthTip;
		this.overCancelMaxnumInDayTip = overCancelMaxnumInDayTip;
		this.cancelBespeakTimeOutTip = cancelBespeakTimeOutTip;
		this.cancelOnDutyTimeOutTip = cancelOnDutyTimeOutTip;
		this.blacklistUserRegTip = blacklistUserRegTip;
		this.confirmRegInfoTip = confirmRegInfoTip;
		this.regPayTimeOutTip = regPayTimeOutTip;
		this.regSuccessHadPayTip = regSuccessHadPayTip;
		this.regSuccessNoPayTip = regSuccessNoPayTip;
		this.regSuccessOnDutyTip = regSuccessOnDutyTip;
	}

	public RuleRegister(String hospitalId, Integer isHasBranch, Integer isHasGradeTwoDept, Integer isHasOnDutyReg,
			Integer isHasSearChDoctor, Integer isHasAppointmentReg, Integer calendarDaysType, Integer sourceCacheDays,
			Integer onlinePaymentControl, Integer isSupportRefundOnDuty, Date onDutyRegStartTime, Date onDutyRegEndTime,
			Date appointmentTomorrowEndTime, Integer paymentTimeOutTime, Integer regMaximumSameDoctor, Integer regMaximumInDay,
			Integer regMaximumInWeek, Integer regMaximumInMonth, Integer regCancelMaxnumInDay, Integer notPaidpayOffTime,
			Integer notPaidpayOffType, Integer cancelBespeakCloseType, Integer cancelBespeakEndTime, Integer cancelOnDutyCloseType,
			Integer cancelOnDutyEndTimes, String noSourceTipContent, String onDutyRegTipContent, String onDutyRegOverTimeTip,
			String bespeakRegOverTimeTip, String regFailedTip, String regDiscountTip, String overMaximumSameDoctorTip,
			String overMaximumInDayTip, String overMaximumInWeekTip, String overMaximumInMonthTip, String overCancelMaxnumInDayTip,
			String cancelBespeakTimeOutTip, String cancelOnDutyTimeOutTip, String blacklistUserRegTip, String confirmRegInfoTip,
			String regPayTimeOutTip, String regSuccessHadPayTip, String regSuccessNoPayTip, String regSuccessOnDutyTip,
			Integer isViewDiseaseDesc, String preRegisterWarmTip, Integer isBasedOnMedicalInsuranceToday,
			Integer isBasedOnMedicalInsuranceAppoint, Integer customCalendarDays, Integer appointmentPaymentControl,
			Integer isSupportRefundAppointment, Integer chooseDoctorStyle, Integer regCancelMaxnumInMonth,
			String overCancelMaxnumInMonthTip, String willReachCancelMaxnumInDayTip, String willReachCancelMaxnumInMonthTip,
			String hadReachCancelMaxnumInDayCancelTip, String hadReachCancelMaxnumInMonthCancelTip, Integer isViewRegFee,
			Integer isViewPopulationType, String populationType, Integer isViewAppointmentType, String appointmentType,
			Integer isViewSourceNum, Integer isViewSourceNumReg, Integer isQueryPatientType, Integer unpaidRegDays,
			Integer isExceptionRefundOrder, String serialNumTip, String patientTypeTip, Integer showRegPeriod,
			Integer isOrderByDoctorTitle, String doctorTitleOrder, String takeNoNeedPayTip, String takeNoUntimelyTip,
			String takeNoDetailTip, String confirmTipMedicareWechat, String confirmTipMedicareAlipay, String confirmTipSelfPayWechat,
			String confirmTipSelfPayAlipay, Integer isconfirmTipMedicareWechat, Integer isconfirmTipMedicareAlipay,
			Integer isconfirmTipSelfPayWechat, Integer isconfirmTipSelfPayAlipay, Integer refundDelayAfterException,
			Integer canQueryUnpaidRecord) {
		super();
		this.hospitalId = hospitalId;
		this.isHasBranch = isHasBranch;
		this.isHasGradeTwoDept = isHasGradeTwoDept;
		this.isHasOnDutyReg = isHasOnDutyReg;
		this.isHasSearChDoctor = isHasSearChDoctor;
		this.isHasAppointmentReg = isHasAppointmentReg;
		this.calendarDaysType = calendarDaysType;
		this.sourceCacheDays = sourceCacheDays;
		this.onlinePaymentControl = onlinePaymentControl;
		this.isSupportRefundOnDuty = isSupportRefundOnDuty;
		this.onDutyRegStartTime = onDutyRegStartTime;
		this.onDutyRegEndTime = onDutyRegEndTime;
		this.appointmentTomorrowEndTime = appointmentTomorrowEndTime;
		this.paymentTimeOutTime = paymentTimeOutTime;
		this.regMaximumSameDoctor = regMaximumSameDoctor;
		this.regMaximumInDay = regMaximumInDay;
		this.regMaximumInWeek = regMaximumInWeek;
		this.regMaximumInMonth = regMaximumInMonth;
		this.regCancelMaxnumInDay = regCancelMaxnumInDay;
		this.notPaidpayOffTime = notPaidpayOffTime;
		this.notPaidpayOffType = notPaidpayOffType;
		this.cancelBespeakCloseType = cancelBespeakCloseType;
		this.cancelBespeakEndTime = cancelBespeakEndTime;
		this.cancelOnDutyCloseType = cancelOnDutyCloseType;
		this.cancelOnDutyEndTimes = cancelOnDutyEndTimes;
		this.noSourceTipContent = noSourceTipContent;
		this.onDutyRegTipContent = onDutyRegTipContent;
		this.onDutyRegOverTimeTip = onDutyRegOverTimeTip;
		this.bespeakRegOverTimeTip = bespeakRegOverTimeTip;
		this.regFailedTip = regFailedTip;
		this.regDiscountTip = regDiscountTip;
		this.overMaximumSameDoctorTip = overMaximumSameDoctorTip;
		this.overMaximumInDayTip = overMaximumInDayTip;
		this.overMaximumInWeekTip = overMaximumInWeekTip;
		this.overMaximumInMonthTip = overMaximumInMonthTip;
		this.overCancelMaxnumInDayTip = overCancelMaxnumInDayTip;
		this.cancelBespeakTimeOutTip = cancelBespeakTimeOutTip;
		this.cancelOnDutyTimeOutTip = cancelOnDutyTimeOutTip;
		this.blacklistUserRegTip = blacklistUserRegTip;
		this.confirmRegInfoTip = confirmRegInfoTip;
		this.regPayTimeOutTip = regPayTimeOutTip;
		this.regSuccessHadPayTip = regSuccessHadPayTip;
		this.regSuccessNoPayTip = regSuccessNoPayTip;
		this.regSuccessOnDutyTip = regSuccessOnDutyTip;
		this.isViewDiseaseDesc = isViewDiseaseDesc;
		this.preRegisterWarmTip = preRegisterWarmTip;
		this.isBasedOnMedicalInsuranceToday = isBasedOnMedicalInsuranceToday;
		this.isBasedOnMedicalInsuranceAppoint = isBasedOnMedicalInsuranceAppoint;
		this.customCalendarDays = customCalendarDays;
		this.appointmentPaymentControl = appointmentPaymentControl;
		this.isSupportRefundAppointment = isSupportRefundAppointment;
		this.chooseDoctorStyle = chooseDoctorStyle;
		this.regCancelMaxnumInMonth = regCancelMaxnumInMonth;
		this.overCancelMaxnumInMonthTip = overCancelMaxnumInMonthTip;
		this.willReachCancelMaxnumInDayTip = willReachCancelMaxnumInDayTip;
		this.willReachCancelMaxnumInMonthTip = willReachCancelMaxnumInMonthTip;
		this.hadReachCancelMaxnumInDayCancelTip = hadReachCancelMaxnumInDayCancelTip;
		this.hadReachCancelMaxnumInMonthCancelTip = hadReachCancelMaxnumInMonthCancelTip;
		this.isViewRegFee = isViewRegFee;
		this.isViewPopulationType = isViewPopulationType;
		this.populationType = populationType;
		this.isViewAppointmentType = isViewAppointmentType;
		this.appointmentType = appointmentType;
		this.isViewSourceNum = isViewSourceNum;
		this.isViewSourceNumReg = isViewSourceNumReg;
		this.isQueryPatientType = isQueryPatientType;
		this.unpaidRegDays = unpaidRegDays;
		this.isExceptionRefundOrder = isExceptionRefundOrder;
		this.serialNumTip = serialNumTip;
		this.patientTypeTip = patientTypeTip;
		this.showRegPeriod = showRegPeriod;
		this.isOrderByDoctorTitle = isOrderByDoctorTitle;
		this.doctorTitleOrder = doctorTitleOrder;
		this.takeNoNeedPayTip = takeNoNeedPayTip;
		this.takeNoUntimelyTip = takeNoUntimelyTip;
		this.takeNoDetailTip = takeNoDetailTip;
		this.confirmTipMedicareWechat = confirmTipMedicareWechat;
		this.confirmTipMedicareAlipay = confirmTipMedicareAlipay;
		this.confirmTipSelfPayWechat = confirmTipSelfPayWechat;
		this.confirmTipSelfPayAlipay = confirmTipSelfPayAlipay;
		this.isconfirmTipMedicareWechat = isconfirmTipMedicareWechat;
		this.isconfirmTipMedicareAlipay = isconfirmTipMedicareAlipay;
		this.isconfirmTipSelfPayWechat = isconfirmTipSelfPayWechat;
		this.isconfirmTipSelfPayAlipay = isconfirmTipSelfPayAlipay;
		this.refundDelayAfterException = refundDelayAfterException;
		this.canQueryUnpaidRecord = canQueryUnpaidRecord;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * 是否有分院   
	 * @return  1:有        0:没有
	 */
	public Integer getIsHasBranch() {
		return isHasBranch;
	}

	public void setIsHasBranch(Integer isHasBranch) {
		this.isHasBranch = isHasBranch;
	}

	/**
	 * 是否有二级科室 1：有   0 ：没有
	 * @return
	 */
	public Integer getIsHasGradeTwoDept() {
		return isHasGradeTwoDept;
	}

	public void setIsHasGradeTwoDept(Integer isHasGradeTwoDept) {
		this.isHasGradeTwoDept = isHasGradeTwoDept;
	}

	/**
	 * 预约挂号日历显示天数  1：7天  2：15天 3：30天
	 * @return
	 */
	public Integer getCalendarDaysType() {
		return calendarDaysType;
	}

	public void setCalendarDaysType(Integer calendarDaysType) {
		this.calendarDaysType = calendarDaysType;
	}

	/**
	 * 在线支付控制  :1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 * @return
	 */
	public Integer getOnlinePaymentControl() {
		return onlinePaymentControl;
	}

	public void setOnlinePaymentControl(Integer onlinePaymentControl) {
		this.onlinePaymentControl = onlinePaymentControl;
	}

	/**
	 * 当班挂号是否支持退费   0：不支持    1：支持
	 * @return
	 */
	public Integer getIsSupportRefundOnDuty() {
		return isSupportRefundOnDuty;
	}

	public void setIsSupportRefundOnDuty(Integer isSupportRefundOnDuty) {
		this.isSupportRefundOnDuty = isSupportRefundOnDuty;
	}

	/**
	 * 当班挂号允许挂号截止时间点
	 * @return
	 */
	public Date getOnDutyRegEndTime() {
		return onDutyRegEndTime;
	}

	public void setOnDutyRegEndTime(Date onDutyRegEndTime) {
		this.onDutyRegEndTime = onDutyRegEndTime;
	}

	/**
	 * 允许预约明天号的截止时间点
	 * @return
	 */
	public Date getAppointmentTomorrowEndTime() {
		return appointmentTomorrowEndTime;
	}

	public void setAppointmentTomorrowEndTime(Date appointmentTomorrowEndTime) {
		this.appointmentTomorrowEndTime = appointmentTomorrowEndTime;
	}

	/**
	 * 挂号超时支付时间  单位分钟
	 * @return
	 */
	public Integer getPaymentTimeOutTime() {
		return paymentTimeOutTime;
	}

	public void setPaymentTimeOutTime(Integer paymentTimeOutTime) {
		this.paymentTimeOutTime = paymentTimeOutTime;
	}

	/**
	 * 每个人每天允许挂同一医生号最大次数
	 * @return
	 */
	public Integer getRegMaximumSameDoctor() {
		return regMaximumSameDoctor;
	}

	public void setRegMaximumSameDoctor(Integer regMaximumSameDoctor) {
		this.regMaximumSameDoctor = regMaximumSameDoctor;
	}

	/**
	 * 每个人每天允许最大挂号次数
	 * @return
	 */
	public Integer getRegMaximumInDay() {
		return regMaximumInDay;
	}

	public void setRegMaximumInDay(Integer regMaximumInDay) {
		this.regMaximumInDay = regMaximumInDay;
	}

	/**
	 * 每个人每周允许挂号次数
	 * @return
	 */
	public Integer getRegMaximumInWeek() {
		return regMaximumInWeek;
	}

	public void setRegMaximumInWeek(Integer regMaximumInWeek) {
		this.regMaximumInWeek = regMaximumInWeek;
	}

	/**
	 * 每个人每月允许挂号次数
	 * @return
	 */
	public Integer getRegMaximumInMonth() {
		return regMaximumInMonth;
	}

	public void setRegMaximumInMonth(Integer regMaximumInMonth) {
		this.regMaximumInMonth = regMaximumInMonth;
	}

	/**
	 * 每个人每天允许取消挂号次数
	 * @return
	 */
	public Integer getRegCancelMaxnumInDay() {
		return regCancelMaxnumInDay;
	}

	public void setRegCancelMaxnumInDay(Integer regCancelMaxnumInDay) {
		this.regCancelMaxnumInDay = regCancelMaxnumInDay;
	}

	/**
	 * 取消预约挂号截止时间点的计算类型 1：就诊前一天几点  2：就诊当天几点  3：就诊时间段前几小时
	 * @return
	 */
	public Integer getCancelBespeakCloseType() {
		return cancelBespeakCloseType;
	}

	public void setCancelBespeakCloseType(Integer cancelBespeakCloseType) {
		this.cancelBespeakCloseType = cancelBespeakCloseType;
	}

	/**
	 * 取消预约挂号截至时间点
	 * @return
	 */
	public Integer getCancelBespeakEndTime() {
		return cancelBespeakEndTime;
	}

	public void setCancelBespeakEndTime(Integer cancelBespeakEndTime) {
		this.cancelBespeakEndTime = cancelBespeakEndTime;
	}

	/**
	 * 取消当班挂号截止类型  1：就诊时间段前几小时    2：就诊时间段前多少分钟
	 * @return
	 */
	public Integer getCancelOnDutyCloseType() {
		return cancelOnDutyCloseType;
	}

	public void setCancelOnDutyCloseType(Integer cancelOnDutyCloseType) {
		this.cancelOnDutyCloseType = cancelOnDutyCloseType;
	}

	/**
	 * 取消当班挂号的截至时间     cancelOnDutyCloseType为1是单位为小时  类型为2是单位为分钟
	 * @return
	 */
	public Integer getCancelOnDutyEndTimes() {
		return cancelOnDutyEndTimes;
	}

	public void setCancelOnDutyEndTimes(Integer cancelOnDutyEndTimes) {
		this.cancelOnDutyEndTimes = cancelOnDutyEndTimes;
	}

	/**
	 * 所选日期号源已挂完时提示语
	 * @return
	 */
	public String getNoSourceTipContent() {
		return noSourceTipContent;
	}

	public void setNoSourceTipContent(String noSourceTipContent) {
		this.noSourceTipContent = noSourceTipContent == null ? null : noSourceTipContent.trim();
	}

	/**
	 * 当班挂号温馨提示语
	 * @return
	 */
	public String getOnDutyRegTipContent() {
		return onDutyRegTipContent;
	}

	public void setOnDutyRegTipContent(String onDutyRegTipContent) {
		this.onDutyRegTipContent = onDutyRegTipContent == null ? null : onDutyRegTipContent.trim();
	}

	/**
	 * 超过当班挂号截止时间点的提示语
	 * @return
	 */
	public String getOnDutyRegOverTimeTip() {
		return onDutyRegOverTimeTip;
	}

	public void setOnDutyRegOverTimeTip(String onDutyRegOverTimeTip) {
		this.onDutyRegOverTimeTip = onDutyRegOverTimeTip == null ? null : onDutyRegOverTimeTip.trim();
	}

	/**
	 * 超过预约明天号的截止时间点提示语
	 * @return
	 */
	public String getBespeakRegOverTimeTip() {
		return bespeakRegOverTimeTip;
	}

	public void setBespeakRegOverTimeTip(String bespeakRegOverTimeTip) {
		this.bespeakRegOverTimeTip = bespeakRegOverTimeTip == null ? null : bespeakRegOverTimeTip.trim();
	}

	/**
	 * 挂号失败提示语
	 * @return
	 */
	public String getRegFailedTip() {
		return regFailedTip;
	}

	public void setRegFailedTip(String regFailedTip) {
		this.regFailedTip = regFailedTip == null ? null : regFailedTip.trim();
	}

	/**
	 * 可享受医院挂号优惠提示语
	 * @return
	 */
	public String getRegDiscountTip() {
		return regDiscountTip;
	}

	public void setRegDiscountTip(String regDiscountTip) {
		this.regDiscountTip = regDiscountTip == null ? null : regDiscountTip.trim();
	}

	/**
	 * 	超过每天挂同一医生号次数提示语
	 * @return
	 */
	public String getOverMaximumSameDoctorTip() {
		return overMaximumSameDoctorTip;
	}

	public void setOverMaximumSameDoctorTip(String overMaximumSameDoctorTip) {
		this.overMaximumSameDoctorTip = overMaximumSameDoctorTip == null ? null : overMaximumSameDoctorTip.trim();
	}

	/**
	 * 超过每天允许挂号次数提示语
	 * @return
	 */
	public String getOverMaximumInDayTip() {
		return overMaximumInDayTip;
	}

	public void setOverMaximumInDayTip(String overMaximumInDayTip) {
		this.overMaximumInDayTip = overMaximumInDayTip == null ? null : overMaximumInDayTip.trim();
	}

	/**
	 * 超过每周允许挂号次数提示语
	 * @return
	 */
	public String getOverMaximumInWeekTip() {
		return overMaximumInWeekTip;
	}

	public void setOverMaximumInWeekTip(String overMaximumInWeekTip) {
		this.overMaximumInWeekTip = overMaximumInWeekTip == null ? null : overMaximumInWeekTip.trim();
	}

	/**
	 * 超过每月允许挂号次数提示语
	 * @return
	 */
	public String getOverMaximumInMonthTip() {
		return overMaximumInMonthTip;
	}

	public void setOverMaximumInMonthTip(String overMaximumInMonthTip) {
		this.overMaximumInMonthTip = overMaximumInMonthTip == null ? null : overMaximumInMonthTip.trim();
	}

	/**
	 * 超过每天允许取消挂号次数提示语
	 * @return
	 */
	public String getOverCancelMaxnumInDayTip() {
		return overCancelMaxnumInDayTip;
	}

	public void setOverCancelMaxnumInDayTip(String overCancelMaxnumInDayTip) {
		this.overCancelMaxnumInDayTip = overCancelMaxnumInDayTip == null ? null : overCancelMaxnumInDayTip.trim();
	}

	/**
	 * 超过取消预约挂号截止时间点提示语
	 * @return
	 */
	public String getCancelBespeakTimeOutTip() {
		return cancelBespeakTimeOutTip;
	}

	public void setCancelBespeakTimeOutTip(String cancelBespeakTimeOutTip) {
		this.cancelBespeakTimeOutTip = cancelBespeakTimeOutTip == null ? null : cancelBespeakTimeOutTip.trim();
	}

	/**
	 * 超过取消当班挂号截止时间点提示语
	 * @return
	 */
	public String getCancelOnDutyTimeOutTip() {
		return cancelOnDutyTimeOutTip;
	}

	public void setCancelOnDutyTimeOutTip(String cancelOnDutyTimeOutTip) {
		this.cancelOnDutyTimeOutTip = cancelOnDutyTimeOutTip == null ? null : cancelOnDutyTimeOutTip.trim();
	}

	/**
	 * 黑名单用户挂号提示语
	 * @return
	 */
	public String getBlacklistUserRegTip() {
		return blacklistUserRegTip;
	}

	public void setBlacklistUserRegTip(String blacklistUserRegTip) {
		this.blacklistUserRegTip = blacklistUserRegTip == null ? null : blacklistUserRegTip.trim();
	}

	/**
	 * 确认挂号信息（暂不支付）页面提示语
	 * @return
	 */
	public String getConfirmRegInfoTip() {
		return confirmRegInfoTip;
	}

	public void setConfirmRegInfoTip(String confirmRegInfoTip) {
		this.confirmRegInfoTip = confirmRegInfoTip == null ? null : confirmRegInfoTip.trim();
	}

	/**
	 * 	挂号支付超时提示语
	 * @return
	 */
	public String getRegPayTimeOutTip() {
		return regPayTimeOutTip;
	}

	public void setRegPayTimeOutTip(String regPayTimeOutTip) {
		this.regPayTimeOutTip = regPayTimeOutTip == null ? null : regPayTimeOutTip.trim();
	}

	/**
	 * 挂号成功（已缴费）时提示语
	 * @return
	 */
	public String getRegSuccessHadPayTip() {
		return regSuccessHadPayTip;
	}

	public void setRegSuccessHadPayTip(String regSuccessHadPayTip) {
		this.regSuccessHadPayTip = regSuccessHadPayTip == null ? null : regSuccessHadPayTip.trim();
	}

	/**
	 * 挂号成功（未缴费）时提示语
	 * @return
	 */
	public String getRegSuccessNoPayTip() {
		return regSuccessNoPayTip;
	}

	public void setRegSuccessNoPayTip(String regSuccessNoPayTip) {
		this.regSuccessNoPayTip = regSuccessNoPayTip == null ? null : regSuccessNoPayTip.trim();
	}

	/**
	 * 挂号成功（当班挂号）时提示语
	 * @return
	 */
	public String getRegSuccessOnDutyTip() {
		return regSuccessOnDutyTip;
	}

	public void setRegSuccessOnDutyTip(String regSuccessOnDutyTip) {
		this.regSuccessOnDutyTip = regSuccessOnDutyTip == null ? null : regSuccessOnDutyTip.trim();
	}

	/**
	 * 是否有当班挂号   1 有   0 没有
	 * @return
	 */
	public Integer getIsHasOnDutyReg() {
		return isHasOnDutyReg;
	}

	public void setIsHasOnDutyReg(Integer isHasOnDutyReg) {
		this.isHasOnDutyReg = isHasOnDutyReg;
	}

	/**
	 * 当班挂号开始时间点
	 * @return
	 */
	public Date getOnDutyRegStartTime() {
		return onDutyRegStartTime;
	}

	public void setOnDutyRegStartTime(Date onDutyRegStartTime) {
		this.onDutyRegStartTime = onDutyRegStartTime;
	}

	/** 
	 * 是否显示病情描述   0不显示    1显示
	 * @return
	 */
	public Integer getIsViewDiseaseDesc() {
		return isViewDiseaseDesc;
	}

	public void setIsViewDiseaseDesc(Integer isViewDiseaseDesc) {
		this.isViewDiseaseDesc = isViewDiseaseDesc;
	}

	public Integer getSourceCacheDays() {
		return sourceCacheDays;
	}

	public void setSourceCacheDays(Integer sourceCacheDays) {
		this.sourceCacheDays = sourceCacheDays;
	}

	public Integer getIsHasSearChDoctor() {
		return isHasSearChDoctor;
	}

	public void setIsHasSearChDoctor(Integer isHasSearChDoctor) {
		this.isHasSearChDoctor = isHasSearChDoctor;
	}

	public Integer getIsHasAppointmentReg() {
		return isHasAppointmentReg;
	}

	public void setIsHasAppointmentReg(Integer isHasAppointmentReg) {
		this.isHasAppointmentReg = isHasAppointmentReg;
	}

	public Integer getNotPaidpayOffTime() {
		return notPaidpayOffTime;
	}

	public void setNotPaidpayOffTime(Integer notPaidpayOffTime) {
		this.notPaidpayOffTime = notPaidpayOffTime;
	}

	public Integer getNotPaidpayOffType() {
		return notPaidpayOffType;
	}

	public void setNotPaidpayOffType(Integer notPaidpayOffType) {
		this.notPaidpayOffType = notPaidpayOffType;
	}

	public static RuleRegister getDefaultRule(String hospitalId) {
		RuleRegister rule = new RuleRegister(hospitalId);
		rule.setIsBasedOnMedicalInsuranceToday(0);
		rule.setIsBasedOnMedicalInsuranceAppoint(0);
		rule.setIsHasBranch(1);
		rule.setIsHasGradeTwoDept(1);
		rule.setCalendarDaysType(1);
		rule.setIsHasOnDutyReg(1);
		rule.setIsViewDiseaseDesc(1);
		rule.setCancelBespeakCloseType(1);
		rule.setOnlinePaymentControl(1);
		rule.setIsSupportRefundOnDuty(1);
		rule.setIsSupportRefundAppointment(1);
		rule.setOnDutyRegStartTime(new Time(0, 0, 0));
		rule.setOnDutyRegEndTime(new Time(16, 0, 0));
		rule.setAppointmentTomorrowEndTime(new Time(16, 0, 0));
		rule.setSourceCacheDays(7);
		rule.setIsHasSearChDoctor(1);
		rule.setIsHasAppointmentReg(1);
		rule.setIsViewRegFee(0);
		rule.setIsViewPopulationType(0);
		rule.setIsViewAppointmentType(0);
		rule.setPopulationType("1,2");
		rule.setAppointmentType("1,2,3,4,5");
		rule.setIsViewSourceNum(1);
		rule.setIsViewSourceNumReg(1);
		rule.setIsQueryPatientType(0);
		rule.setUnpaidRegDays(30);
		rule.setChooseDoctorStyle(1);
		rule.setShowRegPeriod(1);
		rule.setIsOrderByDoctorTitle(0);
		rule.setIsconfirmTipMedicareWechat(0);
		rule.setIsconfirmTipMedicareAlipay(0);
		rule.setIsconfirmTipSelfPayAlipay(0);
		rule.setIsconfirmTipSelfPayWechat(0);
		return rule;
	}

	public String getPreRegisterWarmTip() {
		return preRegisterWarmTip;
	}

	public void setPreRegisterWarmTip(String preRegisterWarmTip) {
		this.preRegisterWarmTip = preRegisterWarmTip == null ? null : preRegisterWarmTip.trim();
	}

	public Integer getIsBasedOnMedicalInsuranceToday() {
		return isBasedOnMedicalInsuranceToday;
	}

	public void setIsBasedOnMedicalInsuranceToday(Integer isBasedOnMedicalInsuranceToday) {
		this.isBasedOnMedicalInsuranceToday = isBasedOnMedicalInsuranceToday;
	}

	public Integer getIsBasedOnMedicalInsuranceAppoint() {
		return isBasedOnMedicalInsuranceAppoint;
	}

	public void setIsBasedOnMedicalInsuranceAppoint(Integer isBasedOnMedicalInsuranceAppoint) {
		this.isBasedOnMedicalInsuranceAppoint = isBasedOnMedicalInsuranceAppoint;
	}

	public Integer getCustomCalendarDays() {
		return customCalendarDays;
	}

	public void setCustomCalendarDays(Integer customCalendarDays) {
		this.customCalendarDays = customCalendarDays;
	}

	public Integer getAppointmentPaymentControl() {
		return appointmentPaymentControl;
	}

	public void setAppointmentPaymentControl(Integer appointmentPaymentControl) {
		this.appointmentPaymentControl = appointmentPaymentControl;
	}

	public Integer getIsSupportRefundAppointment() {
		return isSupportRefundAppointment;
	}

	public void setIsSupportRefundAppointment(Integer isSupportRefundAppointment) {
		this.isSupportRefundAppointment = isSupportRefundAppointment;
	}

	public Integer getChooseDoctorStyle() {
		return chooseDoctorStyle;
	}

	public void setChooseDoctorStyle(Integer chooseDoctorStyle) {
		this.chooseDoctorStyle = chooseDoctorStyle;
	}

	public Integer getRegCancelMaxnumInMonth() {
		return regCancelMaxnumInMonth;
	}

	public void setRegCancelMaxnumInMonth(Integer regCancelMaxnumInMonth) {
		this.regCancelMaxnumInMonth = regCancelMaxnumInMonth;
	}

	public String getOverCancelMaxnumInMonthTip() {
		return overCancelMaxnumInMonthTip;
	}

	public void setOverCancelMaxnumInMonthTip(String overCancelMaxnumInMonthTip) {
		this.overCancelMaxnumInMonthTip = overCancelMaxnumInMonthTip;
	}

	public String getWillReachCancelMaxnumInDayTip() {
		return willReachCancelMaxnumInDayTip;
	}

	public void setWillReachCancelMaxnumInDayTip(String willReachCancelMaxnumInDayTip) {
		this.willReachCancelMaxnumInDayTip = willReachCancelMaxnumInDayTip;
	}

	public String getWillReachCancelMaxnumInMonthTip() {
		return willReachCancelMaxnumInMonthTip;
	}

	public void setWillReachCancelMaxnumInMonthTip(String willReachCancelMaxnumInMonthTip) {
		this.willReachCancelMaxnumInMonthTip = willReachCancelMaxnumInMonthTip;
	}

	public String getHadReachCancelMaxnumInDayCancelTip() {
		return hadReachCancelMaxnumInDayCancelTip;
	}

	public void setHadReachCancelMaxnumInDayCancelTip(String hadReachCancelMaxnumInDayCancelTip) {
		this.hadReachCancelMaxnumInDayCancelTip = hadReachCancelMaxnumInDayCancelTip;
	}

	public String getHadReachCancelMaxnumInMonthCancelTip() {
		return hadReachCancelMaxnumInMonthCancelTip;
	}

	public void setHadReachCancelMaxnumInMonthCancelTip(String hadReachCancelMaxnumInMonthCancelTip) {
		this.hadReachCancelMaxnumInMonthCancelTip = hadReachCancelMaxnumInMonthCancelTip;
	}

	public Integer getIsViewRegFee() {
		return isViewRegFee;
	}

	public void setIsViewRegFee(Integer isViewRegFee) {
		this.isViewRegFee = isViewRegFee;
	}

	public Integer getIsViewPopulationType() {
		return isViewPopulationType;
	}

	public void setIsViewPopulationType(Integer isViewPopulationType) {
		this.isViewPopulationType = isViewPopulationType;
	}

	public String getPopulationType() {
		return populationType;
	}

	public void setPopulationType(String populationType) {
		this.populationType = populationType;
	}

	public Integer getIsViewAppointmentType() {
		return isViewAppointmentType;
	}

	public void setIsViewAppointmentType(Integer isViewAppointmentType) {
		this.isViewAppointmentType = isViewAppointmentType;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Integer getIsViewSourceNum() {
		return isViewSourceNum;
	}

	public void setIsViewSourceNum(Integer isViewSourceNum) {
		this.isViewSourceNum = isViewSourceNum;
	}

	public Integer getIsViewSourceNumReg() {
		return isViewSourceNumReg;
	}

	public void setIsViewSourceNumReg(Integer isViewSourceNumReg) {
		this.isViewSourceNumReg = isViewSourceNumReg;
	}

	public Integer getIsQueryPatientType() {
		return isQueryPatientType;
	}

	public void setIsQueryPatientType(Integer isQueryPatientType) {
		this.isQueryPatientType = isQueryPatientType;
	}

	public Integer getUnpaidRegDays() {
		return unpaidRegDays;
	}

	public void setUnpaidRegDays(Integer unpaidRegDays) {
		this.unpaidRegDays = unpaidRegDays;
	}

	public Integer getIsExceptionRefundOrder() {
		return isExceptionRefundOrder;
	}

	public void setIsExceptionRefundOrder(Integer isExceptionRefundOrder) {
		this.isExceptionRefundOrder = isExceptionRefundOrder;
	}

	public String getSerialNumTip() {
		return serialNumTip;
	}

	public void setSerialNumTip(String serialNumTip) {
		this.serialNumTip = serialNumTip;
	}

	public String getPatientTypeTip() {
		return patientTypeTip;
	}

	public void setPatientTypeTip(String patientTypeTip) {
		this.patientTypeTip = patientTypeTip;
	}

	public Integer getShowRegPeriod() {
		return showRegPeriod;
	}

	public void setShowRegPeriod(Integer showRegPeriod) {
		this.showRegPeriod = showRegPeriod;
	}

	public Integer getIsOrderByDoctorTitle() {
		return isOrderByDoctorTitle;
	}

	public void setIsOrderByDoctorTitle(Integer isOrderByDoctorTitle) {
		this.isOrderByDoctorTitle = isOrderByDoctorTitle;
	}

	public String getDoctorTitleOrder() {
		return doctorTitleOrder;
	}

	public void setDoctorTitleOrder(String doctorTitleOrder) {
		this.doctorTitleOrder = doctorTitleOrder;
	}

	public String getTakeNoNeedPayTip() {
		return takeNoNeedPayTip;
	}

	public void setTakeNoNeedPayTip(String takeNoNeedPayTip) {
		this.takeNoNeedPayTip = takeNoNeedPayTip;
	}

	public String getTakeNoUntimelyTip() {
		return takeNoUntimelyTip;
	}

	public void setTakeNoUntimelyTip(String takeNoUntimelyTip) {
		this.takeNoUntimelyTip = takeNoUntimelyTip;
	}

	public String getTakeNoDetailTip() {
		return takeNoDetailTip;
	}

	public void setTakeNoDetailTip(String takeNoDetailTip) {
		this.takeNoDetailTip = takeNoDetailTip;
	}

	public String getConfirmTipMedicareWechat() {
		return confirmTipMedicareWechat;
	}

	public void setConfirmTipMedicareWechat(String confirmTipMedicareWechat) {
		this.confirmTipMedicareWechat = confirmTipMedicareWechat;
	}

	public String getConfirmTipMedicareAlipay() {
		return confirmTipMedicareAlipay;
	}

	public void setConfirmTipMedicareAlipay(String confirmTipMedicareAlipay) {
		this.confirmTipMedicareAlipay = confirmTipMedicareAlipay;
	}

	public String getConfirmTipSelfPayWechat() {
		return confirmTipSelfPayWechat;
	}

	public void setConfirmTipSelfPayWechat(String confirmTipSelfPayWechat) {
		this.confirmTipSelfPayWechat = confirmTipSelfPayWechat;
	}

	public String getConfirmTipSelfPayAlipay() {
		return confirmTipSelfPayAlipay;
	}

	public void setConfirmTipSelfPayAlipay(String confirmTipSelfPayAlipay) {
		this.confirmTipSelfPayAlipay = confirmTipSelfPayAlipay;
	}

	public Integer getIsconfirmTipMedicareWechat() {
		return isconfirmTipMedicareWechat;
	}

	public void setIsconfirmTipMedicareWechat(Integer isconfirmTipMedicareWechat) {
		this.isconfirmTipMedicareWechat = isconfirmTipMedicareWechat;
	}

	public Integer getIsconfirmTipMedicareAlipay() {
		return isconfirmTipMedicareAlipay;
	}

	public void setIsconfirmTipMedicareAlipay(Integer isconfirmTipMedicareAlipay) {
		this.isconfirmTipMedicareAlipay = isconfirmTipMedicareAlipay;
	}

	public Integer getIsconfirmTipSelfPayWechat() {
		return isconfirmTipSelfPayWechat;
	}

	public void setIsconfirmTipSelfPayWechat(Integer isconfirmTipSelfPayWechat) {
		this.isconfirmTipSelfPayWechat = isconfirmTipSelfPayWechat;
	}

	public Integer getIsconfirmTipSelfPayAlipay() {
		return isconfirmTipSelfPayAlipay;
	}

	public void setIsconfirmTipSelfPayAlipay(Integer isconfirmTipSelfPayAlipay) {
		this.isconfirmTipSelfPayAlipay = isconfirmTipSelfPayAlipay;
	}

	public Integer getRefundDelayAfterException() {
		return refundDelayAfterException;
	}

	public void setRefundDelayAfterException(Integer refundDelayAfterException) {
		this.refundDelayAfterException = refundDelayAfterException;
	}

	public Integer getCanQueryUnpaidRecord() {
		return canQueryUnpaidRecord;
	}

	public void setCanQueryUnpaidRecord(Integer canQueryUnpaidRecord) {
		this.canQueryUnpaidRecord = canQueryUnpaidRecord;
	}

	public String getRegFeeAlias() {
		return regFeeAlias;
	}

	public void setRegFeeAlias(String regFeeAlias) {
		this.regFeeAlias = regFeeAlias;
	}

}