package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

/**
 * 支付异步回调
 * 
 * @author YangXuewen
 *
 */
public class PayAsynResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4185387651432398440L;

	protected String resultCode;

	protected String resultMsg;

	/**
	 * 当前交易状态
	 *   success   支付成功
	 *   refund    退款
	 *   notpay    未支付
	 *   closed    已关闭
	 *   exception 未知状态|异常状态， 需堕入轮询
	 * 
	 * 原始字段和数据:
	 * wechat: 
	 * result_code=SUCCESS/FAIL  此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 * return_msg  返回信息，如非空，为错误原因
	 * return_code=SUCCESS/FAIL  业务结果
	 * err_code  错误代码
	 * err_code_des  错误代码描述
	 * 
	 * alipay:
	 * trade_status
	 * WAIT_BUYER_PAY（交易创建，等待买家付款）
	 * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
	 * TRADE_SUCCESS（交易支付成功）
	 * TRADE_FINISHED（交易结束，不可退款）
	 * 
	 * unionpay:
	 * respCode 00（成功）/ 其他（失败）
	 */
	protected String tradeState;

	/**
	 * 原样返回数据
	 * JSON格式：目前有（hospitalCode|tradeMode）
	 * 
	 * 原始字段：
	 * wechat:   attach
	 * alipay:   passback_params
	 * unionpay: reqReserved
	 */
	protected String attach;

	/**
	 * 商户号
	 * 
	 * 原始字段：
	 * wechat:   mch_id
	 * alipay:   seller_id
	 * unionpay: merId
	 */
	protected String mchId;

	/**
	 * YXW 订单号
	 * 
	 * 原始字段：
	 * wechat:   out_trade_no
	 * alipay:   out_trade_no
	 * unionpay: orderId
	 */
	protected String orderNo;

	/**
	 * 第三方订单号
	 * 
	 * 原始字段：
	 * wechat:   transaction_id
	 * alipay:   trade_no
	 * unionpay: queryId
	 */
	protected String agtOrderNo;

	/**
	 * 支付金额
	 * 统一单位（分）
	 * 
	 * 原始字段：
	 * wechat:   total_fee（分）
	 * alipay:   total_amount（元）
	 * unionpay: txnAmt（分）
	 */
	protected String totalFee;

	/**
	 * 支付完成时间|交易付款时间
	 * 统一格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * 原始字段：
	 * wechat:   time_end（yyyyMMddHHmmss）
	 * alipay:   gmt_payment（yyyy-MM-dd HH:mm:ss）
	 * unionpay: txnTime（yyyyMMddHHmmss）
	 */
	protected String tradeTime;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAgtOrderNo() {
		return agtOrderNo;
	}

	public void setAgtOrderNo(String agtOrderNo) {
		this.agtOrderNo = agtOrderNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public PayAsynResponse() {
		super();
	}

	public PayAsynResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
