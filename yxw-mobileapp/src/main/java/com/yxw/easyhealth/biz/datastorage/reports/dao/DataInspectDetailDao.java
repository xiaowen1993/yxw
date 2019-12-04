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
package com.yxw.easyhealth.biz.datastorage.reports.dao;

import java.util.List;

import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataInspectDetailDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataInspectDetailDao extends BaseDao<DataInspectDetail, String> {
	public abstract List<DataInspectDetail> findByInspectId(String inspectId);
}