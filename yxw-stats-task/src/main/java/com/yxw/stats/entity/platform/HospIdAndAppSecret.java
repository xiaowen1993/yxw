package com.yxw.stats.entity.platform;

import java.io.Serializable;

/**
 * 医院ID及APPSECRET值对象
 * */
public class HospIdAndAppSecret implements Serializable, Comparable<HospIdAndAppSecret> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4079329816185772197L;
	/**
	 * 医院id
	 * */
	private String hospId;

	private String hospCode;

	private String hospName;

	/**
	 * 医院所属的区域代码
	 */
	private String areaCode;

	/**
	 * 公众号密钥
	 * */
	private String appSecret;

	/**
	 * TOKEN
	 * */
	private String token;
	/**
	 * 支付宝 服务窗公钥
	 * */
	private String publicKey;

	private String appId;

	private String appCode;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getHospId() {
		return hospId;
	}

	public void setHospId(String hospId) {
		this.hospId = hospId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	@Override
	public int compareTo(HospIdAndAppSecret o) {
		// TODO Auto-generated method stub
		if (o == null) {
			return 1;
		} else {
			if (hospId.hashCode() > o.getHospId().hashCode()) {
				return 1;
			} else if (hospId.hashCode() < o.getHospId().hashCode()) {
				return -1;
			}
		}
		return 0;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
