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
 * 检验/检查/体检报告查询-->检查结果详情请求参数封装
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: ExamineDetailRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExamineDetailRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -7646035010220284613L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 检查ID,唯一标识某次检查
	 */
	private String checkId;
	/**
	 * 报告类型
	 */
	private String checkType;

	public ExamineDetailRequest() {
		super();
	}

	/**
	 * 
	 * @param branchCode
	 * @param checkId
	 * @param checkType
	 */
	public ExamineDetailRequest(String branchCode, String checkId, String checkType) {
		super();
		this.branchCode = branchCode;
		this.checkId = checkId;
		this.checkType = checkType;
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
	 * @return the checkId
	 */
	public String getCheckId() {
		return checkId;
	}

	/**
	 * @param checkId the checkId to set
	 */
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	/**
	 * @return the checkType
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType the checkType to set
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

}
