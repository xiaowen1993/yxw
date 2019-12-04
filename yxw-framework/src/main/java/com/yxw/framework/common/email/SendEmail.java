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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.validation.Validation;
import com.yxw.framework.exception.SystemException;

/**
 * <p>
 * 发送邮件.
 * </p>
 * 
 * @author 申午武
 * 
 */
public class SendEmail {
    private static Logger logger = LoggerFactory.getLogger(SendEmail.class);
    
    public SendEmail() {
        
    }
    
    /***
     * 发送邮件
     * 
     * @param mailInfo
     *            邮件信息
     * @return CallBackInfo 回调信息
     */
    public CallBackInfo sendEmail(EmailInfo emailInfo) {
        CallBackInfo callBackInfo = new CallBackInfo();
        if (emailInfo == null) {
            logger.error("The mailInfo is can not null.\n");
            throw new SystemException("The mailInfo is can not null");
            
        } else {
            Email email = makeMail(emailInfo);
            try {
                email.send();
                // 记录回调信息
                callBackInfo.setStatus(true);
                callBackInfo.setSentDate(email.getSentDate());
            } catch (EmailException e) {
                callBackInfo.setStatus(false);
                callBackInfo.setSentDate(null);
                logger.error("the mail sends out the failure.\n", e);
                e.printStackTrace();
            }
            
        }
        return callBackInfo;
    }
    
    /***
     * 构造邮件信息,支持附件,群发,抄送,密送,支持带HTML格式的内容
     * 
     * @param mailInfo
     *            邮件信息
     */
    private Email makeMail(EmailInfo emailInfo) {
        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(emailInfo.getHostName());
            email.setSmtpPort(emailInfo.getSmtpPort());
            email.setSSLOnConnect(emailInfo.isSSLOnConnect());
            email.setCharset(emailInfo.getEncode());
            email.setDebug(emailInfo.isDebug());
            email.setAuthentication(emailInfo.getUserName(), emailInfo.getPassWord());
            email.setFrom(emailInfo.getFrom(), emailInfo.getSenderName());
            if (emailInfo.getTo() != null && emailInfo.getTo().size() <= 0) {
                logger.error("The recipient can not be empty.\n");
                throw new SystemException("The recipient can not be empty");
            } else {
                email.setTo(convertInternetAddress(emailInfo.getTo()));
            }
            if (emailInfo.getCc() != null && emailInfo.getCc().size() > 0) {
                email.setCc(convertInternetAddress(emailInfo.getCc()));
            }
            if (emailInfo.getBcc() != null && emailInfo.getBcc().size() > 0) {
                email.setBcc(convertInternetAddress(emailInfo.getBcc()));
            }
            email.setSubject(emailInfo.getTitle());
            if (emailInfo.isHTMLFormat()) {
                // 包含HTML格式的内容
                email.setHtmlMsg(emailInfo.getContent());
            } else {
                // 包含普通文本的内容
                email.setTextMsg(emailInfo.getContent());
            }
            // 添加附件
            List<EmailAttachment> emailAttachments = addAttachment(emailInfo.getAttachmentInfos());
            for (int i = 0; i < emailAttachments.size(); i++) {
                EmailAttachment emailAttachment = emailAttachments.get(i);
                email.attach(emailAttachment);
            }
        } catch (EmailException e) {
            logger.error("Construct the email information error.\n", e);
            e.printStackTrace();
        }
        return email;
    }
    
    /**
     * 转换为Internet地址
     * 
     * @param userMap
     * @return
     */
    private List<InternetAddress> convertInternetAddress(Map<String, String> userMap) {
        List<InternetAddress> list = new ArrayList<InternetAddress>(userMap.size());
        for (Entry<String, String> entry : userMap.entrySet()) {
            try {
                list.add(new InternetAddress(entry.getKey(), entry.getValue()));
            } catch (UnsupportedEncodingException e) {
                logger.error("the email InternetAddress UnsupportedEncoding error.\n");
                e.printStackTrace();
            }
            
        }
        return list;
    }
    
    /***
     * 添加附件
     * 
     * @param attachmentInfos
     *            附件集合
     * @return 经过处理的附件集合
     */
    private List<EmailAttachment> addAttachment(List<AttachmentInfo> attachmentInfos) {
        List<EmailAttachment> emailAttachments = new ArrayList<EmailAttachment>();
        try {
            if (attachmentInfos != null && attachmentInfos.size() > 0) {
                for (int i = 0; i < attachmentInfos.size(); i++) {
                    AttachmentInfo attachmentInfo = attachmentInfos.get(i);
                    // 添加附件
                    EmailAttachment emailAttachment = new EmailAttachment();
                    emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                    // 是否是网络附件
                    if (Validation.validationURL(attachmentInfo.getAttachmentPath())) {
                        emailAttachment.setURL(new URL(attachmentInfo.getAttachmentPath()));
                    } else {
                        // 本地附件
                        emailAttachment.setPath(attachmentInfo.getAttachmentPath());
                    }
                    // 指定附件名称
                    String attachName = null;
                    if (attachmentInfo.getAttachmentName().lastIndexOf(".") == -1) {
                        attachName = attachmentInfo.getAttachmentName()
                                + attachmentInfo.getAttachmentPath().substring(
                                        attachmentInfo.getAttachmentPath().lastIndexOf("."),
                                        attachmentInfo.getAttachmentPath().length());
                    } else {
                        attachName = attachmentInfo.getAttachmentName();
                    }
                    emailAttachment.setName(MimeUtility.encodeText(attachName));
                    emailAttachments.add(emailAttachment);
                }
            }
        } catch (Exception e) {
            logger.error("add EmailAttachment error.\n", e);
            e.printStackTrace();
        }
        return emailAttachments;
    }
}
