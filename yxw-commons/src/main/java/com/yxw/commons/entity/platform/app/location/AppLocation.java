package com.yxw.commons.entity.platform.app.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * APP 定位实体类
 * 
 * @author YangXuewen
 * 
 */
@Entity(name = "appLocation")
public class AppLocation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -914912578022914716L;

	private String platformCode;
	private String cityId;
	private String cityCode;
	private String name;
	private Integer level;
	private String pinyin;

	/**
	 * 0无效，1有效
	 */
	private Integer status;

	private Integer showSort;

	private String provinceId;

	private String provinceName;

	/**
	 * 下级城市
	 */
	private List<AppLocation> childAppLocations = new ArrayList<AppLocation>();

	public AppLocation() {
		super();
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getShowSort() {
		return showSort;
	}

	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}

	public List<AppLocation> getChildAppLocations() {
		return childAppLocations;
	}

	public void setChildAppLocations(List<AppLocation> childAppLocations) {
		this.childAppLocations = childAppLocations;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}