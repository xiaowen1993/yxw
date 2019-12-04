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
 * 检验/检查/体检报告查询-->检验报告详情请求参数封装
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: InspectDetailRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InspectDetailRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -662676933628752899L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	/**
	 * 检验ID,唯一标识某次检验
	 */
	private String inspectId;

	public InspectDetailRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param checkId
	 * @param applyNo
	 */
	public InspectDetailRequest(String branchCode, String inspectId) {
		super();
		this.branchCode = branchCode;
		this.inspectId = inspectId;
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
	 * @return the inspectId
	 */
	public String getInspectId() {
		return inspectId;
	}

	/**
	 * @param inspectId the inspectId to set
	 */
	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

}
