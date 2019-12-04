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

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->号源信息请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class RegInfoRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -6058272882806587319L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 号源开始日期 ,格式：YYYY-MM-DD
	 */
	private String beginDate;

	/**
	 * 号源结束日期 ,格式：YYYY-MM-DD
	 */
	private String endDate;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 医生/专科代码,该字段为空，则查询指定科室下的医生/专科号源信息；该字段不为空时，此时查询指定科室下指定医生/专科的号源信息
	 */
	private String doctorCode;

	public RegInfoRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param beginDate
	 * @param endDate
	 * @param deptCode
	 * @param doctorCode
	 */

	public RegInfoRequest(String branchCode, String beginDate, String endDate, String deptCode, String doctorCode) {
		super();
		this.branchCode = branchCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.deptCode = deptCode;
		this.doctorCode = doctorCode;
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

	/**
	 * @return the doctorCode
	 */

	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode
	 *            the doctorCode to set
	 */

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

}
