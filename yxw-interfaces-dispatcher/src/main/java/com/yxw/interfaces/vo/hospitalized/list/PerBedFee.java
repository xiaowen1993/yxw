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
package com.yxw.interfaces.vo.hospitalized.list;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院费用清单-->清单基本信息
 * @Package: com.yxw.interfaces.entity.hospitalized.list
 * @ClassName: PerBedFee
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PerBedFee extends Reserve implements Serializable {

	private static final long serialVersionUID = -4574724567728779274L;

	/**
	 * 病区
	 */
	private String deptName;
	/**
	 * 床号
	 */
	private String bedNo;
	/**
	 * 说明
	 */
	private String destination;
	/**
	 * 住院押金余额
	 */
	private String balance;
	/**
	 * 本日费用合计
	 */
	private String todayAmout;
	/**
	 * 费用集合
	 */
	private List<BedCost> bedCosts;
	
	public PerBedFee() {
		super();
	}

	public PerBedFee(String deptName, String bedNo, String destination,
			String balance, String todayAmout, List<BedCost> bedCosts) {
		super();
		this.deptName = deptName;
		this.bedNo = bedNo;
		this.destination = destination;
		this.balance = balance;
		this.todayAmout = todayAmout;
		this.bedCosts = bedCosts;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTodayAmout() {
		return todayAmout;
	}

	public void setTodayAmout(String todayAmout) {
		this.todayAmout = todayAmout;
	}

	public List<BedCost> getBedCosts() {
		return bedCosts;
	}

	public void setBedCosts(List<BedCost> bedCosts) {
		this.bedCosts = bedCosts;
	}
	
}
