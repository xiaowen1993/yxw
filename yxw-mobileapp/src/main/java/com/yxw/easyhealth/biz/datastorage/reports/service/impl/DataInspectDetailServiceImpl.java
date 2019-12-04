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
package com.yxw.easyhealth.biz.datastorage.reports.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataInspectDetailDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;
import com.yxw.easyhealth.biz.datastorage.reports.service.DataInspectDetailService;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataInspectDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "dataInspectDetailServiceImpl")
public class DataInspectDetailServiceImpl implements DataInspectDetailService {

	@Autowired
	private DataInspectDetailDao inspectDetailDao;

	@Override
	public void add(DataInspectDetail inspectDetail) {
		inspectDetailDao.add(inspectDetail);
	}

	@Override
	public void batchInsert(List<DataInspectDetail> inspectDetails) {
		inspectDetailDao.batchInsert(inspectDetails);
	}

}
