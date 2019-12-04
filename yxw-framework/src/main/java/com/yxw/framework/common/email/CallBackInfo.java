/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月26日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.email;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 邮件回调信息
 * </p>
 * 
 * @author 申午武
 * 
 */
public class CallBackInfo implements Serializable {
    private static final long serialVersionUID = 5401971939678483405L;
    /**
     * 发送状态
     */
    private boolean status;
    /**
     * 邮件发送时间
     */
    private Date sentDate;
    /**
     * 尝试次数,暂时无用
     */
    @Deprecated
    private Integer attempts;
    
    public CallBackInfo() {
        
    }
    
    public CallBackInfo(boolean status, Date sentDate, Integer attempts) {
        this.status = status;
        this.sentDate = sentDate;
        this.attempts = attempts;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Date getSentDate() {
        return sentDate;
    }
    
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
    
    @Deprecated
    public Integer getAttempts() {
        return attempts;
    }
    
    @Deprecated
    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }
    
}
