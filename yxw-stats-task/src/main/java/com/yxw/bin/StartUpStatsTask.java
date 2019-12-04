package com.yxw.bin;

import java.util.Enumeration;

import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.config.SystemConstants;
import com.yxw.framework.utils.PropertiesUtils;

/**
 * 启动程序
 * 
 */

public class StartUpStatsTask {

	private static String FRAMEWORK_CONFIG = "config.properties";

	private static void loadSystemConfig() {
		PropertiesUtils propertiesUtils = new PropertiesUtils("classpath:" + SystemConstants.FILE_SEPARATOR + FRAMEWORK_CONFIG);
		Enumeration<Object> keys = propertiesUtils.getProperties().keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			String value = propertiesUtils.getProperty(key);
			SystemConfig.put(key, value);
		}
	}

	public static void main(String[] args) {
		loadSystemConfig();
		com.yxw.boot.container.Main.main(args);
	}
}
