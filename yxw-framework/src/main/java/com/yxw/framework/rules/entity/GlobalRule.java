/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月11日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.rules.entity;

import java.io.Serializable;

/**
 * 全局规则配置
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月11日
 */

public class GlobalRule implements Serializable {
    
    private static final long serialVersionUID = -221628924792849659L;
    /**
     * 医院公众号/服务窗ID
     */
    private String appId;
    /**
     * 医院编码
     */
    private String code;
    /**
     * 医院名称
     */
    private String name;
    /**
     * 是否存在分院,true/false
     */
    private boolean isBranch;
    /**
     * web service或者接口请求地址,如果不是web service则不存在自定义命名空间
     */
    private String url;
    /**
     * 自定义命名空间名称
     */
    private String customNamespaceName;
    /**
     * 自定义命名空间地址
     */
    private String customNamespaceUrl;
    /**
     * 接口是否拥有响应状态码
     */
    private String isResponseStatusCode;
    
    public GlobalRule() {
        super();
    }
    
    /**
     * @param appId
     * @param code
     * @param name
     * @param isBranch
     * @param url
     * @param customNamespaceName
     * @param customNamespaceUrl
     * @param isResponseStatusCode
     */
    
    public GlobalRule(String appId, String code, String name, boolean isBranch, String url, String customNamespaceName,
            String customNamespaceUrl, String isResponseStatusCode) {
        super();
        this.appId = appId;
        this.code = code;
        this.name = name;
        this.isBranch = isBranch;
        this.url = url;
        this.customNamespaceName = customNamespaceName;
        this.customNamespaceUrl = customNamespaceUrl;
        this.isResponseStatusCode = isResponseStatusCode;
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
     * @return the code
     */
    
    public String getCode() {
        return code;
    }
    
    /**
     * @param code
     *            the code to set
     */
    
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return the name
     */
    
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     *            the name to set
     */
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the isBranch
     */
    
    public boolean isBranch() {
        return isBranch;
    }
    
    /**
     * @param isBranch
     *            the isBranch to set
     */
    
    public void setBranch(boolean isBranch) {
        this.isBranch = isBranch;
    }
    
    /**
     * @return the url
     */
    
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url
     *            the url to set
     */
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @return the customNamespaceName
     */
    
    public String getCustomNamespaceName() {
        return customNamespaceName;
    }
    
    /**
     * @param customNamespaceName
     *            the customNamespaceName to set
     */
    
    public void setCustomNamespaceName(String customNamespaceName) {
        this.customNamespaceName = customNamespaceName;
    }
    
    /**
     * @return the customNamespaceUrl
     */
    
    public String getCustomNamespaceUrl() {
        return customNamespaceUrl;
    }
    
    /**
     * @param customNamespaceUrl
     *            the customNamespaceUrl to set
     */
    
    public void setCustomNamespaceUrl(String customNamespaceUrl) {
        this.customNamespaceUrl = customNamespaceUrl;
    }
    
    /**
     * @return the isResponseStatusCode
     */
    
    public String getIsResponseStatusCode() {
        return isResponseStatusCode;
    }
    
    /**
     * @param isResponseStatusCode
     *            the isResponseStatusCode to set
     */
    
    public void setIsResponseStatusCode(String isResponseStatusCode) {
        this.isResponseStatusCode = isResponseStatusCode;
    }
    
}
