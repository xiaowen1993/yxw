package com.yxw.boot.container.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxw.boot.container.Container;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.exception.SystemException;

public class SpringContainer implements Container {
	private static final Logger logger = LoggerFactory.getLogger(SpringContainer.class);

	public static String SPRING_CONFIG_PATH = SystemConfig.getStringValue("spring_config_path");

	static ClassPathXmlApplicationContext context;

	public static ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void start() {
		if (StringUtils.isBlank(SPRING_CONFIG_PATH)) {
			throw new SystemException("Spring config file is not found.");
		}
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_PATH.split("[,\\s]+"));
		context.start();
	}

	public void stop() {
		try {
			if (context != null) {
				context.stop();
				context.close();
				context = null;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

}
