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
package com.yxw.framework.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.spring.ext.config.YxwPropertyPlaceholderConfigurer;

/**
 * <p>
 * 系统参数
 * </p>
 * 
 * @author 申午武
 * 
 * @modify by yuce 分为rc版和dev版的不同加载
 * 
 */
public class SystemConfig {
	private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	/**
	 * 系统参数集合
	 */
	private static Map<String, String> systemConfigMap = new HashMap<String, String>();

	/**
	 * 业务规则集合
	 */

	private SystemConfig() {

	}

	/**
	 * 加载系统配置文件 modify by yuce 2015-8-1 改为使用spring的文件解析
	 */
	public static void loadSystemConfig() {
		YxwPropertyPlaceholderConfigurer propsConfig = SpringContextHolder.getBean(YxwPropertyPlaceholderConfigurer.class);

		try {
			Properties props = propsConfig.mergeProperties();
			systemConfigMap.putAll(propsConfig.convertPropsToMap(props));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("system params:{}", systemConfigMap);
		}
		/*
		 * StringBuffer sb = new StringBuffer(); //framework.properties sb.append(SystemConstants.FILE_CALSS_PATH);
		 * sb.append(SystemConstants.YXW_FRAMEWORK_CONFIG_START_PREFIX); if
		 * (SystemConstants.VERSION_TYPE_DEV.equalsIgnoreCase(type)) { sb.append(SystemConstants.VERSION_TYPE_DEV); }
		 * else if (SystemConstants.VERSION_TYPE_RC.equalsIgnoreCase(type)) {
		 * sb.append(SystemConstants.VERSION_TYPE_RC); } else { sb.append(SystemConstants.VERSION_TYPE_DEF); }
		 * sb.append(SystemConstants.YXW_FRAMEWORK_CONFIG_FILE_TYPE);
		 * 
		 * sb.append(SystemConstants.FILE_SPLIT_CHAR);
		 * 
		 * //service.properties sb.append(SystemConstants.FILE_CALSS_PATH); sb.append(SystemConstants.SERVICE_CONFIG);
		 * 
		 * PropertiesUtils propertiesUtils = new PropertiesUtils(sb.toString()); Enumeration<Object> keys =
		 * propertiesUtils.getProperties().keys(); while (keys.hasMoreElements()) { String key =
		 * keys.nextElement().toString(); String value = propertiesUtils.getProperty(key); SystemConfig.put(key, value);
		 * }
		 */
	}

	/**
	 * 添加参数
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, String value) {
		systemConfigMap.put(key, value);
	}

	/**
	 * 根据key值返回value
	 * 
	 * @param key
	 * @return
	 */
	private static String getValue(String key) {
		return systemConfigMap.get(key);
	}

	/**
	 * 根据key值返回String类型的value.
	 */
	public static String getStringValue(String key) {
		return getValue(key);
	}

	/**
	 * 根据key值返回Integer类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public static String getStringValue(String key, String defaultValue) {
		String value = getValue(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 根据key值返回Integer类型的value.如果都為Null或内容错误则抛出异常.
	 */
	public static Integer getIntegerValue(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}

	/**
	 * 根据key值返回Integer类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}

	/**
	 * 根据key值返回Double类型的value.如果都為Null或内容错误则抛出异常.
	 */
	public static Double getDoubleValue(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/**
	 * 根据key值返回Double类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public static Double getDoubleValue(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	/**
	 * 根据key值返回Boolean类型的value.如果都為Null抛出异常,如果内容不是true/false则返回false.
	 */
	public static Boolean getBooleanValue(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 根据key值返回Boolean类型的value.如果都為Null則返回Default值,如果内容不为true/false则返回false.
	 */
	public static Boolean getBooleanValue(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}
}
