package com.yxw.commons.entity.platform.payrefund;

/**
 * 微信支付异步回调
 * 
 * @author YangXuewen
 *
 */
public class WechatPayAsynResponse extends PayAsynResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4621508727532642803L;

	/**
	 * appid
	 */
	private String appId;
	
	/**
	 * openid
	 */
	private String openId;
	
	/**
	 * trade_type
	 */
	private String tradeType;

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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public WechatPayAsynResponse() {
		super();
	}

	public WechatPayAsynResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
