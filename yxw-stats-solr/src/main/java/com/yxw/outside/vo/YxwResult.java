package com.yxw.outside.vo;

import java.io.Serializable;

public class YxwResult implements Serializable {

	private static final long serialVersionUID = 2721732238887215792L;

	private Integer size = 0;
	
	private Object items;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

}
