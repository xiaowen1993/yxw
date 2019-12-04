package com.yxw.commons.entity.platform.area;

import java.util.List;

import com.yxw.base.entity.BaseEntity;

/**
 * 省市区域实体
 * Id和ParentId都是用 国家编制的code
 * 
 * @author YangXuewen
 *
 */
public class Area extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5574025166369163563L;

	/**
	 * 上级ID
	 */
	private String parentId;

	/**
	 * ID路径：格式 /[上级ID]{0,}/[自己的ID] 
	 * 如：
	 * /440000  /440000/440100  /440000/440100/440111
	 * /广东     /广东/广州       /广东/广州/白云区
	 */
	private String idPath;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 名称路径，原理和ID一样
	 */
	private String namePath;

	/**
	 * 短名称
	 */
	private String shortName;

	/**
	 * 短名称路径，原理和ID一样
	 */
	private String shortNamePath;

	/**
	 * 级别：1/2/3
	 */
	private Integer level;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 区号
	 */
	private String cityCode;

	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 拼音
	 */
	private String pinyin;

	/**
	 * 下级城市
	 */
	private List<Area> childAreas;

	public Area() {
		super();
	}

	public Area(String parentId, String idPath, String name, String namePath, String shortName, String shortNamePath, Integer level, String longitude, String latitude, String cityCode, String zipCode,
	        String pinyin) {
		super();
		this.parentId = parentId;
		this.idPath = idPath;
		this.name = name;
		this.namePath = namePath;
		this.shortName = shortName;
		this.shortNamePath = shortNamePath;
		this.level = level;
		this.longitude = longitude;
		this.latitude = latitude;
		this.cityCode = cityCode;
		this.zipCode = zipCode;
		this.pinyin = pinyin;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortNamePath() {
		return shortNamePath;
	}

	public void setShortNamePath(String shortNamePath) {
		this.shortNamePath = shortNamePath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public List<Area> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<Area> childAreas) {
		this.childAreas = childAreas;
	}

}
