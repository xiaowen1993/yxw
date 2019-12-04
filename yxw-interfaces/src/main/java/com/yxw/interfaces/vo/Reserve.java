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
package com.yxw.interfaces.vo;

import java.io.Serializable;

/**
 * 医享网络标准接口请求报文和响应报文预留字段封装
 * @Package: com.yxw.interfaces.vo
 * @ClassName: Request
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年8月10日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Reserve implements Serializable {

	private static final long serialVersionUID = -3996423588562466361L;
	/**
	 * 预留字段1
	 */
	private String reservedFieldOne;
	/**
	 * 预留字段2
	 */
	private String reservedFieldTwo;
	/**
	 * 预留字段3
	 */
	private String reservedFieldThree;
	/**
	 * 预留字段4
	 */
	private String reservedFieldFour;
	/**
	 * 预留字段5
	 */
	private String reservedFieldFive;

	public Reserve() {
		super();
	}

	/**
	 * @param reservedFieldOne
	 * @param reservedFieldTwo
	 * @param reservedFieldThree
	 * @param reservedFieldFour
	 * @param reservedFieldFive
	 */
	public Reserve(String reservedFieldOne, String reservedFieldTwo, String reservedFieldThree, String reservedFieldFour, String reservedFieldFive) {
		super();
		this.reservedFieldOne = reservedFieldOne;
		this.reservedFieldTwo = reservedFieldTwo;
		this.reservedFieldThree = reservedFieldThree;
		this.reservedFieldFour = reservedFieldFour;
		this.reservedFieldFive = reservedFieldFive;
	}

	/**
	 * @return the reservedFieldOne
	 */
	public String getReservedFieldOne() {
		return reservedFieldOne;
	}

	/**
	 * @param reservedFieldOne the reservedFieldOne to set
	 */
	public void setReservedFieldOne(String reservedFieldOne) {
		this.reservedFieldOne = reservedFieldOne;
	}

	/**
	 * @return the reservedFieldTwo
	 */
	public String getReservedFieldTwo() {
		return reservedFieldTwo;
	}

	/**
	 * @param reservedFieldTwo the reservedFieldTwo to set
	 */
	public void setReservedFieldTwo(String reservedFieldTwo) {
		this.reservedFieldTwo = reservedFieldTwo;
	}

	/**
	 * @return the reservedFieldThree
	 */
	public String getReservedFieldThree() {
		return reservedFieldThree;
	}

	/**
	 * @param reservedFieldThree the reservedFieldThree to set
	 */
	public void setReservedFieldThree(String reservedFieldThree) {
		this.reservedFieldThree = reservedFieldThree;
	}

	/**
	 * @return the reservedFieldFour
	 */
	public String getReservedFieldFour() {
		return reservedFieldFour;
	}

	/**
	 * @param reservedFieldFour the reservedFieldFour to set
	 */
	public void setReservedFieldFour(String reservedFieldFour) {
		this.reservedFieldFour = reservedFieldFour;
	}

	/**
	 * @return the reservedFieldFive
	 */
	public String getReservedFieldFive() {
		return reservedFieldFive;
	}

	/**
	 * @param reservedFieldFive the reservedFieldFive to set
	 */
	public void setReservedFieldFive(String reservedFieldFive) {
		this.reservedFieldFive = reservedFieldFive;
	}

}
