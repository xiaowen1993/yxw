/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.hospital.Extension;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.ExtensionDao;
import com.yxw.platform.hospital.service.ExtensionService;

/**
 * @Package: com.yxw.platform.hospital.service.impl
 * @ClassName: WhiteListServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "extensionService")
public class ExtensionServiceImpl extends BaseServiceImpl<Extension, String> implements ExtensionService {

	@Autowired
	private ExtensionDao extensionDao;

	@Override
	protected BaseDao<Extension, String> getDao() {
		return extensionDao;
	}

	@Override
	public List<Extension> findExtensionByhospitalIdAndAppCode(Map<String, Object> params) {
		return extensionDao.findExtensionByhospitalIdAndAppCode(params);
	}

	@Override
	public Extension findExtensionByhospitalIdAndAppCodeAndDistrict(Map<String, Object> params) {
		return extensionDao.findExtensionByhospitalIdAndAppCodeAndDistrict(params);
	}

	@Override
	public Extension findExtensionByAppIdAndSceneid(Map<String, Object> params) {
		return extensionDao.findExtensionByAppIdAndSceneid(params);
	}

}
