package com.yxw.commons.entity.platform.app.optional;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * APP 功能实体类
 * 
 * @author YangXuewen
 *
 */
@Entity(name = "appOptional")
public class AppOptional extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1677334462124119949L;

	/**
	 * 功能所属板块
	 */
	private AppOptionalModule appOptionalModule;

	/**
	 * 功能编码
	 */
	private String code;

	/**
	 * 功能名称
	 */
	private String name;

	/**
	 * 功能图标
	 */
	private String icon;

	/**
	 * 功能图标样式
	 */
	private String iconCss;

	/**
	 * 功能显示顺序
	 */
	private Integer showSort;

	/**
	 * 功能是否发布 0不发布 1发布
	 */
	private Integer visible;

	/**
	 * 功能URL地址
	 */
	private String url;

	/**
	 * 功能描述
	 */
	private String description;

	public AppOptionalModule getAppOptionalModule() {
		return appOptionalModule;
	}

	public void setAppOptionalModule(AppOptionalModule appOptionalModule) {
		this.appOptionalModule = appOptionalModule;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getIconCss() {
		return iconCss;
	}

	public void setIconCss(String iconCss) {
		this.iconCss = iconCss == null ? null : iconCss.trim();
	}

	public Integer getShowSort() {
		return showSort;
	}

	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
}