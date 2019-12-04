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
package com.yxw.interfaces.vo.hospitalized.deposit;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院押金-->住院费用
 * @Package: com.yxw.interfaces.entity.hospitalized.deposit
 * @ClassName: BedFee
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BedFee extends Reserve implements Serializable {

	private static final long serialVersionUID = -9123572436095321083L;

	/**
	 * 住院总费用
	 */
	private String totalFee;
	/**
	 * 已缴押金
	 */
	private String payedFee;
	/**
	 * 待补缴押金
	 */
	private String leftFee;
	
	public BedFee() {
		super();
	}

	public BedFee(String totalFee, String payedFee, String leftFee) {
		super();
		this.totalFee = totalFee;
		this.payedFee = payedFee;
		this.leftFee = leftFee;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayedFee() {
		return payedFee;
	}

	public void setPayedFee(String payedFee) {
		this.payedFee = payedFee;
	}

	public String getLeftFee() {
		return leftFee;
	}

	public void setLeftFee(String leftFee) {
		this.leftFee = leftFee;
	}
	
}
