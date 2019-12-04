package com.yxw.hospitalmanager.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class CountryVo implements Serializable {

	private static final long serialVersionUID = -187374251371159913L;

	private String id;

	private String name;

	@JSONField(serialize = false)
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
