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

/**
 * <p>
 * 框架常量
 * </P>
 * 
 * @author 申午武
 * 
 */
public class SystemConstants {
	/**
	 * 文件分隔符
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	/**
	 * 版本
	 */
	public static final String VERSION_TYPE_RC = "rc";
	public static final String VERSION_TYPE_DEV = "dev";
	public static final String VERSION_TYPE_DEF = "dev";

	public static final String FILE_CALSS_PATH = "classpath:/";

	public static final String FILE_SPLIT_CHAR = ",";

	public static final String YXW_FRAMEWORK_CONFIG_START_PREFIX = "framework_";
	public static final String YXW_FRAMEWORK_CONFIG_FILE_TYPE = ".properties";

	public static final String YXW_FRAMEWORK_CONFIG_DEF = "framework_dev.properties";
	/**
	 * 服务配置文件
	 */
	public static final String SERVICE_CONFIG = "service.properties";
	/**
	 * 接口配置文件
	 */
	public static final String INTERFACE_CONFIG = "provider_services/interface.properties";
}
