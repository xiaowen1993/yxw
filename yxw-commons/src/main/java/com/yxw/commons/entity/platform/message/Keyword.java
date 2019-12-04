package com.yxw.commons.entity.platform.message;

import com.yxw.base.entity.BaseEntity;

/**
 * 微信自动回复---关键字表
 * */
public class Keyword extends BaseEntity {

	private static final long serialVersionUID = -872577947852797953L;

	/**
	 * 关键字内容
	 * */
	private String content;

	/**
	 * 类型
	 * */
	private int type;
	/**
	 * 状态
	 * */
	private int state;

	/**
	 * 规则ID
	 * */
	private String ruleId;

	public Keyword() {

	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

}
