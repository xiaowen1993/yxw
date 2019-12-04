package com.yxw.insurance.chinalife.interfaces.entity.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Resonse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5930222304960599074L;

	/**
	 * 代码	名称	                              说明
		10	Success	                          成功
		20	Not Authorized					  请求者未注册，消息不予处理，请联系系统管理员
		21	Accepted						      已接受未处理
		22	Data Format Error				  请求消息格式错误
		23	Data Error						  数据错误（为空或内容错误）
		24	OutsideSystem Error				  外接系统编号错误
		25	Dictionary Error			 	      消息内容数据字典错误
		30	Rejected						      拒绝（内部服务异常）
		31	CorrelationId Invalid			  数字签名无效或被窜改
		40	Service Not Reach				  服务不可达
		41	Core Service Not Reach			  核心服务不可达
		42	Cis Not Reach					  大病保险服务不可达
		43	NRCMS Not Reach					  医疗经办服务不可达
		50	Service No Response				  服务无反馈
		51	Core Service No Response		      核心服务无反馈
		52	Cis No Response					  大病保险服务无反馈
		53	NRCMS No Response				  医疗经办服务无反馈
	 */
	@JSONField(name = "MessageStatusCode")
	private String messageStatusCode = "";
	@JSONField(name = "Message")
	private String message = "";
	@JSONField(name = "BusinessProcessStatus")
	private BusinessProcessStatus businessProcessStatus = new BusinessProcessStatus();

	public String getMessageStatusCode() {
		return messageStatusCode;
	}

	public void setMessageStatusCode(String messageStatusCode) {
		this.messageStatusCode = messageStatusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BusinessProcessStatus getBusinessProcessStatus() {
		return businessProcessStatus;
	}

	public void setBusinessProcessStatus(BusinessProcessStatus businessProcessStatus) {
		this.businessProcessStatus = businessProcessStatus;
	}

}
