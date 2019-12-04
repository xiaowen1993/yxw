/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月30日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.base.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * entity基类
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年4月29日
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2368417877456900821L;
	/**
	 * 主键ID
	 */
	protected String id;
	/**
	 * 创建人id
	 */
	protected String cp;
	/**
	 * 创建人名称
	 */
	protected String cpName;

	/**
	 * 创建时间
	 */
	protected Date ct;
	/**
	 * 修改人id
	 */
	protected String ep;

	/**
	 * 修改人名称
	 */
	protected String epName;
	/**
	 * 修改时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date et;

	public BaseEntity() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the ct
	 */
	public Date getCt() {
		return ct;
	}

	/**
	 * @param ct
	 *            the ct to set
	 */
	public void setCt(Date ct) {
		this.ct = ct;
	}

	/**
	 * @return the ep
	 */
	public String getEp() {
		return ep;
	}

	/**
	 * @param ep
	 *            the ep to set
	 */
	public void setEp(String ep) {
		this.ep = ep;
	}

	/**
	 * @return the et
	 */
	public Date getEt() {
		return et;
	}

	/**
	 * @param et
	 *            the et to set
	 */
	public void setEt(Date et) {
		this.et = et;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getEpName() {
		return epName;
	}

	public void setEpName(String epName) {
		this.epName = epName;
	}
}
