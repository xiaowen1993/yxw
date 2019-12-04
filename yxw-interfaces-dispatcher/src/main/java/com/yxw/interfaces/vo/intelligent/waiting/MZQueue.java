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
 * 智能查询-->候诊排队查询-->门诊候诊信息
 * @Package: com.yxw.interfaces.entity.intelligent.waiting
 * @ClassName: MZQueue
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZQueue extends Reserve implements Serializable {

	private static final long serialVersionUID = -6834767614021684098L;

	/**
	 * 科室名称
	 */
	private String deptName;
	
	/**
	 * 科室地址
	 */
	private String deptLocation;
	
	/**
	 * 医生姓名
	 */
	private String doctorName;
	
	/**
	 * 就诊顺序号
	 */
	private String serialNum;
	
	/**
	 * 当前看诊序号
	 */
	private String currentNum;
	
	/**
	 * 预计就诊时间
	 */
	private String visitTime;
	
	/**
	 * 当前时刻排在前面的人数
	 */
	private String frontNum;
	
	public MZQueue() {
		super();
	}

	public MZQueue(String deptName, String deptLocation, String doctorName, String serialNum, String currentNum,
			String visitTime, String frontNum) {
		super();
		this.deptName = deptName;
		this.deptLocation = deptLocation;
		this.doctorName = doctorName;
		this.serialNum = serialNum;
		this.currentNum = currentNum;
		this.visitTime = visitTime;
		this.frontNum = frontNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptLocation() {
		return deptLocation;
	}

	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getFrontNum() {
		return frontNum;
	}

	public void setFrontNum(String frontNum) {
		this.frontNum = frontNum;
	}
	
	
}
