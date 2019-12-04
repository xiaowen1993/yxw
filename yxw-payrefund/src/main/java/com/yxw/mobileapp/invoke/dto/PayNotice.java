/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2017 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2017年7月5日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */

package com.yxw.mobileapp.invoke.dto;

import java.io.Serializable;

/**
 * @author 谢家贵
 * @version 1.0
 */

public class PayNotice implements Serializable {
    
    /**
     * 
     */
    
    private static final long serialVersionUID = 4954940463705063732L;
    String orderNo;
    String agtOrdNum;
    String payTime;
    String openId;
    String terminalId;
    
    /**
     * @return the orderNo
     */
    
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * @param orderNo
     *            the orderNo to set
     */
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    /**
     * @return the terminalId
     */
    
    public String getTerminalId() {
        return terminalId;
    }
    
    /**
     * @param terminalId
     *            the terminalId to set
     */
    
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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
    
    /**
     * @return the payTime
     */
    
    public String getPayTime() {
        return payTime;
    }
    
    /**
     * @param payTime
     *            the payTime to set
     */
    
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    
    /**
     * @return the openId
     */
    
    public String getOpenId() {
        return openId;
    }
    
    /**
     * @param openId
     *            the openId to set
     */
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
}
