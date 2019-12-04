package com.yxw.easyhealth.biz.datastorage.clinic.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * 门诊待缴费入库详情
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataMZFeeDetail
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataMzFeeDetail extends DataBaseEntity {

	private static final long serialVersionUID = 682179409935792616L;

	/**
	 * 关联的缴费编号
	 */
	private String mzFeeId;

	/**
	 * 项目时间
	 */
	private String itemTime;

	/**
	 * 项目编号
	 */
	private String itemId;

	/**
	 * 项目名称
	 */
	private String itemName;

	/**
	 * 项目类别/费别
	 */
	private String itemType;

	/**
	 * 项目单位
	 */
	private String itemUnit;

	/**
	 * 项目单价
	 */
	private String itemPrice;

	/**
	 * 项目规格
	 */
	private String itemSpec;

	/**
	 * 项目数量
	 */
	private String itemNumber;

	/**
	 * 项目价格合计
	 */
	private String itemTotalFee;

	/**
	 * 开出科室
	 */
	private String deptName;

	/**
	 * 开药医生代码
	 */
	private String doctorCode;

	/**
	 * 开药医生姓名
	 */
	private String doctorName;

	public DataMzFeeDetail() {

	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getItemTime() {
		return itemTime;
	}

	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemTotalFee() {
		return itemTotalFee;
	}

	public void setItemTotalFee(String itemTotalFee) {
		this.itemTotalFee = itemTotalFee;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

}
