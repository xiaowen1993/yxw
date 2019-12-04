/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.entity;

import java.io.Serializable;
import java.util.Date;

import com.yxw.commons.entity.platform.order.Order;

/**
 * @Package: com.yxw.mobileapp.biz.register.entity
 * @ClassName: RegisterOrder
 * @Statement: <p>挂号订单</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterOrder extends Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6001367282502318221L;

	/**
	 * 诊疗卡类型
	 */
	protected Integer cardType;

	/**
	 * 诊疗卡卡号
	 */
	protected String cardNo;

	/**
	 * 挂号类型,1：预约,2：当天
	 */
	protected Integer regType;

	/**
	 * 医院号源锁号ID
	 */
	protected String lockId;

	public RegisterOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterOrder(String orderNo, Integer orderNoHashVal, String appId, String appCode, String branchId, String hospitalId, String openId,
			String mchOpenId, String mchId, String mchSecret, String subMchId, Integer isSubPay, String mchKey, String relativeOrderNo,
			String orderTitle, String hisOrdNum, String hisRefOrdNum, String mzOrdNum, String agtOrdNum, String agtRefOrdNum, String agtCode,
			Integer payMode, Integer refundMode, Integer onlinePaymentType, Integer payAmout, Date payTime, String payDesc, Integer refundAmout,
			Date refundTime, String refundDesc, String cancelReason, Integer state, String orderDesc, Integer businessCode, String businessName,
			Date orderTime, Integer waitPayTime, String subAppId) {
		super(orderNo, orderNoHashVal, appId, appCode, branchId, hospitalId, openId, mchOpenId, mchId, mchSecret, subMchId, isSubPay, mchKey,
				relativeOrderNo, orderTitle, hisOrdNum, hisRefOrdNum, mzOrdNum, agtOrdNum, agtRefOrdNum, agtCode, payMode, refundMode,
				onlinePaymentType, payAmout, payTime, payDesc, refundAmout, refundTime, refundDesc, cancelReason, state, orderDesc, businessCode,
				businessName, orderTime, waitPayTime, subAppId);
		// TODO Auto-generated constructor stub
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
}
