package com.yxw.statistics.biz.vo;

import java.io.Serializable;

public class OrderManagerVo implements Serializable {

	private static final long serialVersionUID = 3410042095650037434L;

	/**
	 * 医院代码
	 */
	protected String hospitalCode;
	
	/**
	 * 分院代码 -- -1 表示全部
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
	 * 业务类型 -1查询全部的三类core
	 */
	private Integer bizType = -1;
	
	/**
	 * 支付方式
	 */
	private Integer tradeMode = -1;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus = -1;
	
	/**
	 * 业务状态
	 */
	private Integer bizStatus = -1;
	
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
	private String patientMobile;
	
	/**
	 * 平台订单号
	 */
	private String orderNo;
	
	/**
	 * 医院订单
	 */
	private String hisOrderNo;
	
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
		return platform;
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

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

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

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getHisOrderNo() {
		return hisOrderNo;
	}

	public void setHisOrderNo(String hisOrderNo) {
		this.hisOrderNo = hisOrderNo;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 订单生成开始时间
	 */
	private String beginTime;
	
	/**
	 * 订单生成结束时间
	 */
	private String endTime;
}
