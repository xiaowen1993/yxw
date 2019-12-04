/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号退费请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class RefundCurRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 4499195346048660483L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 退费方式
	 */
	private String refundMode;
	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;
	/**
	 * 公众服务平台退款订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔订单的退款流水号
	 */
	private String psRefOrdNum;
	/**
	 * 收单机构退款流水号,对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔退款交易的流水号。
	 */
	private String agtRefOrdNum;
	/**
	 * 退款金额,单位：分
	 */
	private String refundAmout;
	/**
	 * 退费时间,格式：医院
	 */
	private String refundTime;
	/**
	 * 退费原因
	 */
	private String refundReason;

	public RefundCurRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param refundMode
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param psRefOrdNum
	 * @param agtRefOrdNum
	 * @param refundAmout
	 * @param refundTime
	 * @param refundReason
	 */
	public RefundCurRegRequest(String branchCode, String refundMode, String hisOrdNum, String psOrdNum, String psRefOrdNum, String agtRefOrdNum,
			String refundAmout, String refundTime, String refundReason) {
		super();
		this.branchCode = branchCode;
		this.refundMode = refundMode;
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.psRefOrdNum = psRefOrdNum;
		this.agtRefOrdNum = agtRefOrdNum;
		this.refundAmout = refundAmout;
		this.refundTime = refundTime;
		this.refundReason = refundReason;
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
	 * @return the hisOrdNum
	 */

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum
	 *            the hisOrdNum to set
	 */

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	/**
	 * @return the psRefOrdNum
	 */

	public String getPsRefOrdNum() {
		return psRefOrdNum;
	}

	/**
	 * @param psRefOrdNum
	 *            the psRefOrdNum to set
	 */

	public void setPsRefOrdNum(String psRefOrdNum) {
		this.psRefOrdNum = psRefOrdNum;
	}

	/**
	 * @return the agtRefOrdNum
	 */

	public String getAgtRefOrdNum() {
		return agtRefOrdNum;
	}

	/**
	 * @param agtRefOrdNum
	 *            the agtRefOrdNum to set
	 */

	public void setAgtRefOrdNum(String agtRefOrdNum) {
		this.agtRefOrdNum = agtRefOrdNum;
	}

	/**
	 * @return the refundAmout
	 */

	public String getRefundAmout() {
		return refundAmout;
	}

	/**
	 * @param refundAmout
	 *            the refundAmout to set
	 */

	public void setRefundAmout(String refundAmout) {
		this.refundAmout = refundAmout;
	}

	/**
	 * @return the refundTime
	 */

	public String getRefundTime() {
		return refundTime;
	}

	/**
	 * @param refundTime
	 *            the refundTime to set
	 */

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * @return the refundReason
	 */

	public String getRefundReason() {
		return refundReason;
	}

	/**
	 * @param refundReason
	 *            the refundReason to set
	 */

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	/**
	 * @return the refundMode
	 */

	public String getRefundMode() {
		return refundMode;
	}

	/**
	 * @param refundMode
	 *            the refundMode to set
	 */

	public void setRefundMode(String refundMode) {
		this.refundMode = refundMode;
	}

	/**
	 * @return the psOrdNum
	 */
	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum the psOrdNum to set
	 */
	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

}
