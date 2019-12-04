package com.yxw.stats.entity.platform;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.mobileapp.common.entity
 * @ClassName: MsgPushEntity
 * @Statement: <p>消息推送类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class MsgPushEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2396831268481366046L;

	public MsgPushEntity() {
		super();
	}

	public MsgPushEntity(String sendDate) {
		super();
		this.sendDate = sendDate;
	}

	/**
	 * 当前时间（发送该消息的时间）
	 * */
	private String sendDate;

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
