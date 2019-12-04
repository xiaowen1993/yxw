package com.yxw.easyhealth.biz.vo;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.biz.vo
 * @ClassName: SelectItem
 * @Statement: <p>下拉选择选择项传值对像</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-11-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SelectItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2518369320672378397L;

	private String text;

	private String value;

	private String selected;

	public SelectItem(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}

	public SelectItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
