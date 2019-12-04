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
package com.yxw.mobileapp.invoke.dto;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: Request
 * @Statement: <p>对外接口入参</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6891202373238554744L;
	/**
	 * 函数code
	 */
	private String methodCode;

	/**
	 * 参数xml
	 */
	private String params;

	/**
	 * 返回参数格式 0：xml 1：json 默认为0
	 */
	private String responseType;

	public Request() {
		super();
	}

	public Request(String methodCode, String params, String responseType) {
		super();
		this.methodCode = methodCode;
		this.params = params;
		this.responseType = responseType;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
