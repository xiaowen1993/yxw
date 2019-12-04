package com.yxw.outside.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yxw.vo.StatsHospitalInfosVo;

public class AreaVo implements Serializable {

	private static final long serialVersionUID = -656223250660035250L;

	private Integer size;
	
	private String areaName;
	
	/**
	 * 已签约医院
	 */
	private List<StatsHospitalInfosVo> signItems = new ArrayList<>();
	
	/**
	 * 已上线医院数
	 */
	private List<StatsHospitalInfosVo> onlineItems = new ArrayList<>();

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<StatsHospitalInfosVo> getSignItems() {
		return signItems;
	}

	public void setSignItems(List<StatsHospitalInfosVo> signItems) {
		this.signItems = signItems;
	}

	public List<StatsHospitalInfosVo> getOnlineItems() {
		return onlineItems;
	}

	public void setOnlineItems(List<StatsHospitalInfosVo> onlineItems) {
		this.onlineItems = onlineItems;
	}
	
	public int hashCode() {
		return this.getAreaName().hashCode();
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof AreaVo) {
			result = this.getAreaName().equals(((AreaVo)obj).getAreaName());
		}
		
        return result;
    }
	
}
