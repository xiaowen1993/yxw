package com.yxw.solr.vo;

public class BizInfoRequest extends BaseRequest {

	private static final long serialVersionUID = -3965536660326493578L;

	private Integer tradeMode;
	
	private String appId;

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
