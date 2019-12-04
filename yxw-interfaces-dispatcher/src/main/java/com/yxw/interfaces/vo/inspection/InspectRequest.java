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
package com.yxw.interfaces.vo.inspection;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 检验/检查/体检报告查询-->检验报告列表请求参数封装
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: InspectRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InspectRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 8350923684242484921L;

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
	 * 住院号,用诊疗卡查询时，住院号可传空；用住院号查询时，诊疗卡和诊疗卡类型可为空
	 */
	private String admissionNo;

	/**
	 * 开始日期,格式：YYYY-MM-DD
	 */
	private String beginDate;

	/**
	 * 结束日期,格式：YYYY-MM-DD
	 */
	private String endDate;

	public InspectRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param admissionNo
	 * @param beginDate
	 * @param endDate
	 */
	public InspectRequest(String branchCode, String patCardType, String patCardNo, String admissionNo, String beginDate, String endDate) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.admissionNo = admissionNo;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the patCardType
	 */
	public String getPatCardType() {
		return patCardType;
	}

	/**
	 * @param patCardType the patCardType to set
	 */
	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	/**
	 * @return the patCardNo
	 */
	public String getPatCardNo() {
		return patCardNo;
	}

	/**
	 * @param patCardNo the patCardNo to set
	 */
	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	/**
	 * @return the admissionNo
	 */
	public String getAdmissionNo() {
		return admissionNo;
	}

	/**
	 * @param admissionNo the admissionNo to set
	 */
	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
