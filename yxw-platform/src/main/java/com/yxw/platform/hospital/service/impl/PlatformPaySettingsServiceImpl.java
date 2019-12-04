/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.hospital.PlatformPaySettings;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.PlatformPaySettingsDao;
import com.yxw.platform.hospital.service.PlatformPaySettingsService;

/**
 * 后台接入平台管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "hospitalPaySettingsService")
public class PlatformPaySettingsServiceImpl extends BaseServiceImpl<PlatformPaySettings, String> implements PlatformPaySettingsService {

	@Autowired
	private PlatformPaySettingsDao platformPaySettingsDao;

	@Override
	protected BaseDao<PlatformPaySettings, String> getDao() {
		// TODO Auto-generated method stub
		return platformPaySettingsDao;
	}

}
