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
package com.yxw.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.config.SystemConfig;

/**
 * <p>
 * 系统初始化.
 * </p>
 * 
 * @author 申午武
 * 
 */
public class SystemListener implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(SystemListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("System Configuration load start......");
		SystemConfig.loadSystemConfig();
		loadBusinessesXml();
		logger.info("System Configuration load end......");
	}

	/**
	 * 加载业务配置文件
	 */
	public void loadBusinessesXml() {
		// List<File> files = FileUtils.searchFiles(this.getClass().getClassLoader().getResource("/rules").getPath(),
		// new String[] { "xml" }, false);
		// for (File file : files) {
		// Businesses businesses = RuleConvertUtil.xml2Businesses(file);
		// SystemConfig.businessesMap.put(businesses.getCode(), businesses);
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
}
