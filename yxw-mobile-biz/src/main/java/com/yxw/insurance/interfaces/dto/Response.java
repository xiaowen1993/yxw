/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.insurance.interfaces.dto;

import java.io.Serializable;

public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2526607896162883219L;

	/**
	 * 响应结果代码
	 */
	private String resultCode;

	/**
	 * 响应信息 ,当交易结果代码不成功时，该字段返回错误信息，否则返回空字符串
	 */
	private String resultMessage;

	/**
	 * 返回数据
	 */
	private String result;

	public Response() {
		super();
	}

	public Response(String resultCode, String resultMessage) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public Response(String resultCode, String resultMessage, String result) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.result = result;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
