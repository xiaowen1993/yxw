/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-9</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.cache.CommonParamsVo;

/**
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: CommonParamsVo
 * @Statement:
 *             <p>
 *             业务中需要传递的参数vo 包含医院编码 分院编码 appId appCode openId
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-9
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterParamsVo extends CommonParamsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1898767477920068519L;

	/**
	 * 1：为本人挂号 2：为子女挂号 3：为他人挂号
	 */
	private Integer regPersonType;

	/**
	 * 挂号类型,1：预约,2：当天
	 */
	private Integer regType;

	/**
	 * 就诊人已选择科室的编码
	 */
	private String deptCode;
	private String deptName;

	/**
	 * 就诊人已选择医生的编码
	 */
	private String doctorCode;
	private String doctorName;
	/**
	 * 医生职称
	 */
	private String doctorTitle;
	/**
	 * 医生头像url
	 */
	private String doctorPic;

	/**
	 * 医生简介
	 */
	private String doctorIntrodution;

	/**
	 * 就诊人已选择的挂号日期
	 */
	private String selectRegDate;

	/**
	 * 已选择医生的类别 ,0 出诊医生, 1:专家医生,2:专科医生,3:专科
	 */
	private Integer category;

	/**
	 * 分时 时段 1：上午 2：下午 3：晚上
	 */
	private Integer timeFlag;

	/**
	 * 就诊时间段
	 */
	private String doctorBeginTime;
	private String doctorEndTime;

	/**
	 * 挂号费,单位：分
	 */
	private Integer regFee;
	/**
	 * 诊疗费 ,单位：分
	 */
	private Integer treatFee;

	/**
	 * 绑定诊疗卡类型 1：就诊卡、2：医保卡、3：社保卡、4：住院号
	 */
	private Integer cardType;

	/**
	 * 诊疗卡号
	 */
	private String cardNo;

	/**
	 * 患者姓名
	 */
	private String patientName;

	/**
	 * 患者手机号码
	 */
	private String patientMobile;

	/**
	 * 患者性别
	 */
	private Integer patientSex;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 病情描述
	 */
	private String diseaseDesc;

	/**
	 * 标准平台生成的订单号
	 */
	private String orderNo;

	/**
	 * 医院生成的订单号
	 */
	private String hisOrdNo;

	/**
	 * 挂号记录状态
	 */
	private Integer regStatus;

	/**
	 * 订单支付状态
	 */
	private Integer payStatus;

	/**
	 * 挂号方式 1：微信公众号 2：支付宝服务窗 3:健康易
	 */
	private Integer regMode;

	/**
	 * 1:是医生搜索 2:非医生搜索
	 */
	private String isSearchDoctor;

	/**
	 * 是否显示病情描述
	 */
	private Integer isViewDiseaseDesc;

	private Integer onlinePaymentControl;

	/**
	 * 交易方式
	 */
	private Integer tradeMode;

	/**
	 * 暂不支付类型时 是否选择支付
	 */
	private Integer isPay;

	/**
	 * 是否显示交易方式
	 */
	private Integer isShowTradeMode;

	/**
	 * 排班ID
	 */
	private String workId;

	/**
	 * 回调跳转参数
	 */
	private String forward;

	/**新增字段*/
	/**
	 * 当班挂号是否走医保流程 1：有 0：没有
	 */
	private Integer isBasedOnMedicalInsuranceToday;

	/**
	 * 预约挂号是否走医保流程 1：有 0：没有
	 */
	private Integer isBasedOnMedicalInsuranceAppoint;

	/**
	 * 是否显示挂号费
	 */
	private Integer isViewRegFee;

	/**
	 * 人群类型  本地-1  外地 -2
	 */
	private Integer populationType;
	private Integer isViewPopulationType;

	/**
	 * 预约类型 	 
	 * 一般1
	 * 出院后复查 2
	 * 社区转诊 3
	 * 术后复查 4
	 * 产前检查 5
	 */
	private Integer appointmentType;
	private Integer isViewAppointmentType;

	/**
	 * 是否查询患者类型
	 */
	private Integer isQueryPatientType;

	public RegisterParamsVo(CommonParamsVo vo) {
		super(vo.getHospitalCode(), vo.getHospitalId(), vo.getHospitalName(), vo.getBranchHospitalCode(), vo.getBranchHospitalId(), vo
				.getBranchHospitalName(), vo.getAppId(), vo.getAppCode(), vo.getBizCode(), vo.getOpenId());
	}

	public RegisterParamsVo(Integer regType, String deptCode, String deptName, String doctorCode, String doctorName, String doctorTitle,
			String doctorPic, String doctorIntrodution, String selectRegDate, Integer category, Integer timeFlag, String doctorBeginTime,
			String doctorEndTime, Integer regFee, Integer treatFee, Integer cardType, String cardNo, String patientName,
			String diseaseDesc, String orderNo, String hisOrdNo, Integer regStatus, Integer payStatus) {
		super();
		this.regType = regType;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.doctorTitle = doctorTitle;
		this.doctorPic = doctorPic;
		this.doctorIntrodution = doctorIntrodution;
		this.selectRegDate = selectRegDate;
		this.category = category;
		this.timeFlag = timeFlag;
		this.doctorBeginTime = doctorBeginTime;
		this.doctorEndTime = doctorEndTime;
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.patientName = patientName;
		this.diseaseDesc = diseaseDesc;
		this.orderNo = orderNo;
		this.hisOrdNo = hisOrdNo;
		this.regStatus = regStatus;
		this.payStatus = payStatus;
	}

	public RegisterParamsVo(String hospitalCode, String branchHospitalCode, String appId, String appCode, String openId) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.appId = appId;
		this.appCode = appCode;
		this.openId = openId;
	}

	public RegisterParamsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalCard convertMedicalCard() {
		MedicalCard card = new MedicalCard();
		card.setOpenId(this.openId);
		card.setCardNo(this.cardNo);
		card.setBranchCode(branchHospitalCode);
		card.setBranchId(branchHospitalId);
		card.setHospitalCode(hospitalCode);
		card.setHospitalId(hospitalId);
		card.setAppCode(this.appCode);
		return card;
	}

	/**
	 * 转化为挂号记录
	 * 
	 * @return
	 */
	public RegisterRecord convertRegisterRecord(Integer isCompatibleOtherPlatform) {
		RegisterRecord record = new RegisterRecord();
		record.setHospitalCode(this.hospitalCode);
		record.setHospitalId(this.hospitalId);
		record.setHospitalName(hospitalName);
		record.setBranchHospitalCode(this.branchHospitalCode);
		record.setBranchHospitalId(this.branchHospitalId);
		record.setAppId(appId);
		record.setAppCode(appCode);
		record.setWorkId(workId);

		record.setOpenId(this.openId);
		record.setRegType(this.regType);

		record.setDeptCode(this.deptCode);
		record.setDeptName(deptName);
		record.setDoctorCode(this.doctorCode);
		record.setDoctorName(this.doctorName);
		record.setCategory(this.category);
		record.setDoctorTitle(this.doctorTitle);

		record.setRegPersonType(regPersonType);
		record.setCardType(this.cardType);
		record.setCardNo(this.cardNo);
		record.setPatientName(this.patientName);
		record.setPatientSex(this.patientSex);
		record.setPatientMobile(this.patientMobile);
		record.setIdNo(this.idNo);
		record.setIdType(this.idType);
		record.setIsPay(isPay);

		try {
			if (StringUtils.isNotBlank(this.selectRegDate)) {
				record.setScheduleDate(BizConstant.YYYYMMDDE.parse(this.selectRegDate));
			}

			if (StringUtils.isNotBlank(this.doctorBeginTime)) {
				record.setBeginTime(BizConstant.HHMM.parse(this.doctorBeginTime));
				int begin = Integer.valueOf(this.doctorBeginTime.substring(0, 2));

				if (begin < RegisterConstant.ANTE_MERIDIEM_TIME) {
					record.setTimeFlag(1);
				} else if (begin < RegisterConstant.POST_MERIDIEM_TIME) {
					record.setTimeFlag(2);
				} else {
					record.setTimeFlag(3);
				}

			}
			if (StringUtils.isNotBlank(this.doctorEndTime)) {
				record.setEndTime(BizConstant.HHMM.parse(this.doctorEndTime));
			}

			record.setTimeFlag(timeFlag);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int platformMode = ModeTypeUtil.getPlatformModeType(this.appCode);
		record.setPlatformMode(platformMode);

		record.setTradeMode(this.tradeMode);

		if (isCompatibleOtherPlatform.intValue() == RuleConstant.IS_COMPATIBLE_OTHER_PLATFORM_YES) {//兼容医院不修改接口
			/*String tradeModeCode = ModeTypeUtil.getTradeModeCode(record.getTradeMode());
			if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_WECHAT)) {
				record.setPlatformMode(BizConstant.MODE_TYPE_WECHAT_VAL);
				record.setTradeMode(TradeConstant.TRADE_MODE_WECHAT_VAL);
			} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_ALIPAY)) {
				record.setPlatformMode(BizConstant.MODE_TYPE_ALIPAY_VAL);
				record.setTradeMode(TradeConstant.TRADE_MODE_ALIPAY_VAL);
			} else if (StringUtils.equals(tradeModeCode, TradeConstant.TRADE_MODE_UNIONPAY)) {
				// TODO 新增
			}*/
		}

		record.setRegFee(regFee == null ? 0 : regFee);
		record.setTreatFee(treatFee == null ? 0 : treatFee);

		return record;
	}

	/**
	 * 就诊人已选择的科室编码
	 * 
	 * @return
	 */
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 就诊人已选择的医生编码
	 * 
	 * @return
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * 就诊人选择的挂号日期
	 * 
	 * @return
	 */
	public String getSelectRegDate() {
		return selectRegDate;
	}

	public void setSelectRegDate(String selectRegDate) {
		this.selectRegDate = selectRegDate;
	}

	/**
	 * 已选择医生的类别 ,0 出诊医生, 1:专家医生,2:专科医生,3:专科
	 * 
	 * @return
	 */
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	public String getDoctorBeginTime() {
		return doctorBeginTime;
	}

	public void setDoctorBeginTime(String doctorBeginTime) {
		this.doctorBeginTime = doctorBeginTime;
	}

	public String getDoctorEndTime() {
		return doctorEndTime;
	}

	public void setDoctorEndTime(String doctorEndTime) {
		this.doctorEndTime = doctorEndTime;
	}

	public Integer getRegFee() {
		return regFee;
	}

	public void setRegFee(Integer regFee) {
		this.regFee = regFee;
	}

	public Integer getTreatFee() {
		return treatFee;
	}

	public void setTreatFee(Integer treatFee) {
		this.treatFee = treatFee;
	}

	public String getDoctorPic() {
		return doctorPic;
	}

	public void setDoctorPic(String doctorPic) {
		this.doctorPic = doctorPic;
	}

	public String getDoctorIntrodution() {
		return doctorIntrodution;
	}

	public void setDoctorIntrodution(String doctorIntrodution) {
		this.doctorIntrodution = doctorIntrodution;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public String getDiseaseDesc() {
		return diseaseDesc;
	}

	public void setDiseaseDesc(String diseaseDesc) {
		this.diseaseDesc = diseaseDesc;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Integer regStatus) {
		this.regStatus = regStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getHisOrdNo() {
		return hisOrdNo;
	}

	public void setHisOrdNo(String hisOrdNo) {
		this.hisOrdNo = hisOrdNo;
	}

	public Integer getRegMode() {
		return regMode;
	}

	public void setRegMode(Integer regMode) {
		this.regMode = regMode;
	}

	public Integer getRegPersonType() {
		return regPersonType;
	}

	public void setRegPersonType(Integer regPersonType) {
		this.regPersonType = regPersonType;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public Integer getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(Integer patientSex) {
		this.patientSex = patientSex;
	}

	public String getIsSearchDoctor() {
		return isSearchDoctor;
	}

	public void setIsSearchDoctor(String isSearchDoctor) {
		this.isSearchDoctor = isSearchDoctor;
	}

	public Integer getIsViewDiseaseDesc() {
		return isViewDiseaseDesc;
	}

	public void setIsViewDiseaseDesc(Integer isViewDiseaseDesc) {
		this.isViewDiseaseDesc = isViewDiseaseDesc;
	}

	public Integer getOnlinePaymentControl() {
		return onlinePaymentControl;
	}

	public void setOnlinePaymentControl(Integer onlinePaymentControl) {
		this.onlinePaymentControl = onlinePaymentControl;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getIsShowTradeMode() {
		return isShowTradeMode;
	}

	public void setIsShowTradeMode(Integer isShowTradeMode) {
		this.isShowTradeMode = isShowTradeMode;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getForward() throws UnsupportedEncodingException {
		forward =
				"easyhealth/register/confirm/registerInfo?appId=" + this.appId + "&" + "openId=" + this.openId + "&" + "appCode="
						+ this.appCode + "&" + "hospitalId=" + this.hospitalId + "&" + "hospitalCode=" + this.hospitalCode + "&"
						+ "hospitalName=" + this.hospitalName + "&" + "branchHospitalId=" + this.branchHospitalId + "&"
						+ "branchHospitalCode=" + this.branchHospitalCode + "&" + "branchHospitalName=" + this.branchHospitalName + "&"
						+ "deptCode=" + this.deptCode + "&" + "doctorCode=" + this.doctorCode + "&" + "selectRegDate=" + this.selectRegDate
						+ "&" + "category=" + this.category + "&" + "deptName=" + this.deptName + "&" + "doctorName=" + this.doctorName
						+ "&" + "doctorTitle=" + this.doctorTitle + "&" + "doctorBeginTime=" + this.doctorBeginTime + "&"
						+ "doctorEndTime=" + this.doctorEndTime + "&" + "regFee=" + this.regFee + "&" + "treatFee=" + this.treatFee + "&"
						+ "workId=" + StringUtils.defaultString(this.workId, "") + "&" + "timeFlag="
						+ ( this.timeFlag != null ? this.timeFlag : "" ) + "&" + "diseaseDesc="
						+ StringUtils.defaultString(this.diseaseDesc, "");
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
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

	public Integer getPopulationType() {
		return populationType;
	}

	public void setPopulationType(Integer populationType) {
		this.populationType = populationType;
	}

	public Integer getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(Integer appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Integer getIsViewAppointmentType() {
		return isViewAppointmentType;
	}

	public void setIsViewAppointmentType(Integer isViewAppointmentType) {
		this.isViewAppointmentType = isViewAppointmentType;
	}

	public Integer getIsQueryPatientType() {
		return isQueryPatientType;
	}

	public void setIsQueryPatientType(Integer isQueryPatientType) {
		this.isQueryPatientType = isQueryPatientType;
	}
}
