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
package com.yxw.easyhealth.biz.outside.entity.out;

/**
 * @Package: com.yxw.mobileapp.biz.outside.entity.out
 * @ClassName: Request
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RespBody {
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
	private Object result;

	public RespBody() {
		super();
	}

	public RespBody(String resultCode, String resultMessage, Object result) {
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
