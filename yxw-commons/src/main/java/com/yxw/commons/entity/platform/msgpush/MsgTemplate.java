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
import java.util.ArrayList;
import java.util.List;

import com.yxw.base.entity.BaseEntity;

/**
 * 模板消息
 * 
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgTemplate
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
public class MsgTemplate extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2136080098135308647L;
	/**
	 * 微信公众号或者支付宝服务窗指定的模板ID
	 */
	private String templateId;
	/**
	 * 模板库编号
	 */
	private String libraryCode;
	/**
	 * 模板编码
	 */
	private String code;
	/**
	 * 模板名称
	 */
	private String title;
	/**
	 * 模板顶部颜色
	 */
	private String topColor;
	/**
	 * 模板来源,1:微信公众号,2:支付宝服务窗...
	 * @see Platform表，targetId字段
	 * @see BizConstant中对于平台的val值
	 */
	private Integer source;
	/**
	 * 模板消息跳转链接
	 */
	private String url;
	/**
	 * 微信公众号或者支付宝服务窗APPID
	 */
	private String appId;

	/**
	 * 图标名称
	 */
	private String iconName;
	/**
	 * 图标路径
	 */
	private String iconPath;
	/**
	 * 动画名称
	 */
	private String animationName;
	/**
	 * 动画路径
	 */
	private String animationPath;

	/**
	 * 医院ID
	 */
	private String hospitalId;
	
	/**
	 * 2017-7-25 10:41:28 -- add
	 * 消息代码
	 */
	private String msgCode;
	
	/**
	 * 2017-7-25 10:41:47 -- add
	 * 消息编码（targetId）
	 */
	private Integer msgTarget;

	/**
	 * 消息内容
	 */
	private List<MsgTemplateContent> msgTemplateContents = new ArrayList<MsgTemplateContent>();

	/**
	 * 消息模板库功能点
	 */
	private List<MsgTemplateFunction> msgTemplateFunctions = new ArrayList<MsgTemplateFunction>();

	public MsgTemplate() {
		super();
	}

	/**
	 * @param templateId
	 * @param libraryCode
	 * @param code
	 * @param title
	 * @param topColor
	 * @param source
	 * @param url
	 * @param appId
	 * @param iconName
	 * @param iconPath
	 * @param animationName
	 * @param animationPath
	 * @param hospitalId
	 * @param msgTemplateContents
	 * @param msgTemplateFunctions
	 */
	public MsgTemplate(String templateId, String libraryCode, String code, String title, String topColor, Integer source, String url, String appId,
			String iconName, String iconPath, String animationName, String animationPath, String hospitalId, List<MsgTemplateContent> msgTemplateContents,
			List<MsgTemplateFunction> msgTemplateFunctions) {
		super();
		this.templateId = templateId;
		this.libraryCode = libraryCode;
		this.code = code;
		this.title = title;
		this.topColor = topColor;
		this.source = source;
		this.url = url;
		this.appId = appId;
		this.iconName = iconName;
		this.iconPath = iconPath;
		this.animationName = animationName;
		this.animationPath = animationPath;
		this.hospitalId = hospitalId;
		this.msgTemplateContents = msgTemplateContents;
		this.msgTemplateFunctions = msgTemplateFunctions;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the libraryCode
	 */
	public String getLibraryCode() {
		return libraryCode;
	}

	/**
	 * @param libraryCode the libraryCode to set
	 */
	public void setLibraryCode(String libraryCode) {
		this.libraryCode = libraryCode;
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
	 * @return the topColor
	 */
	public String getTopColor() {
		return topColor;
	}

	/**
	 * @param topColor
	 *            the topColor to set
	 */
	public void setTopColor(String topColor) {
		this.topColor = topColor;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the msgTemplateContents
	 */
	public List<MsgTemplateContent> getMsgTemplateContents() {
		return msgTemplateContents;
	}

	/**
	 * @param msgTemplateContents the msgTemplateContents to set
	 */
	public void setMsgTemplateContents(List<MsgTemplateContent> msgTemplateContents) {
		this.msgTemplateContents = msgTemplateContents;
	}

	/**
	 * @return the msgTemplateFunctions
	 */
	public List<MsgTemplateFunction> getMsgTemplateFunctions() {
		return msgTemplateFunctions;
	}

	/**
	 * @param msgTemplateFunctions the msgTemplateFunctions to set
	 */
	public void setMsgTemplateFunctions(List<MsgTemplateFunction> msgTemplateFunctions) {
		this.msgTemplateFunctions = msgTemplateFunctions;
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
	 * @return the animationName
	 */
	public String getAnimationName() {
		return animationName;
	}

	/**
	 * @param animationName the animationName to set
	 */
	public void setAnimationName(String animationName) {
		this.animationName = animationName;
	}

	/**
	 * @return the animationPath
	 */
	public String getAnimationPath() {
		return animationPath;
	}

	/**
	 * @param animationPath the animationPath to set
	 */
	public void setAnimationPath(String animationPath) {
		this.animationPath = animationPath;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Integer getMsgTarget() {
		return msgTarget;
	}

	public void setMsgTarget(Integer msgTarget) {
		this.msgTarget = msgTarget;
	}

}
