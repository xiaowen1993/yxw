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

import com.yxw.base.entity.BaseEntity;

/**
 * 支付配置信息实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
public class PaySettings extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7595011328885023160L;

	/**
	 * 商户号
	 */
	private String mchId;

	/**
	 * 子商户号
	 */
	private String subMchId;

	/**
	 * 支付秘钥
	 */
	private String payKey;

	/**
	 * 证书路径
	 */
	private String certificatePath;

	/**
	 * 是否子商户支付 0：非子商户 1：子商户
	 */
	private Integer isSubPay = 0;

	/**
	 * 商家账号 (支付宝支付时表示email帐号)
	 */
	private String mchAccount;

	/**
	 * 合作者Id 暂时为支付宝使用
	 */
	private String partnerId;

	/**
	 * 支付宝支付私钥
	 */
	private String payPrivateKey;

	/**
	 * 支付宝支付私钥
	 */
	private String payPublicKey;

	/**
	 * 支付方式
	 */
	private PayMode payMode;

	/**
	 * 支付方式ID
	 */
	private String payModeId;

	/**
	 * 主商户支付配置信息，主商户支付的时候用到
	 */
	private PaySettings parentPaySettings;

	/**
	 * 对应的医院
	 */
	private Hospital hospital;

	/**
	 * 支付方式Code
	 */
	private String code;

	/**
	 * 中间表Id
	 */
	private String hpsId;

	/**
	 * 支付方式ID
	 */
	private String pmId;

	/**
	 * 父商户apId
	 */
	private String parentAppId;

	/**
	 * 父商户Secret
	 */
	private String parentSecret;

	//2016-04-29添加字段，by homer.yang
	private String appId;
	private String appSecret;

	/**
	 * 银联商户证书密码
	 */
	private String certificatePwd;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	public String getHpsId() {
		return hpsId;
	}

	public void setHpsId(String hpsId) {
		this.hpsId = hpsId;
	}

	/**
	 * @return the mchId
	 */
	public String getMchId() {
		return mchId;
	}

	/**
	 * @param mchId
	 *            the mchId to set
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	/**
	 * @return the payKey
	 */
	public String getPayKey() {
		return payKey;
	}

	/**
	 * @param payKey
	 *            the payKey to set
	 */
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	/**
	 * @return the certificatePath
	 */
	public String getCertificatePath() {
		return certificatePath;
	}

	/**
	 * @param certificatePath
	 *            the certificatePath to set
	 */
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	/**
	 * @return the mchAccount
	 */
	public String getMchAccount() {
		return mchAccount;
	}

	/**
	 * @param mchAccount
	 *            the mchAccount to set
	 */
	public void setMchAccount(String mchAccount) {
		this.mchAccount = mchAccount;
	}

	public String getPayPrivateKey() {
		return payPrivateKey;
	}

	public void setPayPrivateKey(String payPrivateKey) {
		this.payPrivateKey = payPrivateKey;
	}

	public String getPayPublicKey() {
		return payPublicKey;
	}

	public void setPayPublicKey(String payPublicKey) {
		this.payPublicKey = payPublicKey;
	}

	/**
	 * @return the payMode
	 */
	public PayMode getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode
	 *            the payMode to set
	 */
	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the parentPaySettings
	 */
	public PaySettings getParentPaySettings() {
		return parentPaySettings;
	}

	/**
	 * @param parentPaySettings
	 *            the parentPaySettings to set
	 */
	public void setParentPaySettings(PaySettings parentPaySettings) {
		this.parentPaySettings = parentPaySettings;
	}

	/**
	 * @return the hospital
	 */
	public Hospital getHospital() {
		return hospital;
	}

	/**
	 * @param hospital the hospital to set
	 */
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Integer getIsSubPay() {
		return isSubPay;
	}

	public void setIsSubPay(Integer isSubPay) {
		this.isSubPay = isSubPay;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(String payModeId) {
		this.payModeId = payModeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCertificatePwd() {
		return certificatePwd;
	}

	public void setCertificatePwd(String certificatePwd) {
		this.certificatePwd = certificatePwd;
	}

}
