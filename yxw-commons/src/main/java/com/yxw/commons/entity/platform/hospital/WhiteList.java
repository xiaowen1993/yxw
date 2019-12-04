/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.hospital;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.vo.cache.WhiteListVo;

/**
 * 访问白名单实体
 * 
 * @Package: com.yxw.platform.hospital.entity
 * @ClassName: WhiteList
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Entity(name = "whiteList")
public class WhiteList extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3328904369921457779L;
	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 医院编码
	 */
	private String hospitalCode;

	private String appId;
	/**
	 * 平台类型,1:微信公众号,2:支付宝服务窗
	 */
	private String appCode;

	/**
	 * 是否开放访问,1:开放,0:关闭,则只能通过白名单访问
	 */
	private Integer isOpen;

	private List<WhiteListDetail> whiteListDetails = new ArrayList<WhiteListDetail>();

	/**
	 * 
	 */
	public WhiteList() {
		super();
	}

	/**
	 * @param hospitalName
	 * @param hospitalId
	 * @param appId
	 * @param platformType
	 * @param isOpen
	 */
	public WhiteList(String hospitalName, String hospitalId, String appId, String appCode, Integer isOpen) {
		super();
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalId;
		this.appId = appId;
		this.appCode = appCode;
		this.isOpen = isOpen;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName
	 *            the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the whiteListDetails
	 */
	public List<WhiteListDetail> getWhiteListDetails() {
		return whiteListDetails;
	}

	/**
	 * @param whiteListDetails
	 *            the whiteListDetails to set
	 */
	public void setWhiteListDetails(List<WhiteListDetail> whiteListDetails) {
		this.whiteListDetails = whiteListDetails;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public WhiteListVo convertWhiteListVo() {
		WhiteListVo vo = new WhiteListVo();
		vo.setAppId(appId);
		vo.setAppCode(appCode);
		vo.setHospitalCode(hospitalCode);
		vo.setHospitalId(hospitalId);
		vo.setIsOpen(isOpen);
		vo.setWhiteListId(id);
		return vo;
	}
}
