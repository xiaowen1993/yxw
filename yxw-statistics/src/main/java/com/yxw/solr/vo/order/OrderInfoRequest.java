package com.yxw.solr.vo.order;

import com.yxw.solr.vo.BizInfoRequest;

public class OrderInfoRequest extends BizInfoRequest {

	private static final long serialVersionUID = 8164850916719084312L;
	
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
	
	/**
	 * 订单生成开始时间
	 */
	private String beginTime;
	
	/**
	 * 订单生成结束时间
	 */
	private String endTime;
	
	public Integer getBizType() {
		return bizType == null ? -1 : bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getPayStatus() {
		return payStatus == null ? -1 : payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getBizStatus() {
		return bizStatus == null ? -1 : bizStatus;
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

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	
	
}
