package com.yxw.mail.service.impl;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.yxw.mail.service.MailService;

public class MailServiceImpl implements MailService {
	private static ConcurrentHashMap<String, Address> cacheAddress = new ConcurrentHashMap<String, Address>();

	private Session session;
	private Transport transport;
	private SendThread sendThread;
	private String user;

	public MailServiceImpl(Properties p) throws NoSuchProviderException {
		session = Session.getInstance(p);
		String protocol = p.getProperty("mail.transport.protocol");
		String host = p.getProperty("mail." + protocol + ".host");
		int port = -1;
		try {
			port = Integer.valueOf(p.getProperty("mail." + protocol + ".port"));
		} catch (Exception e) {
		}
		user = p.getProperty("mail." + protocol + ".user");
		String password = p.getProperty("mail." + protocol + ".password");
		transport = session.getTransport(new URLName(protocol, host, port, null, user, password));

		sendThread = new SendThread();
		sendThread.start();
		sendThread.suspend();
	}

	@Override
	public void sendHtml(String subject, String content, String... tos) throws MessagingException {
		sendHtml(subject, content, tos, null, null);
	}

	@Override
	public void sendHtml(String subject, String content, String[] tos, String[] ccs) throws MessagingException {
		sendHtml(subject, content, tos, ccs, null);
	}

	@Override
	public void sendHtml(String subject, String content, String[] tos, String[] ccs, String[] bccs) throws MessagingException {
		int toCount = 0;
		if (tos != null && tos.length > 0) {
			toCount = tos.length;
		}
		int ccCount = 0;
		if (ccs != null && ccs.length > 0) {
			ccCount = ccs.length;
		}
		int bccCount = 0;
		if (bccs != null && bccs.length > 0) {
			bccCount = bccs.length;
		}
		if (toCount + ccCount + bccCount < 1) {
			return;
		}

		Message message = new MimeMessage(session);
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=UTF-8");
		message.setFrom(getAddress(user));

		if (toCount > 0) {
			for (String to : tos) {
				message.addRecipient(Message.RecipientType.TO, getAddress(to));
			}
		}
		if (ccCount > 0) {
			for (String cc : ccs) {
				message.addRecipient(Message.RecipientType.CC, getAddress(cc));
			}
		}
		if (bccCount > 0) {
			for (String bcc : bccs) {
				message.addRecipient(Message.RecipientType.BCC, getAddress(bcc));
			}
		}

		message.setSentDate(new Date());
		message.saveChanges();

		sendThread.addMessage(message);
	}

	@Override
	public void close() {
		sendThread.interrupt();
		sendThread.resume();
	}

	protected static Address getAddress(String name) throws AddressException {
		Address address = cacheAddress.get(name);
		if (address == null) {
			address = new InternetAddress(name);
			cacheAddress.putIfAbsent(name, address);
		}
		return address;
	}

	private class SendThread extends Thread {
		private ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();

		@Override
		public void run() {
			while (!isInterrupted()) {
				if (queue.isEmpty()) {
					suspend();
					// 从[自我]挂起中恢复后需要重新检查队列是否为空
					// 排除队列中为空而继续执行的情况(外部直接调用resume()方法恢复线程时)
					continue;
				}

				Message message = null;
				try {
					transport.connect();

					while ( ( message = queue.poll() ) != null) {
						transport.sendMessage(message, message.getAllRecipients());
					}
				} catch (Exception e) {
					// 发送邮件异常时需要重发
					if (message != null) {
						queue.add(message);
					}
				} finally {
					try {
						transport.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void addMessage(Message message) {
			if (message == null) {
				return;
			}

			queue.add(message);
			resume();
		}
	}
}
