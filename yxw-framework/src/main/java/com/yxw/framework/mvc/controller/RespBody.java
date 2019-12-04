/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月29日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.mvc.controller;

import java.io.Serializable;

/**
 * 统一的响应格式
 * 
 * @author caiwq
 * 
 */
public class RespBody implements Serializable {
    
    private static final long serialVersionUID = 5905715228490291386L;
    
    private String id;
    
    /**
     * 状态
     */
    private Status status;
    /**
     * 消息描述
     */
    private Object message;
    
    public RespBody() {
        super();
    }
    
    /**
     * @param status
     */
    
    public RespBody(Status status) {
        super();
        this.status = status;
    }
    
    /**
     * @param status
     */
    
    public RespBody(String id, Status status) {
        super();
        this.id = id;
        this.status = status;
    }
    
    /**
     * @description
     * @param status
     *            状态
     * @param message
     *            消息
     */
    public RespBody(Status status, Object message) {
        this.status = status;
        this.message = message;
    }
    
    /**
     * 结果类型信息
     */
    public enum Status {
        OK, ERROR, PROMPT
    }
    
    /**
     * 添加成功结果信息
     */
    public void addOK(Object message) {
        this.message = message;
        this.status = Status.OK;
    }
    
    /**
     * 添加错误消息
     */
    public void addError(Object message) {
        this.message = message;
        this.status = Status.ERROR;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Object getMessage() {
        return message;
    }
    
    public void setMessage(Object message) {
        this.message = message;
    }
}
