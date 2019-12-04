package com.yxw.solr.biz.register.entity;

import java.io.Serializable;

public class Dept implements Serializable {

	private static final long serialVersionUID = 292717118585344586L;

	private String deptName;
	
	private String deptCode;
	
	private String branchCode;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
}
