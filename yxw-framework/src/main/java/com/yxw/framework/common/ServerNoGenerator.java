/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年7月10日
 * @version 1.0
 */
package com.yxw.framework.common;

import org.apache.commons.lang3.StringUtils;

import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.exception.SystemException;
import com.yxw.utils.NetworkUtil;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年7月10日  
 */
public class ServerNoGenerator {

	public static String getServerNoByIp() throws Exception {

		String serviceIp = NetworkUtil.getLocalIP();
		if (StringUtils.isNotBlank(serviceIp)) {

			String serviceNo = SystemConfig.getStringValue(serviceIp);
			if (StringUtils.isNotBlank(serviceNo)) {
				return serviceNo;
			} else {
				throw new SystemException("初始化数据：找不到IP[" + serviceIp + "]对应的服务器编号");
			}
		} else {
			throw new SystemException("初始化数据：获取本机IP地址失败");
		}
	}
}
