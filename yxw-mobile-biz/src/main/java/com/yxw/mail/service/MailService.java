package com.yxw.mail.service;

import javax.mail.MessagingException;

public interface MailService {
	public void sendHtml(String subject, String content, String... tos) throws MessagingException;

	public void sendHtml(String subject, String content, String[] tos, String[] ccs) throws MessagingException;

	public void sendHtml(String subject, String content, String[] tos, String[] ccs, String[] bccs) throws MessagingException;

	public void close();
}
