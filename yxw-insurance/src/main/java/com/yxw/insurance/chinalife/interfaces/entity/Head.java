package com.yxw.insurance.chinalife.interfaces.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.insurance.chinalife.interfaces.constants.CommonConstant;

public class Head implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8349162320776913556L;

	//消息接受者的代码
	@JSONField(name = "ReceiverCode")
	private String receiverCode = CommonConstant.RECEIVER_CODE;
	//消息发送者的代码，外接系统编号
	@JSONField(name = "SenderCode")
	private String senderCode = CommonConstant.SENDER_CODE;
	//标准的版本号信息
	@JSONField(name = "StandardVersionCode")
	private String standardVersionCode = CommonConstant.STANDARD_VERSION_CODE;
	@JSONField(name = "BusinessType")
	private String businessType = CommonConstant.BUSINESS_TYPE;

	//交易流水号，请求、应答消息相同
	@JSONField(name = "TransRefGUID")
	private String transRefGUID = UUID.randomUUID().toString();
	//消息创建的时间
	@JSONField(name = "MessageDateTime")
	private String messageDateTime = CommonConstant.sdf.format(new Date());
	//一串数字用于标识消息以防这条消息被修改，由Token、TransRefGUID、MessageDateTime基于加密算法生成
	@JSONField(name = "CorrelationId")
	private String correlationId = "";
	//消息唯一标识符
	@JSONField(name = "MessageId")
	private String messageId = UUID.randomUUID().toString();

	//记录数
	@JSONField(name = "TotalRecord")
	private String totalRecord = "";
	//交易码
	@JSONField(name = "TransactionCode")
	private String transactionCode = "";
	//子交易码
	@JSONField(name = "TransactionSubCode")
	private String transactionSubCode = "";

	//消息发送者的名称
	@JSONField(name = "SenderName")
	private String senderName = "";
	//消息发送者的地址
	@JSONField(name = "SenderAddress")
	private String senderAddress = "";
	//消息接受者的名称
	@JSONField(name = "ReceiverName")
	private String receiverName = "";
	//消息接受者的地址
	@JSONField(name = "ReceiverAddress")
	private String receiverAddress = "";

	public Head() {
		super();
	}

	public Head(String transactionCode, String transactionSubCode) {
		super();
		this.transactionCode = transactionCode;
		this.transactionSubCode = transactionSubCode;
	}

	public String getReceiverCode() {
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getStandardVersionCode() {
		return standardVersionCode;
	}

	public void setStandardVersionCode(String standardVersionCode) {
		this.standardVersionCode = standardVersionCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTransRefGUID() {
		return transRefGUID;
	}

	public void setTransRefGUID(String transRefGUID) {
		this.transRefGUID = transRefGUID;
	}

	public String getMessageDateTime() {
		return messageDateTime;
	}

	public void setMessageDateTime(String messageDateTime) {
		this.messageDateTime = messageDateTime;
	}

	public String getCorrelationId() {
		if (StringUtils.isNotBlank(transRefGUID) && StringUtils.isNotBlank(messageDateTime)) {
			return DigestUtils.shaHex(CommonConstant.TOKEN + transRefGUID + messageDateTime);
		} else {
			return correlationId;
		}
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

}
