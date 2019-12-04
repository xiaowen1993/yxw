/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月14日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.commons.entity.platform.hospital;

import java.util.List;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.entity.platform.message.MixedMeterial;

/**
 * 自定义菜单实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "menu")
public class Menu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576563609661687834L;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单内容 做链接地址
	 */
	private String content;

	/**
	 * 排序
	 */
	private int sort;

	/**
	 * 上级菜单Id
	 */
	private String parentId;

	/**
	 * 功能Id
	 */
	private String optionalId;

	/**
	 * 图文Id
	 */
	private String grapicsId;

	/**
	 * 菜单类型 0：功能  1：图文 2：链接地址
	 */
	private Integer meunType;

	/**
	 * 子菜单
	 */
	private List<Menu> childMenus;

	/**
	 * 平台配置ID
	 */
	private String psId;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 接入平台ID
	 */
	private String platformId;

	/**
	 * 图文集合
	 */
	private MixedMeterial mixedMeterial;

	/**
	 * 对应程序路径
	 */
	private String controllerPath;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the childMenus
	 */
	public List<Menu> getChildMenus() {
		return childMenus;
	}

	/**
	 * @param childMenus
	 *            the childMenus to set
	 */
	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOptionalId() {
		return optionalId;
	}

	public void setOptionalId(String optionalId) {
		this.optionalId = optionalId;
	}

	public String getGrapicsId() {
		return grapicsId;
	}

	public void setGrapicsId(String grapicsId) {
		this.grapicsId = grapicsId;
	}

	public Integer getMeunType() {
		return meunType;
	}

	public void setMeunType(Integer meunType) {
		this.meunType = meunType;
	}

	public String getPsId() {
		return psId;
	}

	public void setPsId(String psId) {
		this.psId = psId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public MixedMeterial getMixedMeterial() {
		return mixedMeterial;
	}

	public void setMixedMeterial(MixedMeterial mixedMeterial) {
		this.mixedMeterial = mixedMeterial;
	}

	public String getControllerPath() {
		return controllerPath;
	}

	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}

}
