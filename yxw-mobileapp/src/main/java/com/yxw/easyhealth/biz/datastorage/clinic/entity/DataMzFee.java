package com.yxw.easyhealth.biz.datastorage.clinic.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * 门诊待缴费记录入库实体
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataMZFee
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-14
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataMzFee extends DataBaseEntity {

	private static final long serialVersionUID = -7201618133106539449L;

	/**
	 * 缴费标识符
	 */
	private String mzFeeId;

	/**
	 * 门诊时间
	 */
	private String clinicTime;

	/**
	 * 科室名称
	 */
	private String deptName;

	/**
	 * 医生名称
	 */
	private String doctorName;

	/**
	 * 医保类型
	 * 自费、医保、公费、农村合作医疗等
	 */
	private String payType;

	/**
	 * 应付金额
	 */
	private String payAmout;

	/**
	 * 医保金额
	 */
	private String medicareAmout;

	/**
	 * 总金额
	 */
	private String totalAmout;

	public DataMzFee() {

	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(String clinicTime) {
		this.clinicTime = clinicTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	public String getMedicareAmout() {
		return medicareAmout;
	}

	public void setMedicareAmout(String medicareAmout) {
		this.medicareAmout = medicareAmout;
	}

	public String getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
