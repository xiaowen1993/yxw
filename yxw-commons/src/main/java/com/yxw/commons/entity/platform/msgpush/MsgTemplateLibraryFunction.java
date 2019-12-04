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
 * 消息模板库功能点
 * @Package: com.yxw.platform.msgpush.entity
 * @ClassName: MsgTemplateLibraryFunction
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MsgTemplateLibraryFunction extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5728991040426544498L;
	/**
	 * 模板库的模板ID
	 */
	private String tempLibraryId;
	/**
	 * 功能名称
	 */
	private String functionName;
	/**
	 * 1:超链接,2:JS函数名称
	 */
	private String functionType;
	/**
	 * JS函数名称或者URL地址
	 */
	private String functionCode;
	/**
	 * 功能点排序号
	 */
	private String sort;

	/**
	 * 
	 */
	public MsgTemplateLibraryFunction() {
		super();
	}

	/**
	 * @param tempLibraryId
	 * @param functionName
	 * @param functionType
	 * @param functionCode
	 * @param sort
	 */
	public MsgTemplateLibraryFunction(String tempLibraryId, String functionName, String functionType, String functionCode, String sort) {
		super();
		this.tempLibraryId = tempLibraryId;
		this.functionName = functionName;
		this.functionType = functionType;
		this.functionCode = functionCode;
		this.sort = sort;
	}

	/**
	 * @return the tempLibraryId
	 */
	public String getTempLibraryId() {
		return tempLibraryId;
	}

	/**
	 * @param tempLibraryId the tempLibraryId to set
	 */
	public void setTempLibraryId(String tempLibraryId) {
		this.tempLibraryId = tempLibraryId;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the functionType
	 */
	public String getFunctionType() {
		return functionType;
	}

	/**
	 * @param functionType the functionType to set
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

}
