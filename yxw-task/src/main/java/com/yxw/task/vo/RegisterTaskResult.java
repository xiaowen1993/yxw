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

import com.yxw.commons.vo.platform.register.SimpleRecord;

/**
 * @Package: com.yxw.platform.quartz.vo
 * @ClassName: CheckRegisterInfo
 * @Statement: <p>挂号相关任务执行返回的结果值对象</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterTaskResult {
	/**
	 * 对应的医院的接口 spring 定义id
	 */
	private String interfaceId;

	private String hospitalId;

	private String hospitalName;

	private String hospitalCode;

	private String branchHospitalCode;

	private List<SimpleRecord> records;

	private SimpleRecord record;

	/**
	 * 任务结果对象
	 */
	private Object taskResult;

	/**
	 * 采集描述信息
	 */
	private String collectCallMsg;

	public RegisterTaskResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterTaskResult(String interfaceId, String hospitalCode, String hospitalId, String hospitalName, String branchHospitalCode,
			List<SimpleRecord> records, Object taskResult, String collectCallMsg) {
		super();
		this.interfaceId = interfaceId;
		this.hospitalCode = hospitalCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.branchHospitalCode = branchHospitalCode;
		this.records = records;
		this.taskResult = taskResult;
		this.collectCallMsg = collectCallMsg;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/**
	 * @return the taskResult
	 */
	public Object getTaskResult() {
		return taskResult;
	}

	/**
	 * @param taskResult the taskResult to set
	 */
	public void setTaskResult(Object taskResult) {
		this.taskResult = taskResult;
	}

	/**
	 * @return the collectCallMsg
	 */
	public String getCollectCallMsg() {
		return collectCallMsg;
	}

	/**
	 * @param collectCallMsg the collectCallMsg to set
	 */
	public void setCollectCallMsg(String collectCallMsg) {
		this.collectCallMsg = collectCallMsg;
	}

	/**
	 * @return the records
	 */
	public List<SimpleRecord> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<SimpleRecord> records) {
		this.records = records;
	}

	/**
	 * @return the record
	 */
	public SimpleRecord getRecord() {
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(SimpleRecord record) {
		this.record = record;
	}

}
