package com.yxw.commons.vo.mobile.biz;

import java.io.Serializable;

/**
 * 用于医生去重
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: RegDoctor
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-10-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegDoctor implements Serializable {

	private static final long serialVersionUID = -2371294560633836538L;

	private String hospitalCode;

	private String branchHospitalCode;

	private String deptCode;

	private String doctorCode;

	private Integer regType;

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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public int hashCode() {
		return hospitalCode.hashCode() ^ branchHospitalCode.hashCode() ^ deptCode.hashCode() ^ doctorCode.hashCode() ^ regType.hashCode();
	}

	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof RegDoctor)) {
			return false;
		}
		RegDoctor tempObj = (RegDoctor) obj;
		return tempObj.getHospitalCode().equals(hospitalCode) && tempObj.getBranchHospitalCode().equals(branchHospitalCode) 
				&& tempObj.getDeptCode().equals(deptCode) && tempObj.getDoctorCode().equals(doctorCode) && tempObj.getRegType().equals(regType);
	}
}
