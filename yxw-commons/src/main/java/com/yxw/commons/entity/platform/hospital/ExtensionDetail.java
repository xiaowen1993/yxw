package com.yxw.commons.entity.platform.hospital;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 
 * @Package: com.yxw.platform.hospital.entity
 * @ClassName: Extension
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Entity(name = "extensionDetail")
public class ExtensionDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6344455879679973484L;

	/**
	 * 推广二维码ID
	 */
	private String extensionId;

	/**
	 * 关注时间
	 */
	private Long createTime;

	/**
	 * openId
	 */
	private String openId;

	public ExtensionDetail() {
		super();
	}

	public ExtensionDetail(String extensionId, Long createTime, String openId) {
		super();
		this.extensionId = extensionId;
		this.createTime = createTime;
		this.openId = openId;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
