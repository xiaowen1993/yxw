/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.datastorage.reports.entity;

import com.yxw.easyhealth.biz.datastorage.base.entity.DataBaseEntity;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.entity
 * @ClassName: DataExamine
 * @Statement: <p>检验详细入库</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DataInspectDetail extends DataBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600822304516708412L;

	/**
	 * 检验Id 关联DataInspect表ID
	 */
	private String inspectId;

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

	public DataInspectDetail() {
		super();
	}

	public DataInspectDetail(String inspectId, String itemName, String result, String unit, String refRange, String abnormal) {
		super();
		this.inspectId = inspectId;
		this.itemName = itemName;
		this.result = result;
		this.unit = unit;
		this.refRange = refRange;
		this.abnormal = abnormal;
	}

	public String getInspectId() {
		return inspectId;
	}

	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRefRange() {
		return refRange;
	}

	public void setRefRange(String refRange) {
		this.refRange = refRange;
	}

	public String getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

}
