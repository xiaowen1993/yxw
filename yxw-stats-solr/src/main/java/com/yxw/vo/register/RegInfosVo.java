/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年3月8日
 * @version 1.0
 */
package com.yxw.vo.register;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年3月8日  
 */
public class RegInfosVo implements Serializable {

	private static final long serialVersionUID = 49033068717034646L;

	@Field
	private String id;

	@Field
	private String branchId;

	@Field
	private String branchName;

	@Field
	private String branchCode;

	@Field
	private String hospitalId;

	@Field
	private String hospitalName;

	@Field
	private String hospitalCode;

	@Field
	private Integer bizStatus;

	@Field
	private Integer payStatus;

	@Field
	private Integer cardType;

	@Field
	private String cardNo;

	@Field
	private String openId;

	@Field
	private Integer regType;

	@Field
	private String patientName;

	@Field
	private String patientMobile;

	@Field
	private String orderNo;

	@Field
	private Long orderNoHashVal;

	@Field
	private String refundOrderNo;

	@Field
	private String refundHisOrdNo;

	@Field
	private String agtOrdNum;

	@Field
	private String agtRefundOrdNum;

	@Field
	private String deptCode;

	@Field
	private String deptName;

	@Field
	private Integer category;

	@Field
	private String doctorCode;

	@Field
	private String doctorName;

	@Field
	private String doctorTitle;

	@Field
	private Long registerTime;

	@Field
	private String scheduleDate;

	@Field
	private String beginTegerime;

	@Field
	private String endTime;

	@Field
	private Integer regMode;

	@Field
	private Integer regFee;

	@Field
	private Integer realRegFee;

	@Field
	private Integer treatFee;

	@Field
	private Integer realTreatFee;

	@Field
	private String feeDesc;

	@Field
	private String hisOrdNo;

	@Field
	private String receiptNum;

	@Field
	private String serialNum;

	@Field
	private String visitLocation;

	@Field
	private String barcode;

	@Field
	private String visitDesc;

	@Field
	private Integer regPersonType;

	@Field
	private Long updateTime;

	@Field
	private Integer timeFlag;

	@Field
	private Integer onlinePaymentType;

	@Field
	private Long payTime;

	@Field
	private Long refundTime;

	@Field
	private String tableName;

	@Field
	private String payTime_utc;

	@Field
	private String refundTime_utc;

	@Field
	private String payDate;

	@Field
	private String refundDate;

	@Field
	private String createDate;

	@Field
	private Integer totalFee;

	@Field
	private Integer platform;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/**
	 * @return the hospitalCode
	 */
	public String getHospitalCode() {
		return hospitalCode;
	}

	/**
	 * @param hospitalCode the hospitalCode to set
	 */
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	/**
	 * @return the bizStatus
	 */
	public Integer getBizStatus() {
		return bizStatus;
	}

	/**
	 * @param bizStatus the bizStatus to set
	 */
	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

	/**
	 * @return the payStatus
	 */
	public Integer getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus the payStatus to set
	 */
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * @return the cardType
	 */
	public Integer getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the regType
	 */
	public Integer getRegType() {
		return regType;
	}

	/**
	 * @param regType the regType to set
	 */
	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the patientMobile
	 */
	public String getPatientMobile() {
		return patientMobile;
	}

	/**
	 * @param patientMobile the patientMobile to set
	 */
	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the orderNoHashVal
	 */
	public Long getOrderNoHashVal() {
		return orderNoHashVal;
	}

	/**
	 * @param orderNoHashVal the orderNoHashVal to set
	 */
	public void setOrderNoHashVal(Long orderNoHashVal) {
		this.orderNoHashVal = orderNoHashVal;
	}

	/**
	 * @return the refundOrderNo
	 */
	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	/**
	 * @param refundOrderNo the refundOrderNo to set
	 */
	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	/**
	 * @return the refundHisOrdNo
	 */
	public String getRefundHisOrdNo() {
		return refundHisOrdNo;
	}

	/**
	 * @param refundHisOrdNo the refundHisOrdNo to set
	 */
	public void setRefundHisOrdNo(String refundHisOrdNo) {
		this.refundHisOrdNo = refundHisOrdNo;
	}

	/**
	 * @return the agtOrdNum
	 */
	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	/**
	 * @param agtOrdNum the agtOrdNum to set
	 */
	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	/**
	 * @return the agtRefundOrdNum
	 */
	public String getAgtRefundOrdNum() {
		return agtRefundOrdNum;
	}

	/**
	 * @param agtRefundOrdNum the agtRefundOrdNum to set
	 */
	public void setAgtRefundOrdNum(String agtRefundOrdNum) {
		this.agtRefundOrdNum = agtRefundOrdNum;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the doctorCode
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode the doctorCode to set
	 */
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the doctorTitle
	 */
	public String getDoctorTitle() {
		return doctorTitle;
	}

	/**
	 * @param doctorTitle the doctorTitle to set
	 */
	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	/**
	 * @return the registerTime
	 */
	public Long getRegisterTime() {
		return registerTime;
	}

	/**
	 * @param registerTime the registerTime to set
	 */
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @return the scheduleDate
	 */
	public String getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate the scheduleDate to set
	 */
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the beginTegerime
	 */
	public String getBeginTegerime() {
		return beginTegerime;
	}

	/**
	 * @param beginTegerime the beginTegerime to set
	 */
	public void setBeginTegerime(String beginTegerime) {
		this.beginTegerime = beginTegerime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the regMode
	 */
	public Integer getRegMode() {
		return regMode;
	}

	/**
	 * @param regMode the regMode to set
	 */
	public void setRegMode(Integer regMode) {
		this.regMode = regMode;
	}

	/**
	 * @return the regFee
	 */
	public Integer getRegFee() {
		return regFee;
	}

	/**
	 * @param regFee the regFee to set
	 */
	public void setRegFee(Integer regFee) {
		this.regFee = regFee;
	}

	/**
	 * @return the realRegFee
	 */
	public Integer getRealRegFee() {
		return realRegFee;
	}

	/**
	 * @param realRegFee the realRegFee to set
	 */
	public void setRealRegFee(Integer realRegFee) {
		this.realRegFee = realRegFee;
	}

	/**
	 * @return the treatFee
	 */
	public Integer getTreatFee() {
		return treatFee;
	}

	/**
	 * @param treatFee the treatFee to set
	 */
	public void setTreatFee(Integer treatFee) {
		this.treatFee = treatFee;
	}

	/**
	 * @return the realTreatFee
	 */
	public Integer getRealTreatFee() {
		return realTreatFee;
	}

	/**
	 * @param realTreatFee the realTreatFee to set
	 */
	public void setRealTreatFee(Integer realTreatFee) {
		this.realTreatFee = realTreatFee;
	}

	/**
	 * @return the feeDesc
	 */
	public String getFeeDesc() {
		return feeDesc;
	}

	/**
	 * @param feeDesc the feeDesc to set
	 */
	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}

	/**
	 * @return the hisOrdNo
	 */
	public String getHisOrdNo() {
		return hisOrdNo;
	}

	/**
	 * @param hisOrdNo the hisOrdNo to set
	 */
	public void setHisOrdNo(String hisOrdNo) {
		this.hisOrdNo = hisOrdNo;
	}

	/**
	 * @return the receiptNum
	 */
	public String getReceiptNum() {
		return receiptNum;
	}

	/**
	 * @param receiptNum the receiptNum to set
	 */
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	/**
	 * @return the serialNum
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * @return the visitLocation
	 */
	public String getVisitLocation() {
		return visitLocation;
	}

	/**
	 * @param visitLocation the visitLocation to set
	 */
	public void setVisitLocation(String visitLocation) {
		this.visitLocation = visitLocation;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the visitDesc
	 */
	public String getVisitDesc() {
		return visitDesc;
	}

	/**
	 * @param visitDesc the visitDesc to set
	 */
	public void setVisitDesc(String visitDesc) {
		this.visitDesc = visitDesc;
	}

	/**
	 * @return the regPersonType
	 */
	public Integer getRegPersonType() {
		return regPersonType;
	}

	/**
	 * @param regPersonType the regPersonType to set
	 */
	public void setRegPersonType(Integer regPersonType) {
		this.regPersonType = regPersonType;
	}

	/**
	 * @return the updateTime
	 */
	public Long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the timeFlag
	 */
	public Integer getTimeFlag() {
		return timeFlag;
	}

	/**
	 * @param timeFlag the timeFlag to set
	 */
	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	/**
	 * @return the onlinePaymentType
	 */
	public Integer getOnlinePaymentType() {
		return onlinePaymentType;
	}

	/**
	 * @param onlinePaymentType the onlinePaymentType to set
	 */
	public void setOnlinePaymentType(Integer onlinePaymentType) {
		this.onlinePaymentType = onlinePaymentType;
	}

	/**
	 * @return the payTime
	 */
	public Long getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the refundTime
	 */
	public Long getRefundTime() {
		return refundTime;
	}

	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the payTime_utc
	 */
	public String getPayTime_utc() {
		return payTime_utc;
	}

	/**
	 * @param payTime_utc the payTime_utc to set
	 */
	public void setPayTime_utc(String payTime_utc) {
		this.payTime_utc = payTime_utc;
	}

	/**
	 * @return the refundTime_utc
	 */
	public String getRefundTime_utc() {
		return refundTime_utc;
	}

	/**
	 * @param refundTime_utc the refundTime_utc to set
	 */
	public void setRefundTime_utc(String refundTime_utc) {
		this.refundTime_utc = refundTime_utc;
	}

	/**
	 * @return the payDate
	 */
	public String getPayDate() {
		return payDate;
	}

	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	/**
	 * @return the refundDate
	 */
	public String getRefundDate() {
		return refundDate;
	}

	/**
	 * @param refundDate the refundDate to set
	 */
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the totalFee
	 */
	public Integer getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the platform
	 */
	public Integer getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

}
