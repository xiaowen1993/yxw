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
package com.yxw.platform.hospital.dao;

import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.hospital.dao
 * @ClassName: WhiteListDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface WhiteListDao extends BaseDao<WhiteList, String> {
	/**
	 * 根据医院id查找记录
	 * @param hospitalId
	 * @return
	 */
	public WhiteList findByAppIdAndCode(String appId, String appCode);

	public WhiteList findHospitalId(String hospitalId, String appCode);
}
