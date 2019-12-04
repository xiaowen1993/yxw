package com.yxw.interfaces.vo;

import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7302979259675442707L;

	private String serviceId;
	private String methodName;

	private Object innerRequest;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getInnerRequest() {
		return innerRequest;
	}

	public void setInnerRequest(Object innerRequest) {
		this.innerRequest = innerRequest;
	}

}
