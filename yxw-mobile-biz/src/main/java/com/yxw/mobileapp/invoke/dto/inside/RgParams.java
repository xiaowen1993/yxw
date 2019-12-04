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
 * @Statement: <p>窗口退款输入参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月10日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RgParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -701397580117898463L;

	/**
	 * 平台 appId
	 */
	private String appId;

	/**
	 * 退费机构:1 微信  2 支付宝
	 */
	private String tradeMode;

	/**
	 * 退费类型 1 挂号退费 ,2 门诊退费
	 */
	private String refundType;

	/**
	 * 要求唯一，能标识单独的一笔退费订单（如果医院退费不产生新的订单号，则传空字符串）
	 */
	private String hisNewOrdNum;

	/**
	 * 支付成功时，医享网返回的公众服务平台订单号，用于唯一标识一笔医院订单
	 */
	private String psOrdNum;

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
	 * 退费标志 1全部退费；2部分退费。 默认状态为1
	 */
	private String partialOrAllrefund;

	/**
	 * 消息推送类型 0不推送消息； 1推送模版消息; 默认状态为1
	 */
	private String pushType;

	public RgParams() {
		super();
	}

	public RgParams(String appId, String tradeMode, String refundType, String hisNewOrdNum, String psOrdNum, String refundAmout,
			String refundTime, String refundReason, String partialOrAllrefund, String pushType) {
		super();
		this.appId = appId;
		this.tradeMode = tradeMode;
		this.refundType = refundType;
		this.hisNewOrdNum = hisNewOrdNum;
		this.psOrdNum = psOrdNum;
		this.refundAmout = refundAmout;
		this.refundTime = refundTime;
		this.refundReason = refundReason;
		this.partialOrAllrefund = partialOrAllrefund;
		this.pushType = pushType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getHisNewOrdNum() {
		return hisNewOrdNum;
	}

	public void setHisNewOrdNum(String hisNewOrdNum) {
		this.hisNewOrdNum = hisNewOrdNum;
	}

	public String getPsOrdNum() {
		return psOrdNum;
	}

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
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

	public String getPartialOrAllrefund() {
		return partialOrAllrefund;
	}

	public void setPartialOrAllrefund(String partialOrAllrefund) {
		this.partialOrAllrefund = partialOrAllrefund;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

}
