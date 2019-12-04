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

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 支付方式实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "payMode")
public class PayMode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6782862390583431595L;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 支付方式编码
	 */
	private String code;

	/**
	 * 所属平台
	 */
	private String platformId;
	
	/**
	 * 显示名称
	 */
	private String showName;
	
	/**
	 * 对应真实的tradeMode值
	 */
	private Integer targetId;
	
	/**
	 * 排序顺序 -- 针对同一个平台的排序
	 */
	private Integer sortIndex;

	public PayMode() {
		super();
	}

	public PayMode(String name, String version, String code, String platformId, String showName, Integer targetId, Integer sortIndex) {
		super();
		this.name = name;
		this.version = version;
		this.code = code;
		this.platformId = platformId;
		this.showName = showName;
		this.targetId = targetId;
		this.sortIndex = sortIndex;
	}

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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
