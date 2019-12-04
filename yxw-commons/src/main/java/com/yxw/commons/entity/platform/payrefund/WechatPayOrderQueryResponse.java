package com.yxw.commons.entity.platform.payrefund;

/**
 * 
 * @author YangXuewen
 *
 */
public class WechatPayOrderQueryResponse extends OrderQueryResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6552602267749126809L;

	//super.tradeState
	//SUCCESS—支付成功
	//REFUND—转入退款
	//NOTPAY—未支付
	//CLOSED—已关闭
	//REVOKED—已撤销（刷卡支付）
	//USERPAYING--用户支付中
	//PAYERROR--支付失败(其他原因，如银行返回失败)

	/**
	 * JSAPI，NATIVE，APP，MICROPAY
	 */
	private String tradeType;

	/**
	 * 付款银行类型，采用字符串类型的银行标识
	 * 如：
	 * ICBC_DEBIT 工商银行(借记卡)
	 * ICBC_CREDIT 工商银行(信用卡)
	 * ABC_DEBIT 农业银行(借记卡)
	 */
	private String bankType;

	/**
	 * 附加数据，原样返回
	 */
	private String attach;

	/**
	 * 交易状态描述
	 */
	private String tradeStateDesc;

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}

	public WechatPayOrderQueryResponse() {
		super();
	}

	public WechatPayOrderQueryResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
