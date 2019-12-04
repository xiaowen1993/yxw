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
package com.yxw.platform.hospital.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.hospital.Extension;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.hospital.dao.impl
 * @ClassName: ExtensionDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface ExtensionDao extends BaseDao<Extension, String> {
	/**
	 * 根据医院Id的及appCode查询推广二维码信息
	 * @param params
	 * @return
	 */
	public List<Extension> findExtensionByhospitalIdAndAppCode(Map<String, Object> params);

	/**
	 * 根据医院Id的及appCode以及标识查询推广二维码信息标识
	 * @param params
	 * @return
	 */
	public Extension findExtensionByhospitalIdAndAppCodeAndDistrict(Map<String, Object> params);

	/**
	 * 根据appId和Sceneid查询推广二维码来源
	 * @param params
	 * @return
	 */
	public Extension findExtensionByAppIdAndSceneid(Map<String, Object> params);

}