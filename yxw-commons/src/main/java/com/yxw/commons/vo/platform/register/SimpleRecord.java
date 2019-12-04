/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-23</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.register;

import java.io.Serializable;

import com.yxw.commons.hash.SimpleHashTableNameGenerator;

/**
 * @Package: com.yxw.platform.register.vo
 * @ClassName: SimpleRecord
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-23
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6492328617905131478L;

	protected String id;

	/**
	 * 记录名称
	 */
	protected String recordTitle;

	/**
	 * 是否是有效的挂号记录  比如挂号时锁号异常后轮询处理变为取消状态的记录为无效挂号记录
	 * RegisterConstant.RECORD_IS_VALID_TRUE = 1
	 * RegisterConstant.RECORD_IS_VALID_FALSE = 0
	 */
	protected Integer isValid;

	protected Long updateTime;

	/**
	 * 就诊人唯一标示
	 */
	protected String openId;

	/**
	 * 订单的appCode
	 */
	protected String appCode;

	/**
	 * 订单的appId 
	 */
	protected String appId;

	/**
	 * 医院code
	 */
	protected String hospitalCode;

	/**
	 * 分院code
	 */
	protected String branchHospitalCode;

	/**
	 * 挂号记录状态
	 * 对应RegisterConstant中的12种定义 <br>
	 * STATE_NORMAL_HAVING = 0 预约中<br>
	 * STATE_NORMAL_HAD = 1 已预约<br>
	 * STATE_NORMAL_USER_CANCEL = 2; 已取消-用户取消<br>
	 * STATE_NORMAL_PAY_TIMEOUT_CANCEL = 3 ;已取消-支付超过规定时长<br>
	 * STATE_NORMAL_STOP_CURE_CANCEL = 4;已取消-停诊取消<br>
	 * STATE_EXCEPTION_HAVING = 5预约异常(his锁号异常)<br>
	 * STATE_EXCEPTION_PAY = 6支付异常<br>
	 * STATE_EXCEPTION_CANCEL = 7 取消挂号异常 <br> -- 当班挂号不写入轮询 
	 * STATE_EXCEPTION_REFUND = 8 退费异常<br> --  不写入轮询
	 * STATE_EXCEPTION_COMMIT = 9 支付后提交his异常<br>
	 * 5  6  8  9 前端显示标示为 预约失败     预约异常处理  查询 his接口his未生产订单时的状态标志位
	 * 7   前端标示为取消中 
	 */
	protected Integer regStatus;

	/**
	 * 支付状态：1.未支付, 2.已支付  3.已退费 , 4.支付中  5.退费中
	 */
	protected Integer payStatus;

	/**
	 * 医院预约流水号
	 */
	protected String hisOrdNo;

	/**
	 * 退款医院流水号
	 */
	protected String refundHisOrdNo;

	/**
	 * 订单编号
	 */
	protected String orderNo;

	protected Integer orderNoHashVal;

	/**
	 * 退款订单编号
	 */
	protected String refundOrderNo;

	/**
	 * 交易机构支付单号
	 */
	protected String agtOrdNum;

	/**
	 * 交易机构退费单号 
	 */
	protected String agtRefundOrdNum;

	/**
	 * 退费时间
	 */
	protected Long refundTime;

	/**
	 * 支付时间(第3方支付成功后的时间)
	 */
	protected Long payTime;

	/**
	 * 支付超时时间
	 */
	protected Long payTimeoutTime;

	/**
	 * 1：预约,2：当天
	 */
	protected Integer regType;

	private Integer execCount;

	/**
	 * 优惠挂号费,单位：分
	 */
	protected Integer realRegFee;

	/**
	 * 优惠诊疗费 ,单位：分
	 */
	protected Integer realTreatFee;

	/**
	 * 1：微信公众号    2：支付宝服务窗
	 */
	protected Integer platformMode;

	/**
	 * 交易方式  
	 * BizConstant.TRADE_MODE_WEIXIN = 1  微信
	 * BizConstant.TRADE_MODE_ALIPAY = 2 支付宝
	 * TRADE_MODE_APP_WEIXIN_VAL = 3  app-微信
	 * TRADE_MODE_APP_ALIPAY_VAL = 4  app-支付宝
	 * TRADE_MODE_APP_UNIONPAY_VAL = 5 app-银联
	 * BizConstant.TRADE_MODE_UNIONPAY = 6 银联
	 */
	protected Integer tradeMode;

	/**
	 * 绑定诊疗卡类型 1：就诊卡、2：医保卡、3：社保卡、4：住院号
	 */
	protected Integer cardType;

	/**
	 * 诊疗卡号
	 */
	protected String cardNo;

	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	/**
	 * 收据号/预约号
	 */
	protected String receiptNum;

	/**
	 * 支付控制类型  1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 * BizConstant.PAYMENT_TYPE_MUST = 1
	 * BizConstant.PAYMENT_TYPE_NOT_NEED = 2
	 * BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT = 3
	 */
	protected Integer onlinePaymentType;

	/**
	 * 是否是医保支付 1 是 0 否
	 */
	protected Integer isMedicarePayMents;

	/**
	 * 订单生成时间
	 */
	private Long createTime;

	public SimpleRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleRecord(String id, String openId, String hospitalCode, String branchHospitalCode, Integer regStatus,
			Integer isHandleSuccess, Integer handleCount, String handleLog, Integer platformMode, String hisOrdNo, String orderNo,
			Long payTimeoutTime) {
		super();
		this.id = id;
		this.openId = openId;
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.regStatus = regStatus;
		this.platformMode = platformMode;
		this.hisOrdNo = hisOrdNo;
		this.orderNo = orderNo;
		this.payTimeoutTime = payTimeoutTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public Integer getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Integer regStatus) {
		this.regStatus = regStatus;
	}

	public Integer getPlatformMode() {
		return platformMode;
	}

	public void setPlatformMode(Integer platformMode) {
		this.platformMode = platformMode;
	}

	public String getHisOrdNo() {
		return hisOrdNo;
	}

	public void setHisOrdNo(String hisOrdNo) {
		this.hisOrdNo = hisOrdNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getPayTimeoutTime() {
		return payTimeoutTime;
	}

	public void setPayTimeoutTime(Long payTimeoutTime) {
		this.payTimeoutTime = payTimeoutTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Integer getExecCount() {
		if (execCount == null) {
			execCount = 0;
		}
		return execCount;
	}

	public void setExecCount(Integer execCount) {
		this.execCount = execCount;
	}

	public Integer getIsValid() {
		if (isValid == null) {
			isValid = 1;
		}
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getRefundHisOrdNo() {
		return refundHisOrdNo;
	}

	public void setRefundHisOrdNo(String refundHisOrdNo) {
		this.refundHisOrdNo = refundHisOrdNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getAgtRefundOrdNum() {
		return agtRefundOrdNum;
	}

	public void setAgtRefundOrdNum(String agtRefundOrdNum) {
		this.agtRefundOrdNum = agtRefundOrdNum;
	}

	public Integer getRealRegFee() {
		return realRegFee;
	}

	public void setRealRegFee(Integer realRegFee) {
		this.realRegFee = realRegFee;
	}

	public Integer getRealTreatFee() {
		return realTreatFee;
	}

	public void setRealTreatFee(Integer realTreatFee) {
		this.realTreatFee = realTreatFee;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getOrderNoHashVal() {
		return orderNoHashVal;
	}

	public void setOrderNoHashVal(Integer orderNoHashVal) {
		this.orderNoHashVal = orderNoHashVal;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getHashTableName() {
		hashTableName = SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true);
		return hashTableName;
	}

	@SuppressWarnings("unused")
	private void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public Long getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
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

	public String getRecordTitle() {
		return recordTitle;
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle = recordTitle;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getOnlinePaymentType() {
		return onlinePaymentType;
	}

	public void setOnlinePaymentType(Integer onlinePaymentType) {
		this.onlinePaymentType = onlinePaymentType;
	}

	public Integer getOrderType() {
		int orderType = tradeMode;

		return orderType;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getIsMedicarePayMents() {
		return isMedicarePayMents;
	}

	public void setIsMedicarePayMents(Integer isMedicarePayMents) {
		this.isMedicarePayMents = isMedicarePayMents;
	}
}
