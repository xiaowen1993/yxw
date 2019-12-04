/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月13日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo;

import java.io.Serializable;

/**
 * 医享网络标准接口响应报文封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月13日
 */

public class Response implements Serializable {

	private static final long serialVersionUID = -2562668468838656765L;
	/**
	 * 交易结果代码,见ResultCodeType
	 * @see com.yxw.interfaces.constants.ResultCodeType
	 */
	private String resultCode;
	/**
	 * 错误信息,当交易结果代码不成功时，该字段返回错误信息，否则返回空字符串
	 */
	private String resultMessage;
	/**
	 * 输出参数
	 */
	private Object result;

	public Response() {
		super();
	}

	/**
	 * @param resultCode
	 * @param resultMessage
	 * @param result
	 */

	public Response(String resultCode, String resultMessage, Object result) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.result = result;
	}

	/**
	 * @return the resultCode
	 */

	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultMessage
	 */

	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * @param resultMessage
	 *            the resultMessage to set
	 */

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * @return the result
	 */

	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */

	public void setResult(Object result) {
		this.result = result;
	}

}
