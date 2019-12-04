package com.yxw.insurance.biz.entity;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * 索赔订单号
 * @author Administrator
 *
 */
public class ClaimOrder extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9181424508840951676L;

	private String id;

	private String openId;

	private String appId;

	private String appCode;

	/**
	 * 医院编码
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行编码
	 */
	private String bankCode;

	/**
	 * 姓名
	 */
	private String patientName;

	/**
	 * 卡号
	 */
	private String patientCardNo;

	/**
	 * 出险原因
	 */
	private String accidentCause;

	/**
	 * 保险公司
	 */
	private String insurer;

	/**
	 * 地址
	 */
	private String address;

	/**订单号
	 */
	private String claimOrderNo;

	/**
	 *申请理赔状态
	 */
	private String claimStatus;

	/**
	 *理赔结果
	 */
	private String claimBearFruit;

	/**
	 *理赔项目的类型（挂号，门诊）
	 */
	private String claimProject;

	/**
	 *理赔项目的订单号
	 */
	private String claimProjectOrderNo;

	/**
	 *理理赔金额
	 */
	private String claimFee;

	/**
	 *理赔申请时间
	 */
	private String claimTime;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 国寿接口返回的流水号
	 */
	private String flowNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientCardNo() {
		return patientCardNo;
	}

	public void setPatientCardNo(String patientCardNo) {
		this.patientCardNo = patientCardNo;
	}

	public String getAccidentCause() {
		return accidentCause;
	}

	public void setAccidentCause(String accidentCause) {
		this.accidentCause = accidentCause;
	}

	public String getInsurer() {
		return insurer;
	}

	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClaimOrderNo() {
		return claimOrderNo;
	}

	public void setClaimOrderNo(String claimOrderNo) {
		this.claimOrderNo = claimOrderNo;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getClaimBearFruit() {
		return claimBearFruit;
	}

	public void setClaimBearFruit(String claimBearFruit) {
		this.claimBearFruit = claimBearFruit;
	}

	public String getClaimProject() {
		return claimProject;
	}

	public void setClaimProject(String claimProject) {
		this.claimProject = claimProject;
	}

	public String getClaimProjectOrderNo() {
		return claimProjectOrderNo;
	}

	public void setClaimProjectOrderNo(String claimProjectOrderNo) {
		this.claimProjectOrderNo = claimProjectOrderNo;
	}

	public String getClaimFee() {
		return claimFee;
	}

	public void setClaimFee(String claimFee) {
		this.claimFee = claimFee;
	}

	public String getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(String claimTime) {
		this.claimTime = claimTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getFlowNumber() {
		return flowNumber;
	}

	public void setFlowNumber(String flowNumber) {
		this.flowNumber = flowNumber;
	}

}
