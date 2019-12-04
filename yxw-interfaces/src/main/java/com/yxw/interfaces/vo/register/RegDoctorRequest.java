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
 * 挂号-->可挂号医生查询请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class RegDoctorRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -5737697477633086698L;
	/**
	 * 医院代码,医院没有分院则传入空字符串,医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 科室代码,如果为空则医生代码必须为空,返回所有医生的信息
	 */
	private String deptCode;
	/**
	 * 医生代码,如果为空则获取某科室下所有医生的信息
	 */
	private String doctorCode;

	public RegDoctorRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param deptCode
	 * @param doctorCode
	 */

	public RegDoctorRequest(String branchCode, String deptCode, String doctorCode) {
		super();
		this.branchCode = branchCode;
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
