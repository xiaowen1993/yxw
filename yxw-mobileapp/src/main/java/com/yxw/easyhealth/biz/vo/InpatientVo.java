package com.yxw.easyhealth.biz.vo;

import com.yxw.commons.vo.cache.CommonParamsVo;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: ClinicVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InpatientVo extends CommonParamsVo {

	private static final long serialVersionUID = -9074842713322342340L;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 卡号
	 */
	private String cardNo;

	/**
	 * 病人姓名
	 */
	private String patientName;

	/**
	 * 手机号码
	 */
	private String patientMobile;

	/**
	 * 证件类型
	 */
	private String idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 就诊人的编号（我们的库中的）
	 */
	private String personId;

	/**
	 * 住院号(多次同一个医院/分院住院用同一个，唯一)
	 */
	private String admissionNo;

	/**
	 * 住院编号/流水号(表示依次住院)
	 */
	private String inpatientId;

	/**
	 * 住院次数(与住院号关联使用，表示第几次的就诊)
	 */
	private String inTime;

	/**
	 * 总费用
	 */
	private String totalFee;

	/**
	 * 已缴费用
	 */
	private String payedFee;

	/**
	 * 待补缴金额
	 */
	private String leftFee;

	/**
	 * 绑定住院号类型。本人1、他人2、儿童3
	 */
	private String bindInpatientType;

	/**
	 * 是否绑定了本人的住院号 
	 */
	private Boolean selfNoExists;

	public InpatientVo() {
		super();
	}

	public InpatientVo(CommonParamsVo vo) {
		super(vo.getHospitalCode(), vo.getHospitalId(), vo.getHospitalName(), vo.getBranchHospitalCode(), vo.getBranchHospitalId(), vo
				.getBranchHospitalName(), vo.getAppId(), vo.getAppCode(), vo.getBizCode(), vo.getOpenId());
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayedFee() {
		return payedFee;
	}

	public void setPayedFee(String payedFee) {
		this.payedFee = payedFee;
	}

	public String getLeftFee() {
		return leftFee;
	}

	public void setLeftFee(String leftFee) {
		this.leftFee = leftFee;
	}

	public String getBindInpatientType() {
		return bindInpatientType;
	}

	public void setBindInpatientType(String bindInpatientType) {
		this.bindInpatientType = bindInpatientType;
	}

	public Boolean getSelfNoExists() {
		return selfNoExists;
	}

	public void setSelfNoExists(Boolean selfNoExists) {
		this.selfNoExists = selfNoExists;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getInpatientId() {
		return inpatientId;
	}

	public void setInpatientId(String inpatientId) {
		this.inpatientId = inpatientId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
