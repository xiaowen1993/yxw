/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年3月17日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.sdk.vo;

/**
 * 医院实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月17日
 */
public class VoForSDK {
    
    private Long id;
    private String appId;
    private String privateKey; // 微信：appSecret， 支付宝：privateKey（支付宝私匙，建议长度1000）
    private String publicKey; // 支付宝公匙，跟支付宝私匙是一对。这个字段在程序里用不到，但是需要配置到支付宝商户平台里去，这里只是放在数据库做记录，建议长度1000
    private String name;
    private String simpleName;
    private String platformCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }
    
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    public String getPublicKey() {
        return publicKey;
    }
    
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSimpleName() {
        return simpleName;
    }
    
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    
    public String getPlatformCode() {
        return platformCode;
    }
    
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    
}
