package com.yxw.commons.entity.platform.payrefund;

/**
 * 支付宝支付异步回调
 * 
 * @author YangXuewen
 *
 */
public class AlipayAsynResponse extends PayAsynResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5829360990341677856L;

	/**
	 * app_id
	 */
	private String appId;
	
	/**
	 * buyer_id
	 */
	private String openId;

	/**
	 * 买家支付宝账号
	 * buyer_logon_id
	 */
	private String buyerLogonId;
	/**
	 * 卖家支付宝账号
	 * seller_email
	 */
	private String sellerEmail;

	/**
	 * 订单标题
	 * subject
	 */
	private String subject;

	/**
	 * 商品描述
	 * body
	 */
	private String body;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AlipayAsynResponse() {
		super();
	}

	public AlipayAsynResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
