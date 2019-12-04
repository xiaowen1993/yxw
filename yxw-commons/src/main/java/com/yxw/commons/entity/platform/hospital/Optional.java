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
 * 可选功能实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "option")
public class Optional extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814834324476485413L;

	/**
	 * 功能名称
	 */
	private String name;

	/**
	 * 对应程序路径
	 */
	private String controllerPath;

	/**
	 * 功能编码
	 */
	private String bizCode;

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
	 * @return the controllerPath
	 */
	public String getControllerPath() {
		return controllerPath;
	}

	/**
	 * @param controllerPath
	 *            the controllerPath to set
	 */
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
}
