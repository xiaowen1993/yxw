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

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 接入平台配置信息实体
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "platformSettings")
public class PlatformSettings extends BaseEntity {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -4980797208312198076L;
    
    /**
     * appId
     */
    private String appId;
    
    /**
     * 微信：appSecret， 支付宝：支付宝私匙
     */
    private String privateKey;
    
    /**
     * 公匙
     */
    private String publicKey;
    
    /**
     * token值
     */
    private String token;
    
    /**
     * 微信公众帐号
     */
    private String wechatAccount;
    
    /**
     * 接入平台ID
     */
    private String platformId;
    
    /**
     * 所属接入平台
     */
    private Platform platform;
    
    /**
     * 对应的医院
     */
    private Hospital hospital;
    
    /**
     * 接入平台Code
     */
    private String code;
    
    /**
     * 中间表Id
     */
    private String hpsId;
    
    /**
     * 接入配置数据ID
     */
    private String pfId;
    
    /**
     * 接入配置平台名称
     */
    private String pfName;
    
    /**
     * @return the pfName
     */
    
    public String getPfName() {
        return pfName;
    }
    
    /**
     * @param pfName
     *            the pfName to set
     */
    
    public void setPfName(String pfName) {
        this.pfName = pfName;
    }
    
    /**
	 * 
	 */
    public PlatformSettings() {
        super();
    }
    
    /**
     * @param appId
     * @param privateKey
     * @param publicKey
     */
    public PlatformSettings(String appId, String privateKey, String publicKey) {
        super();
        this.appId = appId;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
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
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }
    
    /**
     * @param privateKey
     *            the privateKey to set
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    /**
     * @return the publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }
    
    /**
     * @param publicKey
     *            the publicKey to set
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    /**
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }
    
    /**
     * @param platform
     *            the platform to set
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
    
    /**
     * @return the hospital
     */
    public Hospital getHospital() {
        return hospital;
    }
    
    /**
     * @param hospital
     *            the hospital to set
     */
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    
    public String getPlatformId() {
        return platformId;
    }
    
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getHpsId() {
        return hpsId;
    }
    
    public void setHpsId(String hpsId) {
        this.hpsId = hpsId;
    }
    
    public String getPfId() {
        return pfId;
    }
    
    public void setPfId(String pfId) {
        this.pfId = pfId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getWechatAccount() {
        return wechatAccount;
    }
    
    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }
    
}
