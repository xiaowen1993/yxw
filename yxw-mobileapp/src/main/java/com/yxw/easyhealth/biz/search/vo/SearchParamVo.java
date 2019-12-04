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

/**
 * @Package: com.yxw.mobileapp.biz.search.vo
 * @ClassName: SearchParamVo
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-10-19
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SearchParamVo implements Serializable {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -7639621231066600432L;
    /**
     * 搜索输入
     */
    private String searchStr;
    
    private String openId;
    
    private String appId;
    
    /**
     * 渠道 BizConstant.MODE_TYPE_WEIXIN = "wechat" 微信 BizConstant.MODE_TYPE_ALIPAY = "alipay" 支付宝
     * BizConstant.MODE_TYPE_EASYHEALTH = "easyHealth" 健康易
     */
    private String appCode;
    
    /**
     * 区域
     */
    private String areaCode;
    
    /**
     * 搜索类型 BizConstant.SEARCH_TYPE_HOSPITAL = "hospital" BizConstant.SEARCH_TYPE_DEPT = "dept"
     * BizConstant.SEARCH_TYPE_DOCTOR = "doctor"
     */
    private String searchType;
    
    /**
	 * 
	 */
    private Integer regType;
    
    private String parentDeptCode;
    
    private String parentDeptName;
    
    private String hospitalId;
    
    /**
     * 医院code
     */
    private String hospitalCode;
    
    /**
     * 医院Name
     */
    private String hospitalName;
    
    /**
     * 医院代码,医院没有分院则返回空字符串
     */
    private String branchHospitalCode;
    /**
     * 医院名称,医院没有分院则返回空字符串
     */
    private String branchHospitalName;
    
    public SearchParamVo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public SearchParamVo(String searchStr, String appCode, String areaCode) {
        super();
        this.searchStr = searchStr;
        this.appCode = appCode;
        this.areaCode = areaCode;
    }
    
    public String getSearchStr() {
        return searchStr;
    }
    
    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
    
    public String getAppCode() {
        return appCode;
    }
    
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getSearchType() {
        return searchType;
    }
    
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    
    public Integer getRegType() {
        return regType;
    }
    
    public void setRegType(Integer regType) {
        this.regType = regType;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getParentDeptCode() {
        return parentDeptCode;
    }
    
    public void setParentDeptCode(String parentDeptCode) {
        this.parentDeptCode = parentDeptCode;
    }
    
    public String getParentDeptName() {
        return parentDeptName;
    }
    
    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
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
    
    public String getHospitalName() {
        return hospitalName;
    }
    
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    
    public String getBranchHospitalCode() {
        return branchHospitalCode;
    }
    
    public void setBranchHospitalCode(String branchHospitalCode) {
        this.branchHospitalCode = branchHospitalCode;
    }
    
    public String getBranchHospitalName() {
        return branchHospitalName;
    }
    
    public void setBranchHospitalName(String branchHospitalName) {
        this.branchHospitalName = branchHospitalName;
    }
    
    public String getOpenId() {
        return openId;
    }
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
