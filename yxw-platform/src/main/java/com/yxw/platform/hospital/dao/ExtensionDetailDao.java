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

import com.yxw.commons.entity.platform.hospital.ExtensionDetail;
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
public interface ExtensionDetailDao extends BaseDao<ExtensionDetail, String> {
	/**
	 * 根据推广二维码Id查询其关注信息
	 * @param params
	 * @return
	 */
	public List<ExtensionDetail> findExtensionDetailByExtensionId(Map<String, Object> params);

	/**
	 * 根据推广二维码Id及openId查询其关注信息
	 * @param params
	 * @return
	 */
	public ExtensionDetail findExtensionDetailByExtensionIdAndOpenId(Map<String, Object> params);

}