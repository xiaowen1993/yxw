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

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataExamineDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataExamine;
import com.yxw.easyhealth.biz.datastorage.reports.service.DataExamineService;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.service.impl
 * @ClassName: DataExamineServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "dataExamine")
public class DataExamineServiceImpl implements DataExamineService {

	@Autowired
	private DataExamineDao dataExamineDao;

	@Override
	public List<DataExamine> findByBranchHospitalCodeAndcheckId(DataExamine dataExamine) {
		return dataExamineDao.findByBranchHospitalCodeAndcheckId(dataExamine);
	}

	@Override
	public void add(DataExamine dataExamine) {
		dataExamineDao.add(dataExamine);
	}

	@Override
	public void batchInsert(List<DataExamine> dataExamines) {
		dataExamineDao.batchInsert(dataExamines);
	}

	@Override
	public List<String> findByBranchHospitalCodeAndcheckIds(Map<String, Object> params) {
		return dataExamineDao.findByBranchHospitalCodeAndcheckIds(params);
	}

	@Override
	public List<DataExamine> findByIdNo(String idNo) {
		return dataExamineDao.findByIdNo(idNo);
	}

	@Override
	public DataExamine findByExamineId(String examineId) {
		return dataExamineDao.findByExamineId(examineId);
	}

	@Override
	public List<ReportsParamsVo> findReportsByIdNo(String idNo) {
		return dataExamineDao.findReportsByIdNo(idNo);
	}
}
