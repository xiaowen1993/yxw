/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.register.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.register.StopRegisterLog;

/**
 * @Package: com.yxw.mobileapp.biz.register.service.impl
 * @ClassName: StopRegisterLogService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface StopRegisterLogService {
	/**
	 * 插入数据
	 * @param stopRegisterLog
	 */
	public void add(StopRegisterLog stopRegisterLog);

	/**
	 * 批量插入数据
	 * @param stopRegisterLogs
	 */
	public void batchInsert(List<StopRegisterLog> stopRegisterLogs);
}