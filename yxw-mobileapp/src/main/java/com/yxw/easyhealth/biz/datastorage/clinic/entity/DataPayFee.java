package com.yxw.easyhealth.biz.datastorage.clinic.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * 门诊已缴费入库实体
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataPayFee
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataPayFee extends DataBaseEntity {

	private static final long serialVersionUID = -7303666396767790128L;

	private String hisOrdNum;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 接诊科室
	 */
	private String deptName;
	/**
	 * 接诊医生姓名
	 */
	private String doctorName;
	/**
	 * 缴费金额
	 */
	private String payAmout;
	/**
	 * 缴费时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String payTime;
	/**
	 * 支付方式,见PayPlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String payMode;
	/**
	 * 缴费状态,0：未支付,1：已支付,2：已退费
	 */
	private String payStatus;

	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 条形码
	 */
	private String barcode;
	/**
	 * 医院返回的信息,返回取药、检查或治疗地点的导诊信息
	 */
	private String hisMessage;

	public DataPayFee() {
		super();
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
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

	public String getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getHisMessage() {
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}
}
