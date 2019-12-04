/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-18</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.vo;

import com.yxw.commons.vo.cache.CommonParamsVo;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: InspectParamsVo
 * @Statement: <p>业务中需要传递的参数vo   包含医院编码  分院编码  appId  appCode  openId</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年6月18日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InspectParamsVo extends CommonParamsVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5193561067429017794L;

	/**
	 * 查询报告类型 1:检验 2:检查 3:体检
	 */
	private Integer checkType;

	/**
	 * 诊疗卡类型
	 */
	private String patCardType;

	/**
	 * 诊疗卡号
	 */
	private String patCardNo;

	/**
	 * 诊疗人姓名
	 */
	private String patCardName;

	/**
	 * 住院号
	 */
	private String admissionNo;

	/**
	 * 检查报告查询Id
	 */
	private String checkId;

	/**
	 * 检验报告查询Id
	 */
	private String inspectId;

	/**
	 * 医生姓名
	 */
	private String doctorName;

	/**
	 * 报告时间
	 */
	private String reportTime;

	/**
	 * 报告详情的界面样式  1：数据样式        2：图形样式
	 */
	private Integer reportViewCssType;

	public InspectParamsVo() {
		super();
	}

	public InspectParamsVo(String appId, String appCode, String bizCode, String openId, Integer checkType) {
		super();
		this.appId = appId;
		this.appCode = appCode;
		this.bizCode = bizCode;
		this.openId = openId;
		this.checkType = checkType;
	}

	public InspectParamsVo(String hospitalCode, String hospitalId, String hospitalName) {
		super();
		this.hospitalCode = hospitalCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
	}

	public InspectParamsVo(CommonParamsVo vo) {
		super();
		this.hospitalCode = vo.getHospitalCode();
		this.hospitalId = vo.getHospitalId();
		this.hospitalName = vo.getHospitalName();
		this.branchHospitalCode = vo.getBranchHospitalCode();
		this.branchHospitalId = vo.getBranchHospitalId();
		this.branchHospitalName = vo.getBranchHospitalName();
		this.appCode = vo.getAppCode();
		this.appId = vo.getAppId();
		this.bizCode = vo.getBizCode();
		this.openId = vo.getOpenId();
	}

	public InspectParamsVo(String hospitalCode, String hospitalId, String hospitalName, String branchHospitalCode, String branchHospitalId,
			String branchHospitalName, String appId, String appCode, String bizCode, String openId, Integer checkType) {
		super();
		this.hospitalCode = hospitalCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.branchHospitalCode = branchHospitalCode;
		this.branchHospitalId = branchHospitalId;
		this.branchHospitalName = branchHospitalName;
		this.appId = appId;
		this.appCode = appCode;
		this.bizCode = bizCode;
		this.openId = openId;
		this.checkType = checkType;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public String getPatCardType() {
		return patCardType;
	}

	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	public String getPatCardNo() {
		return patCardNo;
	}

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getInspectId() {
		return inspectId;
	}

	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

	public String getPatCardName() {
		return patCardName;
	}

	public void setPatCardName(String patCardName) {
		this.patCardName = patCardName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public Integer getReportViewCssType() {
		return reportViewCssType;
	}

	public void setReportViewCssType(Integer reportViewCssType) {
		this.reportViewCssType = reportViewCssType;
	}

}
