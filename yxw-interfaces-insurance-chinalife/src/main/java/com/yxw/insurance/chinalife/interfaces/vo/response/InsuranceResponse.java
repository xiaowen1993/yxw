package com.yxw.insurance.chinalife.interfaces.vo.response;

import java.io.Serializable;

public class InsuranceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9089262417857225828L;

	/**
	 * 0成功； 1失败
	 */
	private String resultCode;

	private String resultMessage;

	public InsuranceResponse() {
		super();
	}

	public void addSuccess(String message) {
		this.resultMessage = message;
	}

	public void addError(String message) {
		this.resultMessage = message;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
