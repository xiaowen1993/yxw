package com.yxw.commons.entity.platform.app.optional;

import java.util.List;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * APP功能板块实体类
 * 
 * @author YangXuewen
 *
 */
@Entity(name = "appOptionalModule")
public class AppOptionalModule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5020340590399873651L;

	private String name;
	private String code;
	private List<AppOptional> appOptionals;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public List<AppOptional> getAppOptionals() {
		return appOptionals;
	}

	public void setAppOptionals(List<AppOptional> appOptionals) {
		this.appOptionals = appOptionals;
	}

}