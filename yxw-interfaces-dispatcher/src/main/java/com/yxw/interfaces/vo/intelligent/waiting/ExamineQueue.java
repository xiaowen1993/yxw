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
 * 智能查询-->候诊排队查询-->检查/检验/体检排队信息
 * @Package: com.yxw.interfaces.entity.intelligent.waiting
 * @ClassName: ExamineQueue
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExamineQueue extends Reserve implements Serializable {

	private static final long serialVersionUID = 2652963515182813665L;

	/**
	 * 检查/检验/体检名称
	 */
	private String checkName;
	
	/**
	 * 执行科室
	 */
	private String deptName;
	
	/**
	 * 执行科室位置
	 */
	private String deptLocation;
	
	/**
	 * 检查/检验/体检顺序号
	 */
	private String serialNum;
	
	/**
	 * 当前序号
	 */
	private String currentNum;
	
	/**
	 * 预计报到时间
	 */
	private String dutyTime;
	
	/**
	 * 当前时刻排在前面的人数
	 */
	private String frontNum;

	public ExamineQueue(String checkName, String deptName, String deptLocation, String serialNum, String currentNum,
			String dutyTime, String frontNum) {
		super();
		this.checkName = checkName;
		this.deptName = deptName;
		this.deptLocation = deptLocation;
		this.serialNum = serialNum;
		this.currentNum = currentNum;
		this.dutyTime = dutyTime;
		this.frontNum = frontNum;
	}
	
	public ExamineQueue() {
		super();
	}

	public String getCheckName() {
		return checkName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}

	public void setDutyTime(String dutyTime) {
		this.dutyTime = dutyTime;
	}

	public void setFrontNum(String frontNum) {
		this.frontNum = frontNum;
	}

	public String getDeptLocation() {
		return deptLocation;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public String getCurrentNum() {
		return currentNum;
	}

	public String getDutyTime() {
		return dutyTime;
	}

	public String getFrontNum() {
		return frontNum;
	}
	
	
}
