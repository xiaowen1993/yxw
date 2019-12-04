package com.yxw.statistics.biz.vo;

import java.io.Serializable;

public class CardManagerVo implements Serializable {

	private static final long serialVersionUID = 3410042095650037434L;

	/**
	 * 医院代码
	 */
	protected String hospitalCode;
	
	/**
	 * 分院代码 -- -1 表示全部		---- 不必作为查询条件
	 */
	protected String branchCode;
	
	/**
	 * 平台 -- -1 表示全部，这个时候需要查询多个core
	 */
	protected Integer platform;
	
	/**
	 * 分页大小
	 */
	protected Integer pageSize;
	
	/**
	 * 分页序号
	 */
	protected Integer pageIndex;
	
	/**
	 * 患者姓名
	 */
	private String patientName;
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/**
	 * 手机号码
	 */
	private String mobileNo;
	
	/**
	 * 身份证号码
	 */
	private String idNo;
	
	/**
	 * 绑卡状态：-1全部，0解绑，1绑定
	 */
	private Integer state = -1;
	
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getPlatform() {
		return platform == null ? -1 : platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

}
