/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-23</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

import java.io.Serializable;

/**
 * 
 * @Package: com.yxw.cache.vo
 * @ClassName: HospitalInfoByEasyHealthVo
 * @Statement: <p>健康易功能医院关系</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月29日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalInfoByEasyHealthVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6586036093141474267L;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 医院区域代码
	 */
	private String areaCode;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 排序
	 */
	private Integer sortIndex;

	/**
	 * appId(健康易appId)
	 */
	private String appId;

	/**
	 * appCode(健康易appCode)
	 */
	private String appCode;

	/**
	 * 功能Code
	 */
	private String bizCode;

	public HospitalInfoByEasyHealthVo() {
		super();
	}

	public HospitalInfoByEasyHealthVo(String hospitalId, String hospitalCode, String hospitalName, String areaCode, String appId, String bizCode,
			String appCode) {
		super();
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.areaCode = areaCode;
		this.appId = appId;
		this.bizCode = bizCode;
		this.appCode = appCode;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
