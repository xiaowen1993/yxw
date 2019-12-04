package com.yxw.commons.entity.mobile.biz.nih;

import java.util.Date;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.utils.DateUtils;

public class NihRecord extends BaseEntity {

	private static final long serialVersionUID = -4479377855237429875L;

	private String id;

	/**
	 * 也就是OpenId
	 */
	private String userId;

	private String appCode;

	/**
	 * 填写的姓名
	 */
	private String name;

	/**
	 * 电话号码
	 */
	private String mobileNo;

	/**
	 * 病情描述
	 */
	private String desc;

	/**
	 * 是否有效：0，无效，1有效
	 */
	private Integer isValid;

	private Long updateTime;

	private Long createTime;

	private String createTimeStr;

	public String getHashTableName() {
		return SimpleHashTableNameGenerator.getNihHashTable(this.getUserId(), true);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getCreateTimeStr() {
		createTimeStr = DateUtils.dateToString(new Date(this.createTime));
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
