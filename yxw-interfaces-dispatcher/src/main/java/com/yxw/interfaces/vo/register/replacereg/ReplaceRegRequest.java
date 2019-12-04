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

package com.yxw.interfaces.vo.register.replacereg;

import com.yxw.interfaces.vo.Reserve;

import java.io.Serializable;

/**
 * 挂号-->替诊-->医生替诊信息请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class ReplaceRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 6780335770035053628L;

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

	public ReplaceRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param beginDate
	 * @param endDate
	 * @param regType
	 */

	public ReplaceRegRequest(String branchCode, String beginDate, String endDate, String regType) {
		super();
		this.branchCode = branchCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.regType = regType;
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
