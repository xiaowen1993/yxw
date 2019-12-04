package com.yxw.mobileapp.common.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * 参照HttpSessionHandShakeInterceptor 实现HttpSession的拦截，但是不需要复制全部信息。
 * 功能概要：
 * @author Administrator
 * @date 2017年4月13日
 */
public class YxwSocketInterceptor implements HandshakeInterceptor {

	public static final String HTTP_SESSION_ID_ATTR_NAME = "HTTP.SESSION.ID";

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		HttpSession session = getSession(request);
		if (session != null) {
			// copy sessionId
			attributes.put(HTTP_SESSION_ID_ATTR_NAME, session.getId());
			// copy userInfo
			// attributes.put(EasyHealthConstant.SESSION_USER_ENTITY, session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY));
			// just copy openId
			attributes.put(BizConstant.OPENID, ( (EasyHealthUser) session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY) ).getId());
		}
		return true;
	}

	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(false);
		}
		return null;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}

}
