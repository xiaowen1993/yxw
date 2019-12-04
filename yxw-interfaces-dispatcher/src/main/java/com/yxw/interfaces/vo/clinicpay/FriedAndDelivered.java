/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月14日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->设置代煎配送
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: FriedAndDelivered
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月14日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FriedAndDelivered extends Reserve implements Serializable {

	private static final long serialVersionUID = 9220693605281786787L;
	
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 处方单、检查单、检验单、治疗单ID
	 */
	private String recipeId;
	/**
	 * 代煎剂数
	 */
	private String number;
	/**
	 * 单价
	 */
	private String price;
	/**
	 * 总金额
	 */
	private String totalAmout;
	
	public FriedAndDelivered() {
		super();
	}

	public FriedAndDelivered(String branchCode, String mzFeeId,
			String recipeId, String number, String price, String totalAmout) {
		super();
		this.branchCode = branchCode;
		this.mzFeeId = mzFeeId;
		this.recipeId = recipeId;
		this.number = number;
		this.price = price;
		this.totalAmout = totalAmout;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}
	
}
