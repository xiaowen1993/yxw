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
package com.yxw.platform.rule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.rule.SystemSetting;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.rule.dao.SystemSettingDao;
import com.yxw.platform.rule.service.SystemSettingService;

/**
 * @Package: com.yxw.platform.rule.service.impl
 * @ClassName: RuleEditServiceImpl
 * @Statement: <p>
 *             编辑规则 Service 实现类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "systemSettingService")
public class SystemSettingServiceImpl extends BaseServiceImpl<SystemSetting, String> implements SystemSettingService {

	private Logger logger = LoggerFactory.getLogger(SystemSettingServiceImpl.class);

	@Autowired
	private SystemSettingDao systemSettingDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<SystemSetting, String> getDao() {
		return systemSettingDao;
	}

}
