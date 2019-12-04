package com.yxw.commons.entity.mobile.biz.register;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.register.entity
 * @ClassName: StopRegisterLog
 * @Statement: <p>停诊异常日志记录</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterLog extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1637983641422425280L;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	/**
	 * 预约方式 1：微信 2：支付宝
	 */
	private Integer regMode;

	/**
	 * 查询开始时间
	 */
	private String beginDate;

	/**
	 * 查询结束时间
	 */
	private String endDate;

	/**
	 * 入库时间
	 */
	private String storageDate;

	public StopRegisterLog() {
		super();
	}

	public StopRegisterLog(String hospitalCode, String branchHospitalCode, Integer regMode, String beginDate, String endDate, String storageDate) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.regMode = regMode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.storageDate = storageDate;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public Integer getRegMode() {
		return regMode;
	}

	public void setRegMode(Integer regMode) {
		this.regMode = regMode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStorageDate() {
		return storageDate;
	}

	public void setStorageDate(String storageDate) {
		this.storageDate = storageDate;
	}

}