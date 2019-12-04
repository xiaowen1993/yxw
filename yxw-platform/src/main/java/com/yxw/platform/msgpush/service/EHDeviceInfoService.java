/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.service;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.commons.entity.platform.msgpush.EHDeviceInfo;

/**
 * @Package: com.yxw.platform.msgpush.service
 * @ClassName: EHDeviceInfoService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface EHDeviceInfoService extends BaseService<EHDeviceInfo, String> {
	/**
	 * 根据用户ID查找设备
	 * @param params
	 * @return
	 */
	public List<EHDeviceInfo> findByUserId(Map<String, Object> params);

	/**
	 * 根据设备ID查找设备
	 * @param params
	 * @return
	 */
	public EHDeviceInfo findByDeviceId(String deviceId, String deviceType);

	/**
	 * 保存设备信息
	 * @param ehDeviceInfo
	 */
	public boolean saveDeviceInfo(EHDeviceInfo ehDeviceInfo);
}
