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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 邮件信息.
 * </p>
 * 
 * @author 申午武
 * 
 */
public class EmailInfo implements Serializable {
    private static final long serialVersionUID = -4161632580579770239L;
    /***
     * stmp服务器地址
     */
    private String hostName;
    /***
     * stmp端口
     */
    private int smtpPort;
    /***
     * 编码
     */
    private String encode;
    
    /***
     * 是否启用SSL安全链接
     */
    private boolean isSSLOnConnect;
    /***
     * 是否发送HTML格式的邮件
     */
    private boolean isHTMLFormat;
    /***
     * 是否开启调试
     */
    private boolean isDebug;
    /***
     * 用户名
     */
    private String userName;
    /***
     * 密码
     */
    private String passWord;
    /***
     * 发件人
     */
    private String from;
    /***
     * 发件人名称
     */
    private String senderName;
    /***
     * 邮件标题
     */
    private String title;
    /***
     * 邮件内容
     */
    private String content;
    /***
     * 发送地址,key:邮箱地址,value:昵称
     */
    private Map<String, String> to = new HashMap<String, String>();
    /***
     * 抄送地址,key:邮箱地址,value:昵称
     */
    private Map<String, String> cc = new HashMap<String, String>();
    /***
     * 密送地址,key:邮箱地址,value:昵称
     */
    private Map<String, String> bcc = new HashMap<String, String>();
    /***
     * 附件
     */
    private List<AttachmentInfo> attachmentInfos = new ArrayList<AttachmentInfo>();
    
    public EmailInfo() {
        
    }
    
    /**
     * @param hostName
     *            stmp服务器地址.
     * @param smtpPort
     *            stmp服务器端口.
     * @param encode
     *            编码.
     * @param isSSLOnConnect
     *            是否启用SSL安全链接.
     * @param isHTMLFormat
     *            是否发送HTML格式的邮件.
     * @param isDebug
     *            是否开启调试.
     * @param userName
     *            用户名.
     * @param passWord
     *            密码.
     * @param from
     *            发件人.
     * @param senderName
     *            发件人名称.
     * @param title
     *            邮件标题.
     * @param content
     *            邮件内容.
     * @param to
     *            发送地址.
     * @param cc
     *            抄送地址.
     * @param bcc
     *            密送地址.
     * @param attachmentInfos
     *            附件.
     */
    public EmailInfo(String hostName, int smtpPort, String encode, boolean isSSLOnConnect, boolean isHTMLFormat,
            boolean isDebug, String userName, String passWord, String from, String senderName, String title,
            String content, Map<String, String> to, Map<String, String> cc, Map<String, String> bcc,
            List<AttachmentInfo> attachmentInfos) {
        super();
        this.hostName = hostName;
        this.smtpPort = smtpPort;
        this.encode = encode;
        this.isSSLOnConnect = isSSLOnConnect;
        this.isHTMLFormat = isHTMLFormat;
        this.isDebug = isDebug;
        this.userName = userName;
        this.passWord = passWord;
        this.from = from;
        this.senderName = senderName;
        this.title = title;
        this.content = content;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.attachmentInfos = attachmentInfos;
    }
    
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
    
    /**
     * @param hostName
     *            the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    /**
     * @return the smtpPort
     */
    public int getSmtpPort() {
        return smtpPort;
    }
    
    /**
     * @param smtpPort
     *            the smtpPort to set
     */
    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }
    
    /**
     * @return the encode
     */
    public String getEncode() {
        return encode;
    }
    
    /**
     * @param encode
     *            the encode to set
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }
    
    /**
     * @return the isSSLOnConnect
     */
    public boolean isSSLOnConnect() {
        return isSSLOnConnect;
    }
    
    /**
     * @param isSSLOnConnect
     *            the isSSLOnConnect to set
     */
    public void setSSLOnConnect(boolean isSSLOnConnect) {
        this.isSSLOnConnect = isSSLOnConnect;
    }
    
    /**
     * @return the isHTMLFormat
     */
    public boolean isHTMLFormat() {
        return isHTMLFormat;
    }
    
    /**
     * @param isHTMLFormat
     *            the isHTMLFormat to set
     */
    public void setHTMLFormat(boolean isHTMLFormat) {
        this.isHTMLFormat = isHTMLFormat;
    }
    
    /**
     * @return the isDebug
     */
    public boolean isDebug() {
        return isDebug;
    }
    
    /**
     * @param isDebug
     *            the isDebug to set
     */
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * @return the passWord
     */
    public String getPassWord() {
        return passWord;
    }
    
    /**
     * @param passWord
     *            the passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }
    
    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }
    
    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }
    
    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * @return the to
     */
    public Map<String, String> getTo() {
        return to;
    }
    
    /**
     * @param to
     *            the to to set
     */
    public void setTo(Map<String, String> to) {
        this.to = to;
    }
    
    /**
     * @return the cc
     */
    public Map<String, String> getCc() {
        return cc;
    }
    
    /**
     * @param cc
     *            the cc to set
     */
    public void setCc(Map<String, String> cc) {
        this.cc = cc;
    }
    
    /**
     * @return the bcc
     */
    public Map<String, String> getBcc() {
        return bcc;
    }
    
    /**
     * @param bcc
     *            the bcc to set
     */
    public void setBcc(Map<String, String> bcc) {
        this.bcc = bcc;
    }
    
    /**
     * @return the attachmentInfos
     */
    public List<AttachmentInfo> getAttachmentInfos() {
        return attachmentInfos;
    }
    
    /**
     * @param attachmentInfos
     *            the attachmentInfos to set
     */
    public void setAttachmentInfos(List<AttachmentInfo> attachmentInfos) {
        this.attachmentInfos = attachmentInfos;
    }
    
}
