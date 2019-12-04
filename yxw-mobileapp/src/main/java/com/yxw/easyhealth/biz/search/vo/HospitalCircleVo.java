/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-10-19</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.search.vo;

import java.io.Serializable;

public class HospitalCircleVo implements Serializable {

	private static final long serialVersionUID = 4079329816185772197L;

	private String hospId;

	private String hospCode;

	private String hospName;

	private String areaCode;

	private String appId;

	private String appCode;

	private String hadReg;

	/**
	 * 分院地址
	 */
	private String address;

	/**
	 * 分院电话
	 */
	private String tel;

	private String distance;
	/**
	 * 经度
	 * */
	private String longitude;
	/**
	 * 纬度
	 * */
	private String latitude;

	public HospitalCircleVo() {

	}

	public HospitalCircleVo(String hospId, String hospCode, String hospName, String address, String tel, String appId, String appCode) {
		super();
		this.hospId = hospId;
		this.hospCode = hospCode;
		this.hospName = hospName;
		this.address = address;
		this.tel = tel;
		this.appId = appId;
		this.appCode = appCode;
	}

	public HospitalCircleVo(String hospId, String hospCode, String hospName, String address, String tel, String distance) {
		super();
		this.hospId = hospId;
		this.hospCode = hospCode;
		this.hospName = hospName;
		this.address = address;
		this.tel = tel;
		this.distance = distance;
	}

	public String getHadReg() {
		return hadReg;
	}

	public void setHadReg(String hadReg) {
		this.hadReg = hadReg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getHospId() {
		return hospId;
	}

	public void setHospId(String hospId) {
		this.hospId = hospId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getHospCode() {
		return hospCode;
	}

	public void setHospCode(String hospCode) {
		this.hospCode = hospCode;
	}

	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
