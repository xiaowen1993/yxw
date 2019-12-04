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
package com.yxw.easyhealth.datas.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.yxw.easyhealth.datas.manager.InitDataManager;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;

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

		/** 初始化绑卡信息缓存 ***/
		initDataManager.initMedicalCardCache();

		/**** 加载挂号订单到缓存 ***/
		initDataManager.initRegisterRecordCache();
		/**** 加载挂号的异常订单信息到缓存 ***/
		initDataManager.initRegisterExceptionCache();
		/**** 加载门诊的异常订单信息到缓存 ***/
		initDataManager.initClinicExceptionCache();

		initDataManager.finishedInit();
	}
}
