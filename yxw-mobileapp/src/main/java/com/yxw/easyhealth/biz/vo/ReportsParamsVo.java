package com.yxw.easyhealth.biz.vo;

import org.apache.commons.lang.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.vo.cache.CommonParamsVo;


public class ReportsParamsVo extends CommonParamsVo {

	private static final long serialVersionUID = -4719121962108459574L;

	/**
	 * 查询报告类型 1:检验 2:检查 3:体检
	 */
	private Integer reportType;

	/**
	 * 诊疗卡类型
	 */
	private String patCardType;

	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	
	/**
	 * 身份证号码
	 */
	private String idNo;

	/**
	 * 诊疗人姓名
	 */
	private String patCardName;
	
	/**
	 * 脱敏后的就诊人名(不入库)
	 */
	private String encryptedPatientName;

	/**
	 * 住院号
	 */
	private String admissionNo;

	/**
	 * 检查报告查询的详情Id
	 */
	private String detailId;
	
	/**
	 * 报告名称
	 */
	private String reportName;
	
	/**
	 * 报告列表的数据库Id（健康易本地查询记录使用）
	 */
	private String reportId;

	/**
	 * 医生姓名
	 */
	private String doctorName;

	/**
	 * 报告时间
	 */
	private String reportTime;

	/**
	 * 执行时间 (对应检查时间、检验时间、体检时间)
	 */
	private String executeTime;
	
	/**
	 * 模块来源（普通来源，搜索来源）
	 */
	private Integer detailSource;

	/**
	 * 执行科室
	 */
	private String deptName;

	/**
	 * 报告详情的界面样式  1：数据样式        2：图形样式
	 */
	private Integer reportViewCssType;
	
	private String checkType;
	
	private String fileAddress;
	
	private String familyId;

	public ReportsParamsVo() {
		super();
	}

	public ReportsParamsVo(CommonParamsVo vo) {
		super(vo.getHospitalCode(), vo.getHospitalId(), vo.getHospitalName(), vo.getBranchHospitalCode(), vo.getBranchHospitalId(), vo
				.getBranchHospitalName(), vo.getAppId(), vo.getAppCode(), vo.getBizCode(), vo.getOpenId());
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
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

	public String getPatCardName() {
		return patCardName;
	}

	public void setPatCardName(String patCardName) {
		this.patCardName = patCardName;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getCheckType() {
		if (StringUtils.isBlank(checkType) || "null".equals(checkType)) {
			checkType = "";
		}
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getEncryptedPatientName() {
		if (StringUtils.isNotEmpty(patCardName)) {
			encryptedPatientName = patCardName.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedPatientName;
	}

	public void setEncryptedPatientName(String encryptedPatientName) {
		this.encryptedPatientName = encryptedPatientName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public Integer getDetailSource() {
		return detailSource;
	}

	public void setDetailSource(Integer detailSource) {
		this.detailSource = detailSource;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
}
