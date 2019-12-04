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
package com.yxw.easyhealth.biz.outside.entity.in;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.biz.outside.entity
 * @ClassName: SubRefundParams
 * @Statement: <p>退费controller接口参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SubRefundParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 95311897270139399L;

	/**
	 * 	微信/支付宝appid
	 */
	private String appId;

	/**
	 * 退费机构:1 微信  2 支付宝
	 */
	private String agencyType;

	/**
	 * 医院调用的时候可以不传
	 */
	private String sub_mch_id;

	/**
	 * 1 当班挂号退费
	 * 2 预约挂号退费  
	 * 3 门诊退费 
	 * 4.挂号停诊退费 
	 * 5.门诊部分退费
	 */
	private String payType;

	/**
	 * 要求唯一，能标识单独的一笔退费订单（如果医院退费不产生新的订单号，则传空字符串）
	 */
	private String hisNewOrdNum;

	/**
	 * 支付成功时，医享网返回的公众服务平台订单号，用于唯一标识一笔医院订单
	 */
	private String psOrdNum;

	/**
	 * 单位：分
	 */
	private String refundAmout;

	/**
	 * 格式：YYYY-MM-DD HH24:MI:SS
	 */
	private String refundTime;

	/**
	 * 用utf-8格式
	 */
	private String refundReason;

	/**
	 * 1全部退费；
	 * 2部分退费。
	 * 默认状态为1
	 */
	private String refundType;

	/**
	 * 0不推送消息；
	 * 1推送模版消息，根据paytype进行推 送对应模版消息类型
	 * 预约挂号退费==》预约退费通知；
	 * 其他退费==》客服消息；
	 * 2推送客服消息；
	 * 说明：对应模版消息，需要该公众号配置有对应的模版，否则会消息发送失败。
	 * 默认状态为1
	 */
	private String pushType;

	/**
	 * 判断调用退费的来源：
	 * 1.前端调用
	 * 2.YY后台调用
	 */
	private String comeFrom;

	public SubRefundParams() {
		super();
	}

	public SubRefundParams(String appId, String agencyType, String sub_mch_id, String payType, String hisNewOrdNum, String psOrdNum,
			String refundAmout, String refundTime, String refundReason, String refundType, String pushType, String comeFrom) {
		super();
		this.appId = appId;
		this.agencyType = agencyType;
		this.sub_mch_id = sub_mch_id;
		this.payType = payType;
		this.hisNewOrdNum = hisNewOrdNum;
		this.psOrdNum = psOrdNum;
		this.refundAmout = refundAmout;
		this.refundTime = refundTime;
		this.refundReason = refundReason;
		this.refundType = refundType;
		this.pushType = pushType;
		this.comeFrom = comeFrom;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

}
