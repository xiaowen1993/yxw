/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月14日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.mycenter;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 个人中心-->患者信息查询请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class MZPatientRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 5324273170407756063L;
	/**
	 * 医院代码,医院没有分院则传空字符串；医院存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 患者姓名,当诊疗卡类型为”身份证“时，该项必须传值
	 */
	private String patName;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号码
	 */
	private String patCardNo;
	/**
	 * 证件类型,见IdType,患者类型为儿童时，该项允许为空
	 * @see com.yxw.interfaces.constants.IdType
	 */
	private String patIdType;
	/**
	 * 证件号码,患者类型为儿童时，该项允许为空
	 */
	private String patIdNo;
	/**
	 * 性别,见GenderType
	 * @see com.yxw.interfaces.constants.GenderType
	 */
	private String patSex;
	/**
	 * 出生日期
	 */
	private String patBirth;
	/**
	 * 电话
	 */
	private String patMobile;
	/**
	 * 地址
	 */
	private String patAddress;
	/**
	 * 联系人姓名,患者类型为儿童时，该项必输
	 */
	private String guardName;
	/**
	 * 联系人证件类型,见IdType
	 * @see com.yxw.interfaces.constants.IdType
	 */
	private String guardIdType;
	/**
	 * 联系人证件号码
	 */
	private String guardIdNo;
	/**
	 * 联系人电话
	 */
	private String guardMobile;
	/**
	 * 联系人地址
	 */
	private String guardAddress;

	public MZPatientRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patName
	 * @param patCardType
	 * @param patCardNo
	 * @param patIdType
	 * @param patIdNo
	 * @param patSex
	 * @param patBirth
	 * @param patAddress
	 * @param patMobile
	 * @param guardName
	 * @param guardIdType
	 * @param guardIdNo
	 * @param guardMobile
	 * @param guardAddress
	 */
	public MZPatientRequest(String branchCode, String patName, String patCardType, String patCardNo, String patIdType, String patIdNo, String patSex,
			String patBirth, String patAddress, String patMobile, String guardName, String guardIdType, String guardIdNo, String guardMobile,
			String guardAddress) {
		super();
		this.branchCode = branchCode;
		this.patName = patName;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.patIdType = patIdType;
		this.patIdNo = patIdNo;
		this.patSex = patSex;
		this.patBirth = patBirth;
		this.patAddress = patAddress;
		this.patMobile = patMobile;
		this.guardName = guardName;
		this.guardIdType = guardIdType;
		this.guardIdNo = guardIdNo;
		this.guardMobile = guardMobile;
		this.guardAddress = guardAddress;
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
	 * @return the patName
	 */

	public String getPatName() {
		return patName;
	}

	/**
	 * @param patName
	 *            the patName to set
	 */

	public void setPatName(String patName) {
		this.patName = patName;
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
	 * @return the patSex
	 */
	public String getPatSex() {
		return patSex;
	}

	/**
	 * @param patSex the patSex to set
	 */
	public void setPatSex(String patSex) {
		this.patSex = patSex;
	}

	/**
	 * @return the patBirth
	 */
	public String getPatBirth() {
		return patBirth;
	}

	/**
	 * @param patBirth the patBirth to set
	 */
	public void setPatBirth(String patBirth) {
		this.patBirth = patBirth;
	}

	/**
	 * @return the patAddress
	 */
	public String getPatAddress() {
		return patAddress;
	}

	/**
	 * @param patAddress the patAddress to set
	 */
	public void setPatAddress(String patAddress) {
		this.patAddress = patAddress;
	}

	/**
	 * @return the patMobile
	 */
	public String getPatMobile() {
		return patMobile;
	}

	/**
	 * @param patMobile the patMobile to set
	 */
	public void setPatMobile(String patMobile) {
		this.patMobile = patMobile;
	}

	/**
	 * @return the patIdType
	 */
	public String getPatIdType() {
		return patIdType;
	}

	/**
	 * @param patIdType the patIdType to set
	 */
	public void setPatIdType(String patIdType) {
		this.patIdType = patIdType;
	}

	/**
	 * @return the patIdNo
	 */
	public String getPatIdNo() {
		return patIdNo;
	}

	/**
	 * @param patIdNo the patIdNo to set
	 */
	public void setPatIdNo(String patIdNo) {
		this.patIdNo = patIdNo;
	}

	/**
	 * @return the guardName
	 */
	public String getGuardName() {
		return guardName;
	}

	/**
	 * @param guardName the guardName to set
	 */
	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	/**
	 * @return the guardIdType
	 */
	public String getGuardIdType() {
		return guardIdType;
	}

	/**
	 * @param guardIdType the guardIdType to set
	 */
	public void setGuardIdType(String guardIdType) {
		this.guardIdType = guardIdType;
	}

	/**
	 * @return the guardIdNo
	 */
	public String getGuardIdNo() {
		return guardIdNo;
	}

	/**
	 * @param guardIdNo the guardIdNo to set
	 */
	public void setGuardIdNo(String guardIdNo) {
		this.guardIdNo = guardIdNo;
	}

	/**
	 * @return the guardMobile
	 */
	public String getGuardMobile() {
		return guardMobile;
	}

	/**
	 * @param guardMobile the guardMobile to set
	 */
	public void setGuardMobile(String guardMobile) {
		this.guardMobile = guardMobile;
	}

	/**
	 * @return the guardAddress
	 */
	public String getGuardAddress() {
		return guardAddress;
	}

	/**
	 * @param guardAddress the guardAddress to set
	 */
	public void setGuardAddress(String guardAddress) {
		this.guardAddress = guardAddress;
	}

}
