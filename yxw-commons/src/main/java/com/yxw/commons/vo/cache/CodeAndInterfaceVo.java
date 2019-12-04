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
 * @Package: com.yxw.platform.cache.vo
 * @ClassName: CodeAndInterfaceVo
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-23
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CodeAndInterfaceVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9092659956977972918L;

	/**
	 * 医院code
	 */
	private String hospitalCode;
	private String hospitalId;
	private String hospitalName;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;
	private String branchHospitalId;
	private String branchName;
	private String branchAddress;

	/**
	 * 分院远程调用对应的Spring interface id
	 */
	private String interfaceId;

	/**
	 * 状态:0禁用，1启用
	 */
	private int status;

	/**
	 * 支付属性配置ID 用作退款查询密钥 modify 2015年6月25日 20:50:08 周鉴斌
	 */
	private String paysetId;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 医院区域代码
	 */
	private String areaCode;
	private String areaName;

	public CodeAndInterfaceVo(String hospitalCode, String branchHospitalCode, String serviceId) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.interfaceId = serviceId;
	}

	public CodeAndInterfaceVo() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "hospitalCode：" + this.hospitalCode + "  branchHospitalCode:" + this.branchHospitalCode + "  interfaceId:" + this.interfaceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getBranchHospitalId() {
		return branchHospitalId;
	}

	public void setBranchHospitalId(String branchHospitalId) {
		this.branchHospitalId = branchHospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getPaysetId() {
		return paysetId;
	}

	public void setPaysetId(String paysetId) {
		this.paysetId = paysetId;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
