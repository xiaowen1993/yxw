package com.yxw.commons.entity.mobile.biz.inpatient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.MessagePushParamsVo;

/**
 * 住院押金补缴实体表
 * 
 * @Package: com.yxw.mobileapp.biz.inpatient.entity
 * @ClassName: InpatientRecord
 * @Statement:
 *             <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-9-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DepositRecord extends Record {

	private static final long serialVersionUID = 6054631660286892000L;

	/**
	 * 押金补缴订单状态
	 */
	private Integer depositStatus;

	/**
	 * 住院号
	 */
	private String admissionNo;

	/**
	 * 住院次数(与住院号配合使用，效果同住院编号)
	 */
	private Integer inTime;

	/**
	 * 住院编号(表示某一次的住院)
	 */
	private String inpatientId;

	/**
	 * 住院总金额
	 */
	private String totalFee;

	/**
	 * 已缴金额
	 */
	private String paidFee;

	/**
	 * 本次支付金额
	 */
	private String payFee;

	/**
	 * 本次支付金额，以元为单位
	 */
	private String realYuanPayFee;

	/**
	 * 支付前余额，住院费用查询接口中返回
	 */
	private String balanceBeforePay;

	/**
	 * 支付后余额，住院押金补缴支付接口中返回 PS: 支付接口异常，则可以在押金记录接口查询返回
	 */
	private String balanceAfterPay;

	/**
	 * 条形码
	 */
	private String barcode;

	/**
	 * 支付模式
	 */
	private Integer payMode;

	/**
	 * 订单创建时间
	 */
	private Long createTime;

	/**
	 * 是否异常 1 有 0 没有 BizConstant.IS_HAPPEN_EXCEPTION_YES
	 * BizConstant.IS_HAPPEN_EXCEPTION_NO
	 */
	private Integer isException;

	/**
	 * 是否处理成功 HANDLED_EXCEPTION_SUCCESS = 1 HANDLED_EXCEPTION_FAILURE = 0
	 */
	private Integer isHandleSuccess;

	/**
	 * 处理次数 大于3次的异常不再处理
	 */
	private Integer handleCount;

	/**
	 * 处理日志
	 */
	private String handleLog;

	/**
	 * 是否有效的订单
	 */
	private Integer isValid;

	/**
	 * 医院返回的收据号
	 */
	private String receiptNum;

	/**
	 * 流水号
	 */
	private String hisOrdNo;

	/**
	 * 查询的开始时间（只在已缴费列表部分有）
	 */
	private Long queryBeginTime;

	/**
	 * 查询的结束时间
	 */
	private Long queryEndTime;

	/**
	 * 退费金额（支持部分退费）
	 */
	private String refundFee;

	/**
	 * 交易类型 1：支付 2：退费 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	private String tradeType;

	/**
	 * 交易时间 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	private String tradeTime;

	public Integer getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(String admissionNo) {
		this.admissionNo = admissionNo;
	}

	public Integer getInTime() {
		return inTime;
	}

	public void setInTime(Integer inTime) {
		this.inTime = inTime;
	}

	public String getInpatientId() {
		return inpatientId;
	}

	public void setInpatientId(String inpatientId) {
		this.inpatientId = inpatientId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getBalanceBeforePay() {
		return balanceBeforePay;
	}

	public void setBalanceBeforePay(String balanceBeforePay) {
		this.balanceBeforePay = balanceBeforePay;
	}

	public String getBalanceAfterPay() {
		return balanceAfterPay;
	}

	public void setBalanceAfterPay(String balanceAfterPay) {
		this.balanceAfterPay = balanceAfterPay;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public Integer getIsHandleSuccess() {
		return isHandleSuccess;
	}

	public void setIsHandleSuccess(Integer isHandleSuccess) {
		this.isHandleSuccess = isHandleSuccess;
	}

	public Integer getHandleCount() {
		return handleCount;
	}

	public void setHandleCount(Integer handleCount) {
		this.handleCount = handleCount;
	}

	public String getHandleLog() {
		return handleLog;
	}

	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getReceiptNum() {
		if (StringUtils.isBlank(receiptNum) || "null".equals(receiptNum)) {
			receiptNum = "";
		}
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getHisOrdNo() {
		if (StringUtils.isBlank(hisOrdNo) || "null".equals(hisOrdNo)) {
			hisOrdNo = "";
		}
		return hisOrdNo;
	}

	public void setHisOrdNo(String hisOrdNo) {
		this.hisOrdNo = hisOrdNo;
	}

	public Long getQueryBeginTime() {
		return queryBeginTime;
	}

	public void setQueryBeginTime(Long queryBeginTime) {
		this.queryBeginTime = queryBeginTime;
	}

	public Long getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Long queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Override
	public Integer getPayTotalFee() {
		return Integer.parseInt(payFee);
	}

	@Override
	public Integer getRefundTotalFee() {
		if (StringUtils.isNotBlank(this.refundFee)) {
			return Integer.parseInt(this.refundFee);
		} else {
			if (StringUtils.isNotBlank(payFee)) {
				return Integer.parseInt(payFee);
			} else {
				return 0;
			}
		}
	}

	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);
		params.setOpenId(this.openId);

		params.setPlatformType(this.appCode);

		@SuppressWarnings("unchecked")
		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);

		// 跳到缴费明细部分的跳转需要参数
		// BizConstant.URL_PARAM_CHAR_FIRST : ?
		// BizConstant.URL_PARAM_CHAR_ASSGIN : =
		// BizConstant.URL_PARAM_CHAR_CONCAT : &
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.ORDERNO_KEY).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.orderNo).concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.OPENID)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.openId);

		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);

		return params;
	}

	public String getRealYuanPayFee() {
		if (this.payFee != null) {
			BigDecimal source = new BigDecimal(Integer.parseInt(this.payFee));
			BigDecimal divisor = new BigDecimal(100);
			realYuanPayFee = source.divide(divisor).toString();
		}
		return realYuanPayFee;
	}

	public void setRealYuanPayFee(String realYuanPayFee) {
		this.realYuanPayFee = realYuanPayFee;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getPaidFee() {
		return paidFee;
	}

	public void setPaidFee(String paidFee) {
		this.paidFee = paidFee;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getDepositRecordHashTable(this.openId, true);
		}
		return hashTableName;
	}

}
