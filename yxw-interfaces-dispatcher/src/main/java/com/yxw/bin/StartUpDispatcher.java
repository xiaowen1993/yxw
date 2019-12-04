package com.yxw.bin;

import java.util.Enumeration;

import com.alibaba.dubbo.container.logback.LogbackContainer;
import com.alibaba.dubbo.container.spring.SpringContainer;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.config.SystemConstants;
import com.yxw.framework.utils.PropertiesUtils;

/**
 * 启动程序
 * 
 */

public class StartUpDispatcher {
	
	private static String FRAMEWORK_CONFIG = "config.properties";
	
	private static void loadSystemConfig() {
		PropertiesUtils propertiesUtils =
				new PropertiesUtils("classpath:" + SystemConstants.FILE_SEPARATOR + FRAMEWORK_CONFIG);
		Enumeration<Object> keys = propertiesUtils.getProperties().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			String value = propertiesUtils.getProperty(key);
			SystemConfig.put(key, value);
		}
	}
	
	public static void main(String[] args) {
		loadSystemConfig();
		SpringContainer.SPRING_CONFIG_PATH = SystemConfig.getStringValue("spring_config_path");
		LogbackContainer.LOGBACK_FILE_PATH = SystemConfig.getStringValue("logback_config_path");
		com.alibaba.dubbo.container.Main.main(args);
	}
}
