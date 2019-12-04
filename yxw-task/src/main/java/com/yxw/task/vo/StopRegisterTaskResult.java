/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-18</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.task.vo;

import java.util.List;

import com.yxw.interfaces.vo.register.stopreg.StopReg;


/**
 * 
 * @Package: com.yxw.platform.quartz.vo
 * @ClassName: StopRegisterTaskResult
 * @Statement: <p>停诊返回相关数据</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterTaskResult {
	/**
	 * 对应的医院的接口 spring 定义id
	 */
	private String interfaceId;

	private String hospitalCode;

	private String branchHospitalCode;

	private String appCode;

	private List<StopReg> stopRegs;

	public StopRegisterTaskResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
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

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public List<StopReg> getStopRegs() {
		return stopRegs;
	}

	public void setStopRegs(List<StopReg> stopRegs) {
		this.stopRegs = stopRegs;
	}

}
