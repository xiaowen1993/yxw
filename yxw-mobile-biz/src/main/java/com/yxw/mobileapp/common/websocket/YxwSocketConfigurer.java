package com.yxw.mobileapp.common.websocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.framework.config.SystemConfig;

@Configuration
@EnableWebSocket
@EnableWebMvc
public class YxwSocketConfigurer implements WebSocketConfigurer {

	private final static Logger logger = LoggerFactory.getLogger(YxwSocketConfigurer.class);

	private final static String ENABLE_SOCKET_KEY = "enableWebSocket";

	private final static String COMMON_SOCKET_KEY = "commonWebSocketPath";

	private final static String SOCKJS_SOCKET_KEY = "sockJSWebSocketPath";

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// allowed-origins 可以设定允许连接域
		SystemConfig.loadSystemConfig();
		// 读配置，看是否注册websocket服务
		System.out.println(SystemConfig.getStringValue(BizConstant.TRADE_REMOTE_URL_PROPERTIES_KEY, ""));
		String enable = SystemConfig.getStringValue(ENABLE_SOCKET_KEY, "0");
		System.out.println("load enableWebSocket value = " + enable);
		logger.debug("load enableWebSocket value = " + enable);
		if (enable.equals("1")) {
			logger.info("begin to regist websocket to the server...");

			// 普通websocket, 浏览器支持 --> IE,FireFox
			String commonPath = SystemConfig.getStringValue(COMMON_SOCKET_KEY, "");

			System.out.println("common handler hashCode：" + commonPath);

			if (StringUtils.isNotBlank(commonPath)) {
				WebSocketHandler handler = getSocketHandler();
				logger.debug("common handler hashCode：" + handler.hashCode());
				registry.addHandler(getSocketHandler(), commonPath).addInterceptors(getSocketInterceptor());
				System.out.println("common handler hashCode：" + handler.hashCode());
			} else {
				logger.debug(COMMON_SOCKET_KEY + " was not defined int the properties file.");
			}

			// 浏览器不支持的情况下，通过sockjs，使用普通http的方式，进行协议升级
			String sockjsPath = SystemConfig.getStringValue(SOCKJS_SOCKET_KEY, "");
			if (StringUtils.isNotBlank(sockjsPath)) {
				WebSocketHandler handler = getSocketHandler();
				logger.debug("sockjs handler hashCode：" + handler.hashCode());
				registry.addHandler(handler, sockjsPath).addInterceptors(getSocketInterceptor()).withSockJS();
			} else {
				logger.debug(SOCKJS_SOCKET_KEY + " was not defined int the properties file.");
			}

			logger.debug("finished regist...");
			// registry.addHandler(new YxwSocketHandler(), paths)
		} else {
			logger.debug("do not regist websocket...");
		}
	}

	@Bean
	public YxwSocketHandler getSocketHandler() {
		return new YxwSocketHandler();
	}

	@Bean
	public YxwSocketInterceptor getSocketInterceptor() {
		return new YxwSocketInterceptor();
	}

}
