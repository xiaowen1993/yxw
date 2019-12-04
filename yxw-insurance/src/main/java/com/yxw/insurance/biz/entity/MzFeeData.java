package com.yxw.insurance.biz.entity;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @author Administrator
 */
public class MzFeeData extends BaseEntity  implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1895703215009542442L;

	private String id;
	
	/**
	 * 金额
	 */
	private String totalAmout;
	
	/**
	 * 门诊时间
	 */
	private String clinicTime;
	
	/**
	 * 项目
	 */
	private String projectName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(String clinicTime) {
		this.clinicTime = clinicTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	
}
