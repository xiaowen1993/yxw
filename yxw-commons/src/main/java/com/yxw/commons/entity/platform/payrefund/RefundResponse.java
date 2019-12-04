package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

public class RefundResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1910544393394737108L;

	protected String resultCode;

	protected String resultMsg;

	/**
	 * 退款状态
	 */
	protected String refundState;

	/**
	 * 退款成功时间
	 */
	protected String refundSuccessTime;

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

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getRefundSuccessTime() {
		return refundSuccessTime;
	}

	public void setRefundSuccessTime(String refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}

	public RefundResponse() {
		super();
	}

	public RefundResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
