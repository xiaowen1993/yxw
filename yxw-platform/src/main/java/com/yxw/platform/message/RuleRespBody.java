package com.yxw.platform.message;

import java.util.List;

import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.controller.RespBody;

public class RuleRespBody extends RespBody {

	private static final long serialVersionUID = 4898190916325940161L;

	private String ruleId;

	private int ruleType;

	private String ruleName;

	private int replyNum;

	private int textNum;

	private int imageNum;

	private int imageTextNum;

	private Rule rule;

	private List<Keyword> keywordList;

	private List<Reply> replies;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getTextNum() {
		return textNum;
	}

	public void setTextNum(int textNum) {
		this.textNum = textNum;
	}

	public int getImageNum() {
		return imageNum;
	}

	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public int getImageTextNum() {
		return imageTextNum;
	}

	public void setImageTextNum(int imageTextNum) {
		this.imageTextNum = imageTextNum;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public List<Keyword> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<Keyword> keywordList) {
		this.keywordList = keywordList;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public int getRuleType() {
		return ruleType;
	}

	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

}
