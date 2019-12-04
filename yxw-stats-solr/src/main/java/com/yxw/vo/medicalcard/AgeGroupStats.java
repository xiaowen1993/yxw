package com.yxw.vo.medicalcard;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 功能概要：年龄段分析统计
 * 
 * AgeGroup:	0: 0~12
 * 				1: 13~18
 * 				2: 19~29
 * 				3: 30~35
 * 				4: 36~50
 * 				5: 51~70
 * 				6: 70~
 * 
 * @author Administrator
 * @date 2017年3月3日
 */

public class AgeGroupStats implements Serializable {

	private static final long serialVersionUID = -3108528599804003374L;

	/**
	 * id
	 */
	@Field
	private String id;

	@Field // 标准是1，医院项目是2
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
	private long wxAgeGroup0 = 0;

	@Field
	private long wxAgeGroup1 = 0;
	
	@Field
	private long wxAgeGroup2 = 0;
	
	@Field
	private long wxAgeGroup3 = 0;
	
	@Field
	private long wxAgeGroup4 = 0;
	
	@Field
	private long wxAgeGroup5 = 0;
	
	@Field
	private long wxAgeGroup6 = 0;
	
	@Field
	private long aliAgeGroup0 = 0;

	@Field
	private long aliAgeGroup1 = 0;
	
	@Field
	private long aliAgeGroup2 = 0;
	
	@Field
	private long aliAgeGroup3 = 0;
	
	@Field
	private long aliAgeGroup4 = 0;
	
	@Field
	private long aliAgeGroup5 = 0;
	
	@Field
	private long aliAgeGroup6 = 0;
	
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

	public long getWxAgeGroup0() {
		return wxAgeGroup0;
	}

	public void setWxAgeGroup0(long wxAgeGroup0) {
		this.wxAgeGroup0 = wxAgeGroup0;
	}

	public long getWxAgeGroup1() {
		return wxAgeGroup1;
	}

	public void setWxAgeGroup1(long wxAgeGroup1) {
		this.wxAgeGroup1 = wxAgeGroup1;
	}

	public long getWxAgeGroup2() {
		return wxAgeGroup2;
	}

	public void setWxAgeGroup2(long wxAgeGroup2) {
		this.wxAgeGroup2 = wxAgeGroup2;
	}

	public long getWxAgeGroup3() {
		return wxAgeGroup3;
	}

	public void setWxAgeGroup3(long wxAgeGroup3) {
		this.wxAgeGroup3 = wxAgeGroup3;
	}

	public long getWxAgeGroup4() {
		return wxAgeGroup4;
	}

	public void setWxAgeGroup4(long wxAgeGroup4) {
		this.wxAgeGroup4 = wxAgeGroup4;
	}

	public long getWxAgeGroup5() {
		return wxAgeGroup5;
	}

	public void setWxAgeGroup5(long wxAgeGroup5) {
		this.wxAgeGroup5 = wxAgeGroup5;
	}

	public long getWxAgeGroup6() {
		return wxAgeGroup6;
	}

	public void setWxAgeGroup6(long wxAgeGroup6) {
		this.wxAgeGroup6 = wxAgeGroup6;
	}

	public long getAliAgeGroup0() {
		return aliAgeGroup0;
	}

	public void setAliAgeGroup0(long aliAgeGroup0) {
		this.aliAgeGroup0 = aliAgeGroup0;
	}

	public long getAliAgeGroup1() {
		return aliAgeGroup1;
	}

	public void setAliAgeGroup1(long aliAgeGroup1) {
		this.aliAgeGroup1 = aliAgeGroup1;
	}

	public long getAliAgeGroup2() {
		return aliAgeGroup2;
	}

	public void setAliAgeGroup2(long aliAgeGroup2) {
		this.aliAgeGroup2 = aliAgeGroup2;
	}

	public long getAliAgeGroup3() {
		return aliAgeGroup3;
	}

	public void setAliAgeGroup3(long aliAgeGroup3) {
		this.aliAgeGroup3 = aliAgeGroup3;
	}

	public long getAliAgeGroup4() {
		return aliAgeGroup4;
	}

	public void setAliAgeGroup4(long aliAgeGroup4) {
		this.aliAgeGroup4 = aliAgeGroup4;
	}

	public long getAliAgeGroup5() {
		return aliAgeGroup5;
	}

	public void setAliAgeGroup5(long aliAgeGroup5) {
		this.aliAgeGroup5 = aliAgeGroup5;
	}

	public long getAliAgeGroup6() {
		return aliAgeGroup6;
	}

	public void setAliAgeGroup6(long aliAgeGroup6) {
		this.aliAgeGroup6 = aliAgeGroup6;
	}

}
