/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2017 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2017-5-24</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

public class ExtensionCount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9003576624706578840L;

	private String hospitalId;

	private String extensionId;

	private Integer num;

	private String date;

	private Integer totalNum;

	/**
	 * @return the totalNum
	 */

	public Integer getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum
	 *            the totalNum to set
	 */

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the hospitalId
	 */

	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the extensionId
	 */

	public String getExtensionId() {
		return extensionId;
	}

	/**
	 * @param extensionId
	 *            the extensionId to set
	 */

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	/**
	 * @return the num
	 */

	public Integer getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */

	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * @return the date
	 */

	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */

	public void setDate(String date) {
		this.date = date;
	}

}
