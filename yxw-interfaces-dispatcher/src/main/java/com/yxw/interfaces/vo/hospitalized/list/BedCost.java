package com.yxw.interfaces.vo.hospitalized.list;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院费用清单-->清单基本信息-->费用
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */
public class BedCost extends Reserve {

	private static final long serialVersionUID = 1954237646186175657L;

	/**
	 * 费用类别
	 */
	private String costType;
	/**
	 * 费用名称
	 */
	private String costName;
	/**
	 * 费用金额
	 */
	private String costAmout;
	
	public BedCost() {
		super();
	}

	public BedCost(String costType, String costName, String costAmout) {
		super();
		this.costType = costType;
		this.costName = costName;
		this.costAmout = costAmout;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public String getCostAmout() {
		return costAmout;
	}

	public void setCostAmout(String costAmout) {
		this.costAmout = costAmout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}