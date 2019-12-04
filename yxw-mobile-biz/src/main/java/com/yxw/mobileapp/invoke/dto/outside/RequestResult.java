/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-5</p>
 *  <p> Created by Zhoujb</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.outside;

import java.io.Serializable;

/**
 * 
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: RequestResult
 * @Statement: <p>外部请求返回的结果对象</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月5日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RequestResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6629368905035647442L;

	/**
	 * 响应结果代码
	 */
	private String errcode;

	/**
	 * 响应信息 ,当交易结果代码不成功时，该字段返回错误信息，否则返回空字符串
	 */
	private String errmsg;

	/**
	 * 返回数据
	 */
	private String result;

	public RequestResult() {
		super();
	}

	public RequestResult(String errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public RequestResult(String errcode, String errmsg, String result) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.result = result;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
