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

/**
 * <p>
 * 邮件附件
 * </p>
 * 
 * @author 申午武
 * 
 */
public class AttachmentInfo implements Serializable {
    private static final long serialVersionUID = -638346101635261172L;
    /**
     * 附件名称
     */
    private String attachmentName;
    /**
     * 附件地址
     */
    private String attachmentPath;
    
    public AttachmentInfo() {
        
    }
    
    public AttachmentInfo(String attachmentName, String attachmentPath) {
        this.attachmentName = attachmentName;
        this.attachmentPath = attachmentPath;
    }
    
    public String getAttachmentName() {
        return attachmentName;
    }
    
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
    
    public String getAttachmentPath() {
        return attachmentPath;
    }
    
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
    
}
