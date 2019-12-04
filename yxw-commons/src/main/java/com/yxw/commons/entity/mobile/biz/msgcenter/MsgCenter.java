/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.mobile.biz.msgcenter;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;

/**
 * 健康易消息中心(通知中心)
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgCenter
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgCenter extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1682063957690885755L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 图标名称
	 */
	private String iconName;
	/**
	 * 图标路径
	 */
	private String iconPath;
	/**
	 * 医院ID
	 */
	private String hospitalId;
	/**
	 * 医院名称
	 */
	private String hospitalName;
	/**
	 * 消息标题
	 */
	private String msgTitle;
	/**
	 * 1:未读,2:已读
	 */
	private Integer isRead;
	/**
	 * 消息内容
	 */
	private String msgContent;

	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	public MsgCenter() {
		super();
	}

	/**
	 * @param userId
	 * @param tempLibraryId
	 * @param iconName
	 * @param iconPath
	 * @param hospitalId
	 * @param hospitalName
	 * @param msgTitle
	 * @param isRead
	 * @param msgContent
	 * @param hashTableName
	 */
	public MsgCenter(String userId, String templateId, String iconName, String iconPath, String hospitalId, String hospitalName,
			String msgTitle, Integer isRead, String msgContent) {
		super();
		this.userId = userId;
		this.templateId = templateId;
		this.iconName = iconName;
		this.iconPath = iconPath;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.msgTitle = msgTitle;
		this.isRead = isRead;
		this.msgContent = msgContent;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the isRead
	 */
	public Integer getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/**
	 * @return the msgTitle
	 */
	public String getMsgTitle() {
		return msgTitle;
	}

	/**
	 * @param msgTitle the msgTitle to set
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	/**
	 * @return the hashTableName
	 */
	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getSysMsgCenterHashTable(userId, true);
		}
		return hashTableName;
	}

	/**
	 * @param hashTableName the hashTableName to set
	 */
	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	/**
	 * @return the iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param iconName the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return the iconPath
	 */
	public String getIconPath() {
		return iconPath;
	}

	/**
	 * @param iconPath the iconPath to set
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}
