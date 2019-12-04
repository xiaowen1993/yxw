/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊已缴费记录明细-->明细信息
 * 
 * @Package: com.yxw.interfaces.vo.clinicpay
 * @ClassName: Detail
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年7月27日
 * @modify By: 范建明
 * @modify Date: 2015年12月30日
 * @Why&What is modify: 增加itemStatus属性，用于表示明细项的状态[未发药、已发药、已执行、未执行等]
 * @Version: 1.0
 */
public class Detail extends Reserve implements Serializable {

	private static final long serialVersionUID = -4396325589788331159L;
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

	public Detail() {
		super();
	}

	/**
	 * @param itemTime
	 * @param itemId
	 * @param itemName
	 * @param itemType
	 * @param itemUnit
	 * @param itemPrice
	 * @param itemSpec
	 * @param itemNumber
	 * @param itemTotalFee
	 * @param deptName
	 * @param doctorCode
	 * @param doctorName
	 * @param itemStatus
	 */
	public Detail(String itemTime, String itemId, String itemName, String itemType, String itemUnit, String itemPrice, String itemSpec,
			String itemNumber, String itemTotalFee, String deptName, String doctorCode, String doctorName, String itemStatus) {
		super();
		this.itemTime = itemTime;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemUnit = itemUnit;
		this.itemPrice = itemPrice;
		this.itemSpec = itemSpec;
		this.itemNumber = itemNumber;
		this.itemTotalFee = itemTotalFee;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.itemStatus = itemStatus;
	}

	/**
	 * @return the itemTime
	 */
	public String getItemTime() {
		return itemTime;
	}

	/**
	 * @param itemTime
	 *            the itemTime to set
	 */
	public void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType
	 *            the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemUnit
	 */
	public String getItemUnit() {
		return itemUnit;
	}

	/**
	 * @param itemUnit
	 *            the itemUnit to set
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	/**
	 * @return the itemPrice
	 */
	public String getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice
	 *            the itemPrice to set
	 */
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the itemSpec
	 */
	public String getItemSpec() {
		return itemSpec;
	}

	/**
	 * @param itemSpec
	 *            the itemSpec to set
	 */
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	/**
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber
	 *            the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the itemTotalFee
	 */
	public String getItemTotalFee() {
		return itemTotalFee;
	}

	/**
	 * @param itemTotalFee
	 *            the itemTotalFee to set
	 */
	public void setItemTotalFee(String itemTotalFee) {
		this.itemTotalFee = itemTotalFee;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the doctorCode
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode
	 *            the doctorCode to set
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
	 * @param doctorName
	 *            the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

}
