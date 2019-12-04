package com.yxw.mobileapp.common.websocket;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.utils.DateUtils;

public class YxwMessage implements Serializable {

	private static final long serialVersionUID = -5573800126142409432L;

	public enum MessageType {
		// 测试
		TEST,
		// 消息推送
		MSGPUSH
	}
	
	private MessageType messageType;
	
	// 发送人 若为系统则为SYSTEM, 若为外部医院消息，则为hospitalCode, 若为用户, 则为openId
	private String fromUser;
	
	// 接收人 openId(sessionId?)
	private String toUser;
	
	private String content;
	
	@JSONField(serialize=false)
	private Long pushTime;
	
	private String pushTimeLabel;

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPushTime() {
		return pushTime;
	}

	public void setPushTime(Long pushTime) {
		this.pushTime = pushTime;
	}

	public String getPushTimeLabel() {
		pushTimeLabel = DateUtils.dateToString(new Date(pushTime));
		return pushTimeLabel;
	}

	public void setPushTimeLabel(String pushTimeLabel) {
		this.pushTimeLabel = pushTimeLabel;
	}
}
