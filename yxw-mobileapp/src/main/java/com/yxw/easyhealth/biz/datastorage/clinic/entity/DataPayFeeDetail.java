package com.yxw.easyhealth.biz.datastorage.clinic.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * 门诊已缴费详情入库实体
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataPayFeeDetail
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataPayFeeDetail extends DataBaseEntity {

	private static final long serialVersionUID = -1643962280207063479L;

	private String mzFeeId;

	/**
	 * 项目日期,格式：yyyy-MM-dd HH:mm:ss
	 */
	private String itemTime;
	/**
	 * 项目编号,项目的标识符，没有则空
	 */
	private String itemId;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 项目类别/费别,如：药费、检查费、材料费
	 */
	private String itemType;
	/**
	 * 单位
	 */
	private String itemUnit;
	/**
	 * 单价,单位：分
	 */
	private String itemPrice;
	/**
	 * 规格
	 */
	private String itemSpec;
	/**
	 * 数量
	 */
	private String itemNumber;
	/**
	 * 项目价格合计,单位：分，等于：单价*数量
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

	/**
	 * 状态,未发药、已发药、已执行、未执行等
	 */
	private String itemStatus;

	private String dataPayFeeId;

	public DataPayFeeDetail() {

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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getDataPayFeeId() {
		return dataPayFeeId;
	}

	public void setDataPayFeeId(String dataPayFeeId) {
		this.dataPayFeeId = dataPayFeeId;
	}

}
