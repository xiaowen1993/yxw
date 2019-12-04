/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-20</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.common.vo
 * @ClassName: CommonParamsVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CommonParamsVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5103525083759679435L;
	/**
	 * 就诊人已选择的医院编码
	 */
	protected String hospitalCode;
	protected String hospitalId;
	protected String hospitalName;
	/**
	 * 就诊人已选择的分院编码
	 */
	protected String branchHospitalCode;
	protected String branchHospitalId;
	protected String branchHospitalName;

	/**
	 * 医院对应的接入平台id
	 */
	protected String appId;

	/**
	 * 医院对应的接入平台Code
	 */
	protected String appCode;

	/**
	 * 医院区域代码
	 */
	private String areaCode;

	/**
	 * 业务编码
	 */
	protected String bizCode;

	/**
	 * 就诊人的唯一标示
	 */
	protected String openId;

	protected String reqSource;

	public CommonParamsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonParamsVo(String hospitalCode, String hospitalId, String hospitalName, String branchHospitalCode, String branchHospitalId,
			String branchHospitalName, String appId, String appCode, String bizCode, String openId) {
		super();
		this.hospitalCode = hospitalCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.branchHospitalCode = branchHospitalCode;
		this.branchHospitalId = branchHospitalId;
		this.branchHospitalName = branchHospitalName;
		this.appId = appId;
		this.appCode = appCode;
		this.bizCode = bizCode;
		/**
		 * 有参构造函数增加openId
		 * 2015-07-07 14:36:42 周鉴斌修改
		 */
		this.openId = openId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getBranchHospitalId() {
		return branchHospitalId;
	}

	public void setBranchHospitalId(String branchHospitalId) {
		this.branchHospitalId = branchHospitalId;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
