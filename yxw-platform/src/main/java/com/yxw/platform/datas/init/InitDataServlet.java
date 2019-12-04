/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-6</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.datas.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.platform.datas.manager.InitDataManager;

/**
 * @Package: com.yxw.platform.datas.init
 * @ClassName: InitDataServlet
 * @Statement:
 *             <p>
 *             数据初始化
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InitDataServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5343113725802355271L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		SystemConfig.loadSystemConfig();

		InitDataManager initDataManager = SpringContextHolder.getBean(InitDataManager.class);

		/** 初始化医院信息缓存 ***/
		initDataManager.initHospitalCache();

		initDataManager.initWhiteListCache();

		/**** 初始化医院规则配置信息 ***/
		initDataManager.initRuleConfigCache();

		/**** 初始化消息推送信息缓存 ***/
		initDataManager.initMsgTempCache();

		/**** 初始化用户角色信息缓存 ***/
		initDataManager.initUserRoleCache();

		/**** 初始化角色资源缓存  ****/
		initDataManager.initRoleResourceCache();

		/***** 初始化健康易中的医院与功能的关系 *****/
		initDataManager.initHospitalAndOptionCache();

		/***** 初始化城市信息 *****/
		initDataManager.initAreasCache();

		/***** 初始化APP定位可选城市信息 *****/
		//		initDataManager.initAppLocationsCache();

		/***** 初始化APP 功能*****/
		initDataManager.initAppOptionalsCache();

		/***** 初始化APP运营信息 *****/
		initDataManager.initAppCarrieroperatorsCache();

		/***** 初始化版本配色信息 *****/
		initDataManager.initAppColorCache();
		/***** 初始化结束符号 *****/
		initDataManager.finishedInit();

	}
}
