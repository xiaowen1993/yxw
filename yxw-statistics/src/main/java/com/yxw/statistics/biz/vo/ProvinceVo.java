package com.yxw.statistics.biz.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProvinceVo implements Serializable {

	private static final long serialVersionUID = -187374251371159913L;

	private String id;
	
	private String name;
	
	private Map<String, CityVo> cityMap = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, CityVo> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<String, CityVo> cityMap) {
		this.cityMap = cityMap;
	}

}
 