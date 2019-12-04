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
 * 模板库
 * 
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgTemplateLibrary
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
public class MsgTemplateLibrary extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4562477202922409673L;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 模板编码
	 */
	private String templateCode;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 模板来源,1:微信公众号,2:支付宝服务窗,3:深圳健康易
	 */
	private Integer source;
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
	 * 一级行业
	 */
	private String primaryIndustry;
	/**
	 * 二级行业
	 */
	private String secondIndustry;
	/**
	 * 顶部颜色
	 */
	private String topColor;
	/**
	 * 跳链
	 */
	private String url;
	/**
	 * 消息内容
	 */
	private List<MsgLibraryContent> msgLibraryContents = new ArrayList<MsgLibraryContent>();
	/**
	 * 消息模板库功能点
	 */
	private List<MsgTemplateLibraryFunction> msgTemplateLibraryFunctions = new ArrayList<MsgTemplateLibraryFunction>();

	public MsgTemplateLibrary() {
		super();
	}

	/**
	 * @param templateId
	 * @param templateCode
	 * @param title
	 * @param source
	 * @param iconName
	 * @param iconPath
	 * @param animationName
	 * @param animationPath
	 * @param primaryIndustry
	 * @param secondIndustry
	 * @param topColor
	 * @param url
	 * @param msgLibraryContents
	 * @param msgTemplateLibraryFunctions
	 */
	public MsgTemplateLibrary(String templateId, String templateCode, String title, Integer source, String iconName, String iconPath,
			String animationName, String animationPath, String primaryIndustry, String secondIndustry, String topColor, String url,
			List<MsgLibraryContent> msgLibraryContents, List<MsgTemplateLibraryFunction> msgTemplateLibraryFunctions) {
		super();
		this.templateId = templateId;
		this.templateCode = templateCode;
		this.title = title;
		this.source = source;
		this.iconName = iconName;
		this.iconPath = iconPath;
		this.animationName = animationName;
		this.animationPath = animationPath;
		this.primaryIndustry = primaryIndustry;
		this.secondIndustry = secondIndustry;
		this.topColor = topColor;
		this.url = url;
		this.msgLibraryContents = msgLibraryContents;
		this.msgTemplateLibraryFunctions = msgTemplateLibraryFunctions;
	}

	public String getTopColor() {
		return topColor;
	}

	public void setTopColor(String topColor) {
		this.topColor = topColor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	 * @return the templateCode
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @param templateCode
	 *            the templateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
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
	 * @return the primaryIndustry
	 */
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	/**
	 * @param primaryIndustry
	 *            the primaryIndustry to set
	 */
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	/**
	 * @return the secondIndustry
	 */
	public String getSecondIndustry() {
		return secondIndustry;
	}

	/**
	 * @param secondIndustry
	 *            the secondIndustry to set
	 */
	public void setSecondIndustry(String secondIndustry) {
		this.secondIndustry = secondIndustry;
	}

	public List<MsgLibraryContent> getMsgLibraryContents() {
		return msgLibraryContents;
	}

	public void setMsgLibraryContents(List<MsgLibraryContent> msgLibraryContents) {
		this.msgLibraryContents = msgLibraryContents;
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

	/**
	 * @return the msgTemplateLibraryFunctions
	 */
	public List<MsgTemplateLibraryFunction> getMsgTemplateLibraryFunctions() {
		return msgTemplateLibraryFunctions;
	}

	/**
	 * @param msgTemplateLibraryFunctions the msgTemplateLibraryFunctions to set
	 */
	public void setMsgTemplateLibraryFunctions(List<MsgTemplateLibraryFunction> msgTemplateLibraryFunctions) {
		this.msgTemplateLibraryFunctions = msgTemplateLibraryFunctions;
	}

}
