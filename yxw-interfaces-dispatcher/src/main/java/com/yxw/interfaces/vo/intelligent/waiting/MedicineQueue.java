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
package com.yxw.interfaces.vo.intelligent.waiting;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 智能查询-->候诊排队查询-->取药排队信息
 * @Package: com.yxw.interfaces.entity.intelligent.waiting
 * @ClassName: MedicineQueue
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MedicineQueue extends Reserve implements Serializable {

	private static final long serialVersionUID = 4137191563243240398L; 
	
	/**
	 * 药房名称
	 */
	private String pharmacy;
	
	/**
	 * 药房位置
	 */
	private String localtion;
	
	/**
	 * 取药顺序号
	 */
	private String serialNum;
	
	/**
	 * 当前取药序号
	 */
	private String currentNum;
	
	/**
	 * 预计取号时间
	 */
	private String getTime;
	
	/**
	 * 当前时刻排在前面的人数
	 */
	private String frontNum;
	
	public MedicineQueue () {
		super();
	}

	public MedicineQueue(String reservedFieldOne, String reservedFieldTwo, String reservedFieldThree,
			String reservedFieldFour, String reservedFieldFive) {
		super(reservedFieldOne, reservedFieldTwo, reservedFieldThree, reservedFieldFour, reservedFieldFive);
		// TODO Auto-generated constructor stub
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getLocaltion() {
		return localtion;
	}

	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getFrontNum() {
		return frontNum;
	}

	public void setFrontNum(String frontNum) {
		this.frontNum = frontNum;
	}
	
	
 
}
