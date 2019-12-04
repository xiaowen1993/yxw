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
 * 挂号-->可挂号科室查询请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class RegDeptRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -1655947903123671054L;
	/**
	 * 医院代码,医院没有分院则传入空字符串,医院不存在分院时不允许为空
	 */
	private String branchCode;

	/**
	 * 父科室代码
	 */
	private String parentDeptCode;

	public RegDeptRequest() {
		super();
	}

	/**
	 * @param parentDeptCode
	 */

	public RegDeptRequest(String parentDeptCode) {
		super();
		this.parentDeptCode = parentDeptCode;
	}

	/**
	 * @param branchCode
	 * @param parentDeptCode
	 */

	public RegDeptRequest(String branchCode, String parentDeptCode) {
		super();
		this.branchCode = branchCode;
		this.parentDeptCode = parentDeptCode;
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
	 * @return the parentDeptCode
	 */

	public String getParentDeptCode() {
		return parentDeptCode;
	}

	/**
	 * @param parentDeptCode
	 *            the parentDeptCode to set
	 */

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

}
