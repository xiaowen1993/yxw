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
package com.yxw.task.vo;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.yxw.commons.vo.platform.Dept;
import com.yxw.interfaces.vo.register.RegDoctor;

/**
 * @Package: com.yxw.platform.quartz.vo
 * @ClassName: HospitalBaseInfo
 * @Statement: <p>
 *             科室/医生信息vo
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalBaseInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 357529722654620674L;

	/**
	 * 对应的医院的接口 spring 定义id
	 */
	private String interfaceId;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 医院对应的编码
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 医院的一级科室信息
	 */
	private List<Dept> levelOnedepts;

	/**
	 * 2级科室列表展开时,第一个1级科室的2级科室目录
	 */
	private List<Dept> firstSubDepts;

	/**
	 * 医院的二级科室信息
	 */
	private ConcurrentHashMap<String, List<Dept>> levelTwodepts = new ConcurrentHashMap<String, List<Dept>>();

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	/**
	 * 采集描述信息
	 */
	private String collectCallMsg;

	/**
	 * 医院的所有医生信息
	 */
	private List<RegDoctor> doctors;

	public HospitalBaseInfo(String interfaceId, List<Dept> depts, List<RegDoctor> doctors, String collectCallMsg) {
		super();
		this.interfaceId = interfaceId;
		this.collectCallMsg = collectCallMsg;
		this.doctors = doctors;
	}

	public HospitalBaseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public List<RegDoctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<RegDoctor> doctors) {
		this.doctors = doctors;
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

	public List<Dept> getLevelOnedepts() {
		return levelOnedepts;
	}

	public void setLevelOnedepts(List<Dept> levelOnedepts) {
		this.levelOnedepts = levelOnedepts;
	}

	public ConcurrentHashMap<String, List<Dept>> getLevelTwodepts() {
		return levelTwodepts;
	}

	public void setLevelTwodepts(ConcurrentHashMap<String, List<Dept>> levelTwodepts) {
		this.levelTwodepts = levelTwodepts;
	}

	public List<Dept> getFirstSubDepts() {
		return firstSubDepts;
	}

	public void setFirstSubDepts(List<Dept> firstSubDepts) {
		this.firstSubDepts = firstSubDepts;
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

	public String getCollectCallMsg() {
		return collectCallMsg;
	}

	public void setCollectCallMsg(String collectCallMsg) {
		if (StringUtils.isNotBlank(this.collectCallMsg)) {
			this.collectCallMsg = this.collectCallMsg.concat(collectCallMsg);
		} else {
			this.collectCallMsg = collectCallMsg;
		}
	}
}
