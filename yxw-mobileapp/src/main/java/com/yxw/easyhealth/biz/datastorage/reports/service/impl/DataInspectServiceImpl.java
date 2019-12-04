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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataInspectDao;
import com.yxw.easyhealth.biz.datastorage.reports.dao.DataInspectDetailDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspect;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;
import com.yxw.easyhealth.biz.datastorage.reports.service.DataInspectService;

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
@Service(value = "dataInspectService")
public class DataInspectServiceImpl implements DataInspectService {

	@Autowired
	private DataInspectDao dataInspectDao;

	@Autowired
	private DataInspectDetailDao dataInspectDetailDao;

	@Override
	public List<DataInspect> findByBranchHospitalCodeAndInspectId(DataInspect dataInspect) {
		return dataInspectDao.findByBranchHospitalCodeAndInspectId(dataInspect);
	}

	@Override
	public void add(DataInspect dataInspect) {
		dataInspectDao.add(dataInspect);
	}

	@Override
	public void batchInsert(List<DataInspect> dataInspects) {
		dataInspectDao.batchInsert(dataInspects);
	}

	@Override
	public void batchInsertAndInspectDetail(List<DataInspect> dataInspects, List<DataInspectDetail> dataInspectDetails) {
		if (dataInspects != null && dataInspects.size() > 0) {
			dataInspectDao.batchInsert(dataInspects);
		}
		if (dataInspectDetails != null && dataInspectDetails.size() > 0) {
			dataInspectDetailDao.batchInsert(dataInspectDetails);
		}
	}

	@Override
	public List<String> findByBranchHospitalCodeAndInspectIds(Map<String, Object> params) {
		return dataInspectDao.findByBranchHospitalCodeAndInspectIds(params);
	}

	@Override
	public List<DataInspect> findByIdNo(String idNo) {
		return dataInspectDao.findByIdNo(idNo);
	}

	@Override
	public List<DataInspectDetail> findByInspectId(String inspectId) {
		return dataInspectDetailDao.findByInspectId(inspectId);
	}
}
