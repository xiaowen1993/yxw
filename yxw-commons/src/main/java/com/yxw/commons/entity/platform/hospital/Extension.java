package com.yxw.commons.entity.platform.hospital;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 
 * @Package: com.yxw.platform.hospital.entity
 * @ClassName: Extension
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Entity(name = "extension")
public class Extension extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8429421005142909146L;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 区域或者人员
	 */
	private String district;

	/**
	 * 类型1:微信 2:支付宝
	 */
	private Integer extensionMode;

	/**
	 * sceneid
	 */
	private String sceneid;

	/**
	 * 二维码外部地址
	 */
	private String outsideUrl;

	/**
	 * 二维码外部地址
	 */
	private String insideUrl;

	/**
	 * 推广返回sign 支付宝
	 */
	private String sign;

	/**
	 * appId
	 */
	private String appId;

	/**
	 * 统计数
	 */
	private int count;

	public Extension() {
		super();
	}

	public Extension(String hospitalId, String district, int extensionMode, String sceneid, String outsideUrl, String insideUrl, String sign,
			String appId) {
		super();
		this.hospitalId = hospitalId;
		this.district = district;
		this.extensionMode = extensionMode;
		this.sceneid = sceneid;
		this.outsideUrl = outsideUrl;
		this.insideUrl = insideUrl;
		this.sign = sign;
		this.appId = appId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getExtensionMode() {
		return extensionMode;
	}

	public void setExtensionMode(Integer extensionMode) {
		this.extensionMode = extensionMode;
	}

	public String getSceneid() {
		return sceneid;
	}

	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}

	public String getOutsideUrl() {
		return outsideUrl;
	}

	public void setOutsideUrl(String outsideUrl) {
		this.outsideUrl = outsideUrl;
	}

	public String getInsideUrl() {
		return insideUrl;
	}

	public void setInsideUrl(String insideUrl) {
		this.insideUrl = insideUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
