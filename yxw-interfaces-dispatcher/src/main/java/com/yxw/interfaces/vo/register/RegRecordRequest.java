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
 * 挂号-->挂号记录查询请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class RegRecordRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 7490651211864454245L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
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
	 * 预约方式,见PlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String regMode;
	/**
	 * 开始时间,格式：YYYY-MM-DD
	 */
	private String beginDate;
	/**
	 * 结束时间,格式：YYYY-MM-DD
	 */
	private String endDate;
	/**
	 * 公众服务平台订单号,要求唯一，能标识单独的一笔预约挂号订单。该字段不为空时，查询指定公众服务平台订单号的记录；该字段为空时，查询指定患者，所有的预约记录。
	 */
	private String psOrdNum;

	public RegRecordRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param regChannel
	 * @param beginDate
	 * @param endDate
	 * @param psOrdNum
	 */

	public RegRecordRequest(String branchCode, String patCardType, String patCardNo, String regMode, String beginDate, String endDate, String psOrdNum) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.regMode = regMode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.psOrdNum = psOrdNum;
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
	 * @return the regChannel
	 */

	public String getRegMode() {
		return regMode;
	}

	/**
	 * @param regChannel
	 *            the regChannel to set
	 */

	public void setRegMode(String regMode) {
		this.regMode = regMode;
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
	 * @return the psOrdNum
	 */

	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum
	 *            the psOrdNum to set
	 */

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

}
