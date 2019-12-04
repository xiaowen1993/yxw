/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月19日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊待缴费记录处方信息-->处方明细
 * @Package: com.yxw.interfaces.vo.clinicpay
 * @ClassName: RecipeDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月19日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RecipeDetail extends Reserve implements Serializable {

	private static final long serialVersionUID = -1635041140868769179L;
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
	
	public RecipeDetail() {
		super();
	}

	public RecipeDetail(String itemTime, String itemId, String itemName,
			String itemUnit, String itemPrice, String itemSpec,
			String itemNumber, String itemTotalFee) {
		super();
		this.itemTime = itemTime;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemUnit = itemUnit;
		this.itemPrice = itemPrice;
		this.itemSpec = itemSpec;
		this.itemNumber = itemNumber;
		this.itemTotalFee = itemTotalFee;
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
	
}
