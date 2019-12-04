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

import com.yxw.base.entity.BaseEntity;

/**
 * 客服消息
 * 
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgCustomer
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年5月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgCustomer extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6078262653529270334L;
	/**
	 * 消息编码
	 */
	private String code;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息来源,1:微信公众号,2:支付宝服务窗
	 */
	private Integer source;
	/**
	 * 消息类型,1:text,目前默认为1
	 */
	private Integer type;
	/**
	 * 微信公众号或者支付宝服务窗APPID
	 */
	private String appId;
	/**
	 * 医院ID
	 */
	private String hospitalId;

	public MsgCustomer() {
		super();
	}

	/**
	 * @param code
	 * @param title
	 * @param content
	 * @param source
	 * @param type
	 * @param appId
	 * @param hospitalId
	 */
	public MsgCustomer(String code, String title, String content, Integer source, Integer type, String appId, String hospitalId) {
		super();
		this.code = code;
		this.title = title;
		this.content = content;
		this.source = source;
		this.type = type;
		this.appId = appId;
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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

}
