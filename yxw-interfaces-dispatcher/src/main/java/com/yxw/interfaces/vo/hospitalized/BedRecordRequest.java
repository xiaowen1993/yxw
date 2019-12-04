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
 * 住院患者服务-->住院记录查询请求参数封装
 * @Package: com.yxw.interfaces.entity.hospitalized
 * @ClassName: BedRecordRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BedRecordRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 8064690141950519236L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	/**
	 * 住院号
	 */
	private String admissionNo;
	
	public BedRecordRequest() {
		super();
	}

	public BedRecordRequest(String branchCode, String patCardType,
			String patCardNo, String admissionNo) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.admissionNo = admissionNo;
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getPatCardType() {
		return patCardType;
	}
	
	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}
	
	public String getPatCardNo() {
		return patCardNo;
	}
	
	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}
	
	public String getAdmissionNo() {
		return admissionNo;
	}
	
	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}
	
}
