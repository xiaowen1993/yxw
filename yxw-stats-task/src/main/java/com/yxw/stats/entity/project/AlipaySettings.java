package com.yxw.stats.entity.project;

import java.io.Serializable;
import java.util.Date;

public class AlipaySettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2189519500027253117L;

	private Long id;

	private String appid;

	private String alipayPublicKey;

	private String signCharset;

	private String charset;

	private String signType;

	private String prikey1;

	private String prikey2;

	private String prikey3;

	private String pubkey1;

	private String pubkey2;

	private String pubkey3;

	private String grantType;

	private String alipayPid;

	private String key;

	private Date version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid == null ? null : appid.trim();
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey == null ? null : alipayPublicKey.trim();
	}

	public String getSignCharset() {
		return signCharset;
	}

	public void setSignCharset(String signCharset) {
		this.signCharset = signCharset == null ? null : signCharset.trim();
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset == null ? null : charset.trim();
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType == null ? null : signType.trim();
	}

	public String getPrikey1() {
		return prikey1;
	}

	public void setPrikey1(String prikey1) {
		this.prikey1 = prikey1 == null ? null : prikey1.trim();
	}

	public String getPrikey2() {
		return prikey2;
	}

	public void setPrikey2(String prikey2) {
		this.prikey2 = prikey2 == null ? null : prikey2.trim();
	}

	public String getPrikey3() {
		return prikey3;
	}

	public void setPrikey3(String prikey3) {
		this.prikey3 = prikey3 == null ? null : prikey3.trim();
	}

	public String getPubkey1() {
		return pubkey1;
	}

	public void setPubkey1(String pubkey1) {
		this.pubkey1 = pubkey1 == null ? null : pubkey1.trim();
	}

	public String getPubkey2() {
		return pubkey2;
	}

	public void setPubkey2(String pubkey2) {
		this.pubkey2 = pubkey2 == null ? null : pubkey2.trim();
	}

	public String getPubkey3() {
		return pubkey3;
	}

	public void setPubkey3(String pubkey3) {
		this.pubkey3 = pubkey3 == null ? null : pubkey3.trim();
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType == null ? null : grantType.trim();
	}

	public String getAlipayPid() {
		return alipayPid;
	}

	public void setAlipayPid(String alipayPid) {
		this.alipayPid = alipayPid == null ? null : alipayPid.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}
}