package com.yxw.solr.vo.order;

import java.io.Serializable;

public class OrderInfoResponse implements Serializable {

	private static final long serialVersionUID = -3644866877583467563L;

	private int size;
	
	private int platform;
	
	private int bizType;
	
	private Serializable beans;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public Serializable getBeans() {
		return beans;
	}

	public void setBeans(Serializable beans) {
		this.beans = beans;
	}
}
