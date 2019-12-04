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

package com.yxw.interfaces.vo.register.appointment;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->预约挂号-->预约退费确认请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class AckRefundRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -7158826008428216875L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 公众服务平台订单号 ,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔订单的流水号
	 */
	private String psOrdNum;
	/**
	 * 退费方式
	 */
	private String refundMode;

	/**
	 * 退款金额,单位：分
	 */
	private String refundAmout;

	public AckRefundRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param refundMode
	 * @param refundAmout
	 */

	public AckRefundRegRequest(String branchCode, String hisOrdNum, String psOrdNum, String refundMode, String refundAmout) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.refundMode = refundMode;
		this.refundAmout = refundAmout;
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

}
