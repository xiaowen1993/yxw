/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.hospitalized;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->出院小结查询请求参数封装
 * @Package: com.yxw.interfaces.entity.hospitalized
 * @ClassName: OutSummaryRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OutSummaryRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 84005873902463477L;
	
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 住院记录ID
	 */
	private String patientId;
	/**
	 * 住院号
	 */
	private String admissionNo;
	/**
	 * 住院次数
	 */
	private String inTime;
	
	public OutSummaryRequest() {
		super();
	}

	public OutSummaryRequest(String branchCode, String patientId,
			String admissionNo, String inTime) {
		super();
		this.branchCode = branchCode;
		this.patientId = patientId;
		this.admissionNo = admissionNo;
		this.inTime = inTime;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

}
