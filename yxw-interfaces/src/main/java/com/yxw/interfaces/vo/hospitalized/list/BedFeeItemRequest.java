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
package com.yxw.interfaces.vo.hospitalized.list;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院费用清单-->清单明细列表请求参数封装
 * @Package: com.yxw.interfaces.entity.hospitalized.list
 * @ClassName: BedFeeItemRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BedFeeItemRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -9060427748804341293L;

	/**
	 * 费用日期,格式：YYYY-MM-DD
	 */
	private String costDate;
	/**
	 * 医院代码
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
	/**
	 * 费用类别
	 */
	private String costType;
	
	public BedFeeItemRequest() {
		super();
	}

	public BedFeeItemRequest(String costDate, String branchCode,
			String patientId, String admissionNo, String inTime, String costType) {
		super();
		this.costDate = costDate;
		this.branchCode = branchCode;
		this.patientId = patientId;
		this.admissionNo = admissionNo;
		this.inTime = inTime;
		this.costType = costType;
	}

	public String getCostDate() {
		return costDate;
	}

	public void setCostDate(String costDate) {
		this.costDate = costDate;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}
	
}
