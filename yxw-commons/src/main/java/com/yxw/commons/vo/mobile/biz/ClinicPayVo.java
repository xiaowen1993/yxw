package com.yxw.commons.vo.mobile.biz;

import org.apache.commons.lang3.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.vo.cache.CommonParamsVo;

/**
 * 门诊待缴费Vo
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: ClinicVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ClinicPayVo extends CommonParamsVo {

	private static final long serialVersionUID = 910772611132087187L;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 卡号
	 */
	private String cardNo;

	/**
	 * 病人姓名
	 */
	private String patientName;

	/**
	 * 手机号码
	 */
	private String patientMobile;

	/**
	 * 缴费项唯一标示
	 */
	private String mzFeeId;

	/**
	 * 省略的mzFeeId(取20位)
	 */
	private String omitMzFeeId;

	/**
	 * 总金额
	 */
	private Integer totalFee;

	/**
	 * 医保金额
	 */
	private Integer medicareFee;

	/**
	 * 应付金额
	 */
	private Integer payFee;

	/**
	 * 医保类型
	 */
	private String medicareType;

	/**
	 * 脱敏姓名
	 */
	private String encryptedPatientName;

	/**
	 * His就诊流水号
	 */
	private String hisOrdNum;

	/**
	 * 支付订单收据号
	 */
	private String receiptNum;

	/**
	 * His返回的取药信息
	 */
	private String hisMessage;

	/**
	 * 接口返回的条形码内容
	 */
	private String barcode;

	/**
	 * 科室名称
	 */
	private String deptName;

	/**
	 * 医生名称
	 */
	private String doctorName;

	/**
	 * 门诊状态
	 */
	private Integer clinicStatus;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 是否显示支付方式
	 */
	private int isShowTradeMode;

	/**
	 * 关联的家人
	 */
	private String familyId;

	/**
	 * 0 常规访问
	 * 1消息推送访问
	 */
	private Integer visitWay;

	/**
	 * 是否已理赔，1：已理赔  0：未理赔
	 */
	private Integer isClaim;

	public ClinicPayVo() {
		super();
	}

	public Integer getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getMedicareFee() {
		return medicareFee;
	}

	public void setMedicareFee(Integer medicareFee) {
		this.medicareFee = medicareFee;
	}

	public Integer getPayFee() {
		return payFee;
	}

	public void setPayFee(Integer payFee) {
		this.payFee = payFee;
	}

	public String getMedicareType() {
		return medicareType;
	}

	public void setMedicareType(String medicareType) {
		this.medicareType = medicareType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getEncryptedPatientName() {
		if (StringUtils.isNotBlank(patientName)) {
			encryptedPatientName = patientName.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedPatientName;
	}

	public void setEncryptedPatientName(String encryptedPatientName) {
		this.encryptedPatientName = encryptedPatientName;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
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

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getOmitMzFeeId() {
		if (StringUtils.isNotBlank(mzFeeId)) {
			if (mzFeeId.length() < 20) {
				omitMzFeeId = mzFeeId;
			} else {
				omitMzFeeId = mzFeeId.substring(0, 18).concat("...");
			}
		} else {
			omitMzFeeId = "";
		}
		return omitMzFeeId;
	}

	public void setOmitMzFeeId(String omitMzFeeId) {
		this.omitMzFeeId = omitMzFeeId;
	}

	public String getHisMessage() {
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getClinicStatus() {
		return clinicStatus;
	}

	public void setClinicStatus(Integer clinicStatus) {
		this.clinicStatus = clinicStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getIsShowTradeMode() {
		return isShowTradeMode;
	}

	public void setIsShowTradeMode(int isShowTradeMode) {
		this.isShowTradeMode = isShowTradeMode;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public Integer getVisitWay() {
		return visitWay;
	}

	public void setVisitWay(Integer visitWay) {
		this.visitWay = visitWay;
	}

}
