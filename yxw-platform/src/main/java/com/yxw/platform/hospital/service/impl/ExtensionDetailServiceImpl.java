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

import com.yxw.commons.entity.platform.hospital.ExtensionDetail;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.ExtensionDetailDao;
import com.yxw.platform.hospital.service.ExtensionDetailService;

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
@Service(value = "extensionDetailService")
public class ExtensionDetailServiceImpl extends BaseServiceImpl<ExtensionDetail, String> implements ExtensionDetailService {

	@Autowired
	private ExtensionDetailDao extensionDetailDao;

	@Override
	protected BaseDao<ExtensionDetail, String> getDao() {
		return extensionDetailDao;
	}

	@Override
	public List<ExtensionDetail> findExtensionDetailByExtensionId(Map<String, Object> params) {
		return extensionDetailDao.findExtensionDetailByExtensionId(params);
	}

	@Override
	public ExtensionDetail findExtensionDetailByExtensionIdAndOpenId(Map<String, Object> params) {
		return extensionDetailDao.findExtensionDetailByExtensionIdAndOpenId(params);
	}

}
