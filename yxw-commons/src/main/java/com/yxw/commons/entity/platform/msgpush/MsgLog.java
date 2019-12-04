/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.msgpush;

import java.io.Serializable;
import java.util.Date;

import com.yxw.base.entity.BaseEntity;

/**
 * 消息推送日志
 * 
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgLog
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: 申午武
 * @Create Date: 2015年5月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgLog extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 852957404437294440L;
	/**
	 * 模板消息或者客服消息编码
	 */
	private String msgCode;
	/**
	 * 模板消息或者客服消息编码
	 */
	private String errorCode;
	/**
	 * 模板消息或者客服消息编码
	 */
	private String errorMsg;
	/**
	 * 微信公众平台或者支付宝服务窗APPID
	 */
	private String appId;
	/**
	 * 医院ID
	 */
	private String hospitalId;
	/**
	 * 用户ID
	 */
	private String openId;
	/**
	 * 消息来源,1:微信公众号,2:支付宝服务窗
	 */
	private Integer source;
	/**
	 * 推送状态,1:成功,2:失败
	 */
	private Integer pushStatus;
	/**
	 * 推送失败次数
	 */
	private Integer pushFailedNum;
	/**
	 * 推送的JSON内容
	 */
	private String pushContent;
	/**
	 * 推送时间
	 */
	private Date pushDate;

	public MsgLog() {
		super();
	}

	public MsgLog(String msgCode, String errorCode, String errorMsg, String appId, String hospitalId, String openId, Integer source,
			Integer pushStatus, Integer pushFailedNum, String pushContent, Date pushDate) {
		super();
		this.msgCode = msgCode;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.appId = appId;
		this.hospitalId = hospitalId;
		this.openId = openId;
		this.source = source;
		this.pushStatus = pushStatus;
		this.pushFailedNum = pushFailedNum;
		this.pushContent = pushContent;
		this.pushDate = pushDate;
	}

	/**
	 * @return the msgCode
	 */
	public String getMsgCode() {
		return msgCode;
	}

	/**
	 * @param msgCode
	 *            the msgCode to set
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId
	 *            the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the source
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Integer source) {
		this.source = source;
	}

	/**
	 * @return the pushStatus
	 */
	public Integer getPushStatus() {
		return pushStatus;
	}

	/**
	 * @param pushStatus
	 *            the pushStatus to set
	 */
	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	/**
	 * @return the pushFailedNum
	 */
	public Integer getPushFailedNum() {
		return pushFailedNum;
	}

	/**
	 * @param pushFailedNum
	 *            the pushFailedNum to set
	 */
	public void setPushFailedNum(Integer pushFailedNum) {
		this.pushFailedNum = pushFailedNum;
	}

	/**
	 * @return the pushContent
	 */
	public String getPushContent() {
		return pushContent;
	}

	/**
	 * @param pushContent
	 *            the pushContent to set
	 */
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	/**
	 * @return the date
	 */
	public Date getPushDate() {
		return pushDate;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
