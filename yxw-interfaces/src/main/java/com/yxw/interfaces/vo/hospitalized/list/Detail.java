package com.yxw.interfaces.vo.hospitalized.list;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院费用清单-->清单明细列表-->费用明细
 * @Package: com.yxw.interfaces.entity.hospitalized.list
 * @ClassName: Detail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Detail extends Reserve {

	private static final long serialVersionUID = 1L;

	/**
	 * 项目
	 */
	private String projectName;
	/**
	 * 规格
	 */
	private String spec;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 数量
	 */
	private String quantity;
	/**
	 * 金额
	 */
	private String amout;
	
	public Detail() {
		super();
	}

	public Detail(String projectName, String spec, String unit,
			String quantity, String amout) {
		super();
		this.projectName = projectName;
		this.spec = spec;
		this.unit = unit;
		this.quantity = quantity;
		this.amout = amout;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}
	
}
