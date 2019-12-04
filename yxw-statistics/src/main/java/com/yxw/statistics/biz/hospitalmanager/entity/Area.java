package com.yxw.statistics.biz.hospitalmanager.entity;

import com.yxw.base.entity.BaseEntity;

public class Area extends BaseEntity {

	private static final long serialVersionUID = -4383019810878665141L;

	private String id;
	
	private String name;
	
	private String parentId;
	
	private Integer level;

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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
