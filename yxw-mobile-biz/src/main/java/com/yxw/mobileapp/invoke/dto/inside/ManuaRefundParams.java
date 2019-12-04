/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: StopRegistration
 * @Statement: <p>手动退款输入参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月10日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ManuaRefundParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -701397580117898463L;

	/**
	 * 平台 appId
	 */
	private String appId;

	/**
	 * 父商户Id
	 */
	private String parentAppId;

	/**
	 * 商户Id
	 */
	private String mchId;

	/**
	 * 子商户Id
	 */
	private String subMchId;

	/**
	 * 密钥
	 */
	private String key;

	/**
	 * 退费机构:1 微信  2 支付宝
	 */
	private String tradeMode;

	/**
	 * 支付成功时，医享网返回的公众服务平台订单号，用于唯一标识一笔医院订单
	 */
	private String psOrdNum;

	/**
	 * 第三方支付单号
	 */
	private String agtOrdNum;

	/**
	 * 退款订单号
	 */
	private String refundOrderNo;

	/**
	 * 支付金额
	 */
	private String totalFee;

	/**
	 * 退款金额 单位：分
	 */
	private String refundAmout;

	/**
	 * 退费时间 格式：YYYY-MM-DD HH24:MI:SS
	 */
	private String refundTime;

	/**
	 * 退费原因 
	 */
	private String refundReason;

	/**
	 * 密钥文件名称
	 */
	private String fileName;

	public ManuaRefundParams(String appId, String parentAppId, String mchId, String subMchId, String tradeMode, String psOrdNum,
			String agtOrdNum, String refundOrderNo, String totalFee, String refundAmout, String refundTime, String refundReason) {
		super();
		this.appId = appId;
		this.parentAppId = parentAppId;
		this.mchId = mchId;
		this.subMchId = subMchId;
		this.tradeMode = tradeMode;
		this.psOrdNum = psOrdNum;
		this.agtOrdNum = agtOrdNum;
		this.refundOrderNo = refundOrderNo;
		this.totalFee = totalFee;
		this.refundAmout = refundAmout;
		this.refundTime = refundTime;
		this.refundReason = refundReason;
	}

	public ManuaRefundParams() {
		super();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getParentAppId() {
		return parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getPsOrdNum() {
		return psOrdNum;
	}

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getRefundAmout() {
		return refundAmout;
	}

	public void setRefundAmout(String refundAmout) {
		this.refundAmout = refundAmout;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
