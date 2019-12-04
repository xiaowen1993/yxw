package com.yxw.statistics.biz.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class CityVo implements Serializable {

	private static final long serialVersionUID = -187374251371159913L;

	private String id;

	private String name;

	@JSONField(serialize = false)
	private String parentId;

	private Map<String, CountryVo> countryMap = new HashMap<>();

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

	public Map<String, CountryVo> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<String, CountryVo> countryMap) {
		this.countryMap = countryMap;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
