package com.yxw.commons.entity.platform.payrefund;

/**
 * 
 * @author YangXuewen
 *
 */
public class AlipayOrderQueryResponse extends OrderQueryResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6552602267749126809L;

	//super.tradeState
	//trade_status
	//WAIT_BUYER_PAY（交易创建，等待买家付款）
	//TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
	//TRADE_SUCCESS（交易支付成功）
	//TRADE_FINISHED（交易结束，不可退款）

	/**
	 * 买家支付宝账号（有脱敏）
	 */
	private String buyerLogonId;

	/**
	 * 实收金额，该金额为本笔交易，商户账户能够实际收到的金额
	 * 单位（分）
	 */
	private String receiptAmount;

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public AlipayOrderQueryResponse() {
		super();
	}

	public AlipayOrderQueryResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
