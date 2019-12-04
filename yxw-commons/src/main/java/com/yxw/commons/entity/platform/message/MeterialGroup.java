package com.yxw.commons.entity.platform.message;

import com.yxw.base.entity.BaseEntity;

/**
 * 素材分组
 * */
public class MeterialGroup extends BaseEntity {

	private static final long serialVersionUID = 8748578827439847187L;
	/**
	 * 素材分组名称
	 * */
	private String name;
	/**
	 * 删除状态（1未删除 0已删除）
	 * */
	private int state;

	public MeterialGroup() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
