/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2016 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2016年1月12日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.hospitalized;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->出院清账
 * @Package: com.yxw.interfaces.entity.hospitalized.deposit
 * @ClassName: SettleBedFee
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2016年1月12日
 * @modify By: 
 * @modify Date: 
 * @Why&What is modify: 
 * @Version: 1.0
 */
public class SettleBedFee extends Reserve implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3013807525904866520L;
	/**
	 * 住院总费用
	 */
	private String totalFee;
	/**
	 * 已缴押金
	 */
	private String payedFee;
	/**
	 * 押金余额
	 */
	private String leftFee;
	/**
	 * 医院返回的信息
	 */
	private String hisMessage;
	
	public SettleBedFee() {
		super();
	}

	public SettleBedFee(String totalFee, String payedFee, String leftFee, String hisMessage) {
		super();
		this.totalFee = totalFee;
		this.payedFee = payedFee;
		this.leftFee = leftFee;
		this.hisMessage = hisMessage;
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

	public String getHisMessage() {
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}
	
}
