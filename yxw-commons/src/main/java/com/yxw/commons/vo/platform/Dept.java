/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yxw.interfaces.vo.register.RegDept;
import com.yxw.interfaces.vo.register.RegDoctor;


/**
 * @Package: com.yxw.platform.vo
 * @ClassName: Dept
 * @Statement: <p>
 *             科室信息
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Dept implements Serializable, Comparable<Dept> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5027721008532265503L;

	private String hospitalId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 医院Name
	 */
	private String hospitalName;

	/**
	 * 医院代码,医院没有分院则返回空字符串
	 */
	private String branchHospitalCode;
	/**
	 * 医院名称,医院没有分院则返回空字符串
	 */
	private String branchHospitalName;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 科室电话
	 */
	private String deptTelephone;
	/**
	 * 科室介绍
	 */
	private String deptDescription;
	/**
	 * 科室位置描述
	 */
	private String deptLocation;
	/**
	 * 父科室代码,0表示没有父科室
	 */
	private String parentDeptCode;

	/**
	 * 子科室
	 */
	private List<Dept> subDepts;

	/**
	 * 科室下的医生列表
	 */
	private List<RegDoctor> doctors;

	private String appId;

	private Integer regType;

	public Dept(RegDept dept) {
		super();
		// TODO Auto-generated constructor stub
		BeanUtils.copyProperties(dept, this);
	}

	public Dept(String branchCode, String branchHospitalName, String deptCode, String deptName, String deptTelephone, String deptDescription,
			String deptLocation, String parentDeptCode) {
		super();
		this.branchHospitalCode = branchCode;
		this.branchHospitalName = branchHospitalName;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.deptTelephone = deptTelephone;
		this.deptDescription = deptDescription;
		this.deptLocation = deptLocation;
		this.parentDeptCode = parentDeptCode;
	}

	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptTelephone() {
		return deptTelephone;
	}

	public void setDeptTelephone(String deptTelephone) {
		this.deptTelephone = deptTelephone;
	}

	public String getDeptDescription() {
		return deptDescription;
	}

	public void setDeptDescription(String deptDescription) {
		this.deptDescription = deptDescription;
	}

	public String getDeptLocation() {
		return deptLocation;
	}

	public void setDeptLocation(String deptLocation) {
		this.deptLocation = deptLocation;
	}

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	public List<Dept> getSubDepts() {
		return subDepts;
	}

	public void setSubDepts(List<Dept> subDepts) {
		this.subDepts = subDepts;
	}

	public List<RegDoctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<RegDoctor> doctors) {
		this.doctors = doctors;
	}

	@Override
	public int compareTo(Dept o) {
		// TODO Auto-generated method stub
		if (deptName.hashCode() > o.getDeptName().hashCode()) {
			return 1;
		} else if (deptName.hashCode() < o.getDeptName().hashCode()) {
			return -1;
		} else {
			return 0;
		}
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}
}
