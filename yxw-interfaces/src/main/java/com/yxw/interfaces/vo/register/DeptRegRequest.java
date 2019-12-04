/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月14日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->科室号源信息查询请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class DeptRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 7791497752242992916L;
	/**
	 * 号源开始日期,格式：YYYY-MM-DD
	 */
	private String beginDate;
	/**
	 * 号源结束日期,格式：YYYY-MM-DD
	 */
	private String endDate;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 科室代码
	 */
	private String deptCode;

	public DeptRegRequest() {
		super();
	}

	/**
	 * @param beginDate
	 * @param endDate
	 * @param branchCode
	 * @param deptCode
	 */

	public DeptRegRequest(String beginDate, String endDate, String branchCode, String deptCode) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.branchCode = branchCode;
		this.deptCode = deptCode;
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
	 * @return the deptCode
	 */

	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}
