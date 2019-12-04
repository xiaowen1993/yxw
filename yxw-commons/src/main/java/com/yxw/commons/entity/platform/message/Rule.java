package com.yxw.commons.entity.platform.message;

import java.util.ArrayList;
import java.util.List;

import com.yxw.base.entity.BaseEntity;

/**
 * 规则关键字实体类
 * */
public class Rule extends BaseEntity {

	private static final long serialVersionUID = 7706707147525647820L;

	/**
	 * 规则名
	 * */
	private String ruleName;
	/**
	 * 医院ID
	 * */
	private String hospitalId;

	/**
	 * 状态
	 * */
	private int state;
	/**
	 * 
	 * 类型 1随机回复 2 回复全部
	 * */
	private int type;
	/**
	 * 
	 * 第三方类型（1微信 2支付宝）
	 * */
	private int thirdType;

	private List<Keyword> keywords = new ArrayList<Keyword>();

	private List<Reply> replyList = new ArrayList<Reply>();

	private int textNum;
	private int imgNum;
	private int imageTextNum;
	private int totalNum;

	public Rule() {

	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public int getTextNum() {
		return textNum;
	}

	public void setTextNum(int textNum) {
		this.textNum = textNum;
	}

	public int getImgNum() {
		return imgNum;
	}

	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}

	public int getImageTextNum() {
		return imageTextNum;
	}

	public void setImageTextNum(int imageTextNum) {
		this.imageTextNum = imageTextNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}

	public int getThirdType() {
		return thirdType;
	}

	public void setThirdType(int thirdType) {
		this.thirdType = thirdType;
	}

}
