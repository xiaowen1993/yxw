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
package com.yxw.interfaces.vo.inspection;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 检验/检查/体检报告查询-->检验报告详情
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: InspectDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InspectDetail extends Reserve implements Serializable {

	private static final long serialVersionUID = -7649224689167101774L;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 结果
	 */
	private String result;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 参考范围
	 */
	private String refRange;
	/**
	 * 结果异常提示,0：正常,1：偏高,2：偏低
	 */
	private String abnormal;

	public InspectDetail() {
		super();
	}

	/**
	 * @param itemName
	 * @param result
	 * @param unit
	 * @param refRange
	 * @param abnormal
	 */
	public InspectDetail(String itemName, String result, String unit, String refRange, String abnormal) {
		super();
		this.itemName = itemName;
		this.result = result;
		this.unit = unit;
		this.refRange = refRange;
		this.abnormal = abnormal;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the refRange
	 */
	public String getRefRange() {
		return refRange;
	}

	/**
	 * @param refRange the refRange to set
	 */
	public void setRefRange(String refRange) {
		this.refRange = refRange;
	}

	/**
	 * @return the abnormal
	 */
	public String getAbnormal() {
		return abnormal;
	}

	/**
	 * @param abnormal the abnormal to set
	 */
	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

}
