/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月12日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.attach;

import java.io.Serializable;

public class FileInfo implements Serializable {
    
    private static final long serialVersionUID = -1438047718644325029L;
    /**
     * 原始名称
     */
    private String originalName;
    /**
     * 真实名称
     */
    private String archiveName;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 绝对路径
     */
    private String absolutePath;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 存档状态
     */
    private boolean status;
    /**
     * 描述信息
     */
    private String msg;
    
    public FileInfo() {
        
    }
    
    public String getOriginalName() {
        return originalName;
    }
    
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    
    public String getArchiveName() {
        return archiveName;
    }
    
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }
    
    public String getRelativePath() {
        return relativePath;
    }
    
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    
    public String getAbsolutePath() {
        return absolutePath;
    }
    
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
    
    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
