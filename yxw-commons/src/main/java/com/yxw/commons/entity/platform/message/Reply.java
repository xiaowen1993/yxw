package com.yxw.commons.entity.platform.message;

import java.util.ArrayList;
import java.util.List;

import com.yxw.base.entity.BaseEntity;

/**
 * 微信自动回复实体类
 * */
public class Reply extends BaseEntity {

	private static final long serialVersionUID = -7207334175635631279L;

	/**
	 * 回复类型 1被添加/关注后回复 2关键词自动回复
	 * */
	private int type;
	/**
	 * 第三方类型（1微信 2 支付宝）
	 * */
	private int thirdType;
	/**
	 * 回复内容类型（1文字 2图片 3图文）
	 * */
	private int contentType;
	/**
	 * 医院ID
	 * */
	private String hospitalId;
	/**
	 * 分院ID
	 * */
	private String hospitalBranchId;
	/**
	 * 文本内容
	 * */
	private String content;
	/**
	 * 多图片路径
	 * */
	private String picPaths;
	/**
	 * 删除状态（1未删除 0已删除）
	 * */
	private int state;

	/**
	 * 回复对应的素材
	 * */
	private List<MixedMeterial> mixedMeterialList = new ArrayList<MixedMeterial>();;

	public Reply() {

	}

	public Reply(int type, int thirdType, int contentType, String hospitalId, int state) {
		super();
		this.type = type;
		this.thirdType = thirdType;
		this.contentType = contentType;
		this.hospitalId = hospitalId;
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalBranchId() {
		return hospitalBranchId;
	}

	public void setHospitalBranchId(String hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(String picPaths) {
		this.picPaths = picPaths;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getThirdType() {
		return thirdType;
	}

	public void setThirdType(int thirdType) {
		this.thirdType = thirdType;
	}

	public List<MixedMeterial> getMixedMeterialList() {
		return mixedMeterialList;
	}

	public void setMixedMeterialList(List<MixedMeterial> mixedMeterialList) {
		this.mixedMeterialList = mixedMeterialList;
	}

}
