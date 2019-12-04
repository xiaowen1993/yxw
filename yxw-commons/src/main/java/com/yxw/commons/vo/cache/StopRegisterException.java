/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.commons.vo.cache;

import java.io.Serializable;

/**
 * 
 * @Package: com.yxw.cache.vo
 * @ClassName: StopRegisterException
 * @Statement: <p>停诊异常队列信息</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613279919131967198L;

	/**
	 * 医院Code
	 */
	private String hospitalCode;

	/**
	 * 医院代码 ,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;

	/**
	 * 开始时间,格式：YYYY-MM-DD
	 */
	private String beginDate;

	/**
	 * 结束时间 ,格式：YYYY-MM-DD
	 */
	private String endDate;

	/**
	 * 预约方式 ,1：微信,2：支付宝
	 */
	private String regType;

	public StopRegisterException() {
		super();
	}

	public StopRegisterException(String hospitalCode, String branchCode, String beginDate, String endDate, String regType) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchCode = branchCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.regType = regType;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	/**
	 * @return the branchCode
	 */

	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the beginDate
	 */

	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the regType
	 */

	public String getRegType() {
		return regType;
	}

	/**
	 * @param regType
	 *            the regType to set
	 */

	public void setRegType(String regType) {
		this.regType = regType;
	}

}
