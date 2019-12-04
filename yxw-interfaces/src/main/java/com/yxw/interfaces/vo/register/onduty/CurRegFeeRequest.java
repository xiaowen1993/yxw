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

package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号费用减免查询请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class CurRegFeeRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 6252389527665745759L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院存在分院时不允许为空
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
	 * 挂号费,单位：分
	 */
	private String regFee;
	/**
	 * 诊疗费 ,单位：分
	 */
	private String treatFee;

	public CurRegFeeRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param regFee
	 * @param treatFee
	 */

	public CurRegFeeRequest(String branchCode, String patCardType, String patCardNo, String regFee, String treatFee) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.regFee = regFee;
		this.treatFee = treatFee;
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
	 * @return the patCardType
	 */

	public String getPatCardType() {
		return patCardType;
	}

	/**
	 * @param patCardType
	 *            the patCardType to set
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
	 * @param patCardNo
	 *            the patCardNo to set
	 */

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	/**
	 * @return the regFee
	 */

	public String getRegFee() {
		return regFee;
	}

	/**
	 * @param regFee
	 *            the regFee to set
	 */

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	/**
	 * @return the treatFee
	 */

	public String getTreatFee() {
		return treatFee;
	}

	/**
	 * @param treatFee
	 *            the treatFee to set
	 */

	public void setTreatFee(String treatFee) {
		this.treatFee = treatFee;
	}

}
