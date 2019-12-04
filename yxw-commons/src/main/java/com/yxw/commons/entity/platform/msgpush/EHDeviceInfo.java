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
package com.yxw.commons.entity.platform.msgpush;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;

/**
 * 健康易设备信息
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: DeviceInfo
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class EHDeviceInfo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -916737220282600973L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 目前用来存储在百度云推送平台申请的账号ID,一个应用ID可以有多个应用类别
	 */
	private String appId;
	/**
	 * 1:健康易
	 */
	private String appType;
	/**
	 * 设备ID
	 */
	private String deviceId;
	/**
	 * 1:苹果,2:安卓
	 */
	private String deviceType;
	/**
	 * 渠道ID
	 */
	private String channelId;
	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	public EHDeviceInfo() {
		super();
	}

	/**
	 * @param userId
	 * @param appId
	 * @param appType
	 * @param deviceId
	 * @param deviceType
	 * @param channelId
	 */
	public EHDeviceInfo(String userId, String appId, String appType, String deviceId, String deviceType, String channelId) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.appType = appType;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.channelId = channelId;
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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the hashTableName
	 */
	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getEhDeviceInfoHashTable(userId, true);
		}
		return hashTableName;
	}

	/**
	 * @param hashTableName the hashTableName to set
	 */
	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

}
