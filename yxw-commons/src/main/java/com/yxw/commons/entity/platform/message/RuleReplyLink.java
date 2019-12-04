package com.yxw.commons.entity.platform.message;

import com.yxw.base.entity.BaseEntity;

public class RuleReplyLink extends BaseEntity {

	private static final long serialVersionUID = 529054586276263487L;

	/**
	 * 规则ID
	 * */
	private String ruleId;
	/**
	 * 回复ID
	 * */
	private String replyId;
	/**
	 * 状态
	 * */
	private int state;

	public RuleReplyLink() {

	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
