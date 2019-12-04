/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2017 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2017-8-22</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

/**
 * @author LiangJinXin
 * @version 1.0
 * @date 2017-8-22
 */

public class TradeOrderQueryParams implements Serializable {
    
    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 微信/支付宝appid
     */
    private String appId;
    
    /**
     * 支付方式 0: 全部 1：微信 2：支付宝
     */
    private String tradeMode;
    
    /**
     * 分院code
     */
    private String branchCode;
    
    /**
     * 医享网平台订单号
     */
    private String psOrdNum;
    
    /**
     * 第三方交易流水号
     */
    private String agtOrdNum;
    
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
     * @return the tradeMode
     */
    
    public String getTradeMode() {
        return tradeMode;
    }
    
    /**
     * @param tradeMode
     *            the tradeMode to set
     */
    
    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }
    
    /**
     * @return the branchCode
     */
    
    public String getBranchCode() {
        return branchCode;
    }
    
    /**
     * @param branchCode
     *            the branchCode to set
     */
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
    /**
     * @return the psOrdNum
     */
    
    public String getPsOrdNum() {
        return psOrdNum;
    }
    
    /**
     * @param psOrdNum
     *            the psOrdNum to set
     */
    
    public void setPsOrdNum(String psOrdNum) {
        this.psOrdNum = psOrdNum;
    }
    
    /**
     * @return the agtOrdNum
     */
    
    public String getAgtOrdNum() {
        return agtOrdNum;
    }
    
    /**
     * @param agtOrdNum
     *            the agtOrdNum to set
     */
    
    public void setAgtOrdNum(String agtOrdNum) {
        this.agtOrdNum = agtOrdNum;
    }
    
}
