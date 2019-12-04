package com.yxw.vo.dept;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 功能概要：科室统计
 * 
 * @author Administrator
 * @date 2017年3月3日
 */

public class DeptStats implements Serializable {

	private static final long serialVersionUID = 1266302537792370124L;

	/**
	 * id
	 */
	@Field
	private String id;

	@Field // 标准是1，医院项目是2 -- 对于跨平台的，都叫做2
	private Integer platform;

	/**
	 * 医院代码
	 */
	@Field
	private String hospitalCode = "-";

	/**
	 * 医院名称
	 */
	@Field
	private String hospitalName = "-";

	/**
	 * 区域代码
	 */
	@Field
	private String areaCode;

	/**
	 * 区域名称
	 */
	@Field
	private String areaName;

	/**
	 * 本月的年月 格式：YYYY-MM
	 */
	@Field
	private String thisMonth;

	@Field
	private String deptName;

	@Field
	private String deptCode;

	@Field
	private long monthWxCount = 0;

	@Field
	private long monthAliCount = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

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

	public long getMonthWxCount() {
		return monthWxCount;
	}

	public void setMonthWxCount(long monthWxCount) {
		this.monthWxCount = monthWxCount;
	}

	public long getMonthAliCount() {
		return monthAliCount;
	}

	public void setMonthAliCount(long monthAliCount) {
		this.monthAliCount = monthAliCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (this.getClass() == obj.getClass()) {
				DeptStats stats = (DeptStats) obj;
				return this.getDeptCode().equals(stats.getDeptCode());
			} else {
				return false;
			}
		}
	}

	/**
	 * 考虑到可能会有多个平台的数据
	 * @param stats
	 */
	public void combineEntity(DeptStats stats) {
		if (stats != null) {
			setMonthWxCount(this.getMonthWxCount() + stats.getMonthWxCount());
			setMonthAliCount(this.getMonthAliCount() + stats.getMonthAliCount());
		}
	}

}
