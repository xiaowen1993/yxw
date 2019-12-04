package com.yxw.outside.vo;

import java.io.Serializable;

import com.yxw.outside.constant.ResultConstant;

public class YxwOutsideResponse implements Serializable {

	private static final long serialVersionUID = 338963154887093315L;

	/**
	 * 数据
	 */
	private String yxwResult;
	
	/**
	 * 消息
	 */
	private String resultMessage = "";
	
	/**
	 * 返回代码
	 */
	private Integer resultCode = ResultConstant.RESULT_CODE_SUCCESS;

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResult() {
		return yxwResult;
	}

	public void setResult(String yxwResult) {
		this.yxwResult = yxwResult;
	}

}
