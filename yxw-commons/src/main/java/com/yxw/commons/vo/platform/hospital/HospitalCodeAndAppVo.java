/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.hospital;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @Package: com.yxw.platform.hospital.vo
 * @ClassName: HospitalCodeAndAppVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalCodeAndAppVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6748640445004948677L;

	/**
	 * 
	 */
	protected String appId;

	protected String appName;

	protected String appCode;
	/**
	 *微信支付私钥
	 */
	protected String privateKey;

	/**
	 * 支付宝支付公钥，验证参数使用
	 * 2015年7月2日 12:52:55 周鉴斌 删除此字段 使用aliPublicKey 代替
	 
	private String publicKey;*/

	/**
	 * 医院id
	 */
	protected String hospitalId;
	protected String hospitalName;
	protected String hospitalCode;

	/**
	 * 微信：商户号ID， 支付宝：合作者ID
	 */
	protected String mchId;

	/**
	 * 子商户mchId
	 */
	protected String subMchId;

	/**
	 * 是否有子商户   1 有   0 没有
	 */
	protected Integer isSubPay;

	/**
	 * 商户key  appSecret
	 */
	protected String paykey;

	/**
	 * openId
	 */
	protected String mchOpenId;

	/**
	 * 支付配置ID 用户查询支付或者退款密钥
	 */
	protected String paySettingId;

	/**
	 * 微信退款的密钥文件路径
	 */
	protected String refundFilePath;

	/**
	 * 微信退款的密钥文件名
	 */
	protected String refundFileName;

	/**
	 * 支付宝卖家账号
	 */
	protected String mchAccount;

	/**
	 * 支付宝支付私钥
	 */
	protected String aliPrivateKey;

	/**
	 * 支付宝支付公钥，验证参数使用
	 */
	protected String aliPublicKey;

	/**
	 * 父商户appId
	 * 2015年7月28日 20:04:18 周鉴斌增加父商户appId
	 */
	protected String parentAppId;

	/**
	 * 父商户secret
	 * 2015年7月28日 20:04:18 周鉴斌增加父商户secret
	 */
	protected String parentSecret;

	/**
	 * 支付方式，code
	 * 2016年4月23日 20:58:18 蔡万桥
	 */
	protected String payModeCode;

	/**
	 * 交易类型，值
	 */
	protected String tradeMode;

	/**
	 * 合作者Id 暂时为支付宝使用
	 */
	private String partnerId;

	private String appSecret;

	/**
	 * 银联商户证书密码
	 */
	private String certificatePwd;

	public HospitalCodeAndAppVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HospitalCodeAndAppVo(String appId, String appCode, String hospitalId, String hospitalCode) {
		super();
		this.appId = appId;
		this.appCode = appCode;
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
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

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public Integer getIsSubPay() {
		return isSubPay;
	}

	public void setIsSubPay(Integer isSubPay) {
		this.isSubPay = isSubPay;
	}

	public String getPaykey() {
		return paykey;
	}

	public void setPaykey(String paykey) {
		this.paykey = paykey;
	}

	public String getMchOpenId() {
		return mchOpenId;
	}

	public void setMchOpenId(String mchOpenId) {
		this.mchOpenId = mchOpenId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPaySettingId() {
		return paySettingId;
	}

	public void setPaySettingId(String paySettingId) {
		this.paySettingId = paySettingId;
	}

	/*public String getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}*/

	public String getMchAccount() {
		return mchAccount;
	}

	public void setMchAccount(String mchAccount) {
		this.mchAccount = mchAccount;
	}

	public String getAliPrivateKey() {
		return aliPrivateKey;
	}

	public void setAliPrivateKey(String aliPrivateKey) {
		this.aliPrivateKey = aliPrivateKey;
	}

	public String getAliPublicKey() {
		return aliPublicKey;
	}

	public void setAliPublicKey(String aliPublicKey) {
		this.aliPublicKey = aliPublicKey;
	}

	public String getRefundFilePath() {
		return refundFilePath;
	}

	public void setRefundFilePath(String refundFilePath) {
		this.refundFilePath = refundFilePath;
	}

	public String getParentAppId() {
		return parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}

	public String getParentSecret() {
		return parentSecret;
	}

	public void setParentSecret(String parentSecret) {
		this.parentSecret = parentSecret;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCertificatePwd() {
		return certificatePwd;
	}

	public void setCertificatePwd(String certificatePwd) {
		this.certificatePwd = certificatePwd;
	}

	public String getRefundFileName() {
		if (StringUtils.isNotBlank(refundFilePath)) {
			int index = refundFilePath.lastIndexOf(java.io.File.separatorChar);
			if (index > -1) {
				refundFileName = refundFilePath.substring(index + 1);
			} else {
				if (java.io.File.separatorChar == '\\') {
					index = refundFilePath.lastIndexOf('/');
				} else {
					index = refundFilePath.lastIndexOf('\\');
				}
				if (index > -1) {
					refundFileName = refundFilePath.substring(index + 1);
				} else {
					refundFileName = refundFilePath;
				}
			}
		}
		return refundFileName;
	}

	public void setRefundFileName(String refundFileName) {
		this.refundFileName = refundFileName;
	}
}
