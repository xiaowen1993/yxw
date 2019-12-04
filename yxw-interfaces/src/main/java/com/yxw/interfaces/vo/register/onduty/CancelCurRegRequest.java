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
 * 挂号-->当天挂号-->当天挂号取消请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class CancelCurRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 3727320643299881975L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 医院预约流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;
	/**
	 * 取消来源
	 */
	private String cancelMode;
	/**
	 * 取消时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String cancelTime;
	/**
	 * 取消原因
	 */
	private String cancelReason;

	public CancelCurRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param cancleMode
	 * @param cancelTime
	 * @param cancelReason
	 */

	public CancelCurRegRequest(String branchCode, String hisOrdNum, String psOrdNum, String cancelMode, String cancelTime, String cancelReason) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.cancelMode = cancelMode;
		this.cancelTime = cancelTime;
		this.cancelReason = cancelReason;
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
	 * @return the cancelTime
	 */

	public String getCancelTime() {
		return cancelTime;
	}

	/**
	 * @param cancelTime
	 *            the cancelTime to set
	 */

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	/**
	 * @return the cancelReason
	 */

	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason
	 *            the cancelReason to set
	 */

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * @return the cancelMode
	 */

	public String getCancelMode() {
		return cancelMode;
	}

	/**
	 * @param cancelMode
	 *            the cancelMode to set
	 */

	public void setCancelMode(String cancelMode) {
		this.cancelMode = cancelMode;
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
