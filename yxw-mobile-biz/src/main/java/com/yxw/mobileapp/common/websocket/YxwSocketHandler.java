package com.yxw.mobileapp.common.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgcenter.service.MsgCenterService;
import com.yxw.mobileapp.common.websocket.YxwMessage.MessageType;

public class YxwSocketHandler extends TextWebSocketHandler {

	private final static Logger logger = LoggerFactory.getLogger(YxwSocketHandler.class);

	// 考虑到可能会有同一个账号多地方登录(开发环境)
	private static final Map<String, List<WebSocketSession>> sessionMap = new ConcurrentHashMap<>(1024);

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String sessionId = (String) session.getAttributes().get("HTTP.SESSION.ID");
		logger.info("in session[{}] closed websocket connection.", new Object[] { sessionId });
		sessionMap.remove((String) session.getAttributes().get(BizConstant.OPENID));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String key = (String) session.getAttributes().get(BizConstant.OPENID);
		if (sessionMap.containsKey(key)) {
			sessionMap.get(key).add(session);
		} else {
			List<WebSocketSession> list = new ArrayList<>();
			list = Collections.synchronizedList(list);
			list.add(session);
			sessionMap.put(key, list);
		}

		// 测试
		YxwMessage message = new YxwMessage();
		message.setMessageType(MessageType.TEST);
		message.setContent("本消息为测试信息");
		message.setPushTime(System.currentTimeMillis());
		session.sendMessage(new TextMessage(JSON.toJSONString(message)));

		// 查询 - isRead 1未读，2已读
		MsgCenterService msgCenterService = SpringContextHolder.getBean(MsgCenterService.class);
		int msgCount = msgCenterService.findCountByIsRead(key, 1);
		if (msgCount > 0) {
			Map<String, Object> contentMap = new HashMap<>();
			contentMap.put("hasUnread", true);
			contentMap.put("unreadCount", msgCount);

			YxwMessage unreadMsg = new YxwMessage();
			unreadMsg.setMessageType(MessageType.MSGPUSH);
			unreadMsg.setContent(JSON.toJSONString(contentMap));
			unreadMsg.setPushTime(System.currentTimeMillis());
			session.sendMessage(new TextMessage(JSON.toJSONString(unreadMsg)));
		}
	}

	/**
	 * 如果服务器推送消息过来，只需要 通过spring 获取该handler对象，再调用sendMessage方法即可
	 * 如果以后消息多了的话，可以定义实体、分类
	 */
	public void sendMessage(String message, String openId) {
		if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(message)) {
			if (sessionMap.containsKey(openId)) {
				try {
					List<WebSocketSession> sessions = sessionMap.get(openId);
					for (WebSocketSession session : sessions) {
						session.sendMessage(new TextMessage(message));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 如果服务器推送消息过来，只需要 通过spring 获取该handler对象，再调用sendMessage方法即可
	 * 如果以后消息多了的话，可以定义实体、分类
	 */
	public void sendMessage(YxwMessage message) {
		if (StringUtils.isNotBlank(message.getToUser())) {
			String key = message.getToUser();
			if (sessionMap.containsKey(key)) {
				try {
					List<WebSocketSession> sessions = sessionMap.get(key);
					for (WebSocketSession session : sessions) {
						session.sendMessage(new TextMessage(JSON.toJSONString(message)));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 测试
	/*YxwSocketHandler handler = SpringContextHolder.getBean(YxwSocketHandler.class);
	Map<String, Object> contentMap = new HashMap<>();
	contentMap.put("hasUnread", true);
	contentMap.put("unreadCount", 1);
	
	YxwMessage unreadMsg = new YxwMessage();
	unreadMsg.setMessageType(MessageType.MSGPUSH);
	unreadMsg.setToUser(vo.getOpenId());
	unreadMsg.setFromUser("hospital-system");
	unreadMsg.setContent(JSON.toJSONString(contentMap));
	unreadMsg.setPushTime(System.currentTimeMillis());
	handler.sendMessage(unreadMsg);*/
}
