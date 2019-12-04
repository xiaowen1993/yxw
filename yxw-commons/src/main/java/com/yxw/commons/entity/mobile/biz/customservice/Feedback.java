/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.mobile.biz.customservice;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * 客服中心 用户建议
 * 
 * @Author: luob
 * @Create Date: 2015-10-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Feedback extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String openId;

	private String platformType;

	private String feedback;

	private int type;

	private String reply;

	public Feedback(String openId, String platformType, String feedback, int type, String reply) {
		super();
		this.openId = openId;
		this.platformType = platformType;
		this.feedback = feedback;
		this.type = type;
		this.reply = reply;
	}

	public Feedback() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}
