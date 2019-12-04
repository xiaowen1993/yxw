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

public class Request implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6891202373238554744L;
	/**
	 * 函数name
	 */
	private String methodName;

	/**
	 * 参数实体类
	 */
	private Object params;

	/**
	 * 返回参数格式 0：xml 1：json 默认为0
	 */
	private String responseType;

	public Request() {
		super();
	}

	public Request(String methodName, Object params, String responseType) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.responseType = responseType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
