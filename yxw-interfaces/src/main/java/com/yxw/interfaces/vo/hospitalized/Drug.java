package com.yxw.interfaces.vo.hospitalized;

import java.util.List;

import com.yxw.interfaces.vo.Reserve;

public class Drug extends Reserve {

	private static final long serialVersionUID = 1L;

	/**
	 * 药品类型
	 */
	private String drugType;
	/**
	 * 药品明细集合
	 */
	private List<Detail> details;
	
	public Drug() {
		super();
	}

	public Drug(String drugType, List<Detail> details) {
		super();
		this.drugType = drugType;
		this.details = details;
	}

	public String getDrugType() {
		return drugType;
	}

	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	
}
