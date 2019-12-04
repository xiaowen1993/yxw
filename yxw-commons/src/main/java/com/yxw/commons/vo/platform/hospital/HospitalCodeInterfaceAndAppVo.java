/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.hospital;

import java.io.Serializable;

/**
 * 
 * @Package: com.yxw.platform.hospital.vo
 * @ClassName: HospitalCodeInterfaceAndAppVo
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalCodeInterfaceAndAppVo extends HospitalCodeAndAppVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835381006058990062L;
	/**
	 * 接口ID
	 */
	private String interfaceId;

	private String branchHospitalName;

	private String branchHospitalCode;

	public HospitalCodeInterfaceAndAppVo() {
		super();
	}

	public HospitalCodeInterfaceAndAppVo(String interfaceId, String branchHospitalName, String branchHospitalCode) {
		super();
		this.interfaceId = interfaceId;
		this.branchHospitalName = branchHospitalName;
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

}
