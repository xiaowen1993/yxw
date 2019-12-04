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

package com.yxw.interfaces.vo.register.stopreg;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->停诊-->医生停诊订单处理确认请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class ConfirmStopRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = -735689475325405986L;

	/**
	 * 医院代码 ,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;

	/**
	 * 医院挂号订单流水号
	 */
	private String hisOrdNum;

	/**
	 * 公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;

	/**
	 * 公众服务平台退款订单号 ,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔订单的退款流水号
	 */
	private String psRefOrdNum;

	/**
	 * 收单机构退款流水号,对应收单机构（如财付通、支付宝、银联等机构）用于标识一笔退款交易的流水号。支付宝该输入项为空字符串
	 */
	private String agtRefOrdNum;

	public ConfirmStopRegRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param psRefOrdNum
	 * @param agtRefOrdNum
	 */

	public ConfirmStopRegRequest(String branchCode, String hisOrdNum, String psOrdNum, String psRefOrdNum, String agtRefOrdNum) {
		super();
		this.branchCode = branchCode;
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.psRefOrdNum = psRefOrdNum;
		this.agtRefOrdNum = agtRefOrdNum;
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

}
