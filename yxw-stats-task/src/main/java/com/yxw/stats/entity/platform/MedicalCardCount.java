/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.statistics.entity
 * @ClassName: MedicalCardCount
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年9月9日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MedicalCardCount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1383625226774097801L;

	/**
	 * 日期
	 */
	private String date;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 分院ID
	 */
	private String branchId;

	/**
	 * 统计数量
	 */
	private Integer count = 0;

	/**
	 * 绑卡渠道 1 微信 2 支付宝
	 */
	private Integer platform;

	/**
	 * 微信绑卡数
	 */
	private Integer wechatCount = 0;

	/**
	 * 支付宝绑卡数
	 */
	private Integer alipayCount = 0;

	/**
	 * @return the wechatCount
	 */

	public Integer getWechatCount() {
		return wechatCount;
	}

	/**
	 * @param wechatCount
	 *            the wechatCount to set
	 */

	public void setWechatCount(Integer wechatCount) {
		this.wechatCount = wechatCount;
	}

	/**
	 * @return the alipayCount
	 */

	public Integer getAlipayCount() {
		return alipayCount;
	}

	/**
	 * @param alipayCount
	 *            the alipayCount to set
	 */

	public void setAlipayCount(Integer alipayCount) {
		this.alipayCount = alipayCount;
	}

	/**
	 * @return the platform
	 */

	public Integer getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public MedicalCardCount() {
		super();
	}

	public MedicalCardCount(String date, String hospitalId, String branchId, Integer count, Integer platform, Integer wechatCount,
			Integer alipayCount) {
		super();
		this.date = date;
		this.hospitalId = hospitalId;
		this.branchId = branchId;
		this.count = count;
		this.platform = platform;
		this.wechatCount = wechatCount;
		this.alipayCount = alipayCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
