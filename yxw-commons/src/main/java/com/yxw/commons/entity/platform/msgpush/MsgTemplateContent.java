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

import com.yxw.base.entity.BaseEntity;

/**
 * 模板消息内容
 * 
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgTemplateContent
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
public class MsgTemplateContent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 58551325533919019L;
	/**
	 * 序号
	 */
	private Integer sort;
	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 内容
	 */
	private String value;
	/**
	 * 节点
	 */
	private String node;
	/**
	 * 字体颜色
	 */
	private String fontColor;
	/**
	 * 模板ID
	 */
	private String templateId;

	public MsgTemplateContent() {
		super();
	}

	/**
	 * @param sort
	 * @param keyword
	 * @param value
	 * @param node
	 * @param fontColor
	 * @param templateId
	 */
	public MsgTemplateContent(Integer sort, String keyword, String value, String node, String fontColor, String templateId) {
		super();
		this.sort = sort;
		this.keyword = keyword;
		this.value = value;
		this.node = node;
		this.fontColor = fontColor;
		this.templateId = templateId;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
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

}
