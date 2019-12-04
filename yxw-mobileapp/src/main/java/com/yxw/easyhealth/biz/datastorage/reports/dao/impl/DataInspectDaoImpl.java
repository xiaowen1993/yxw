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
package com.yxw.easyhealth.biz.datastorage.reports.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataInspectDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspect;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataInspectDaoImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class DataInspectDaoImpl extends BaseDaoImpl<DataInspect, String> implements DataInspectDao {
	private Logger logger = LoggerFactory.getLogger(DataInspectDaoImpl.class);
	private final static String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_ID = "findByBranchHospitalCodeAndInspectId";
	private final static String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_IDS = "findByBranchHospitalCodeAndInspectIds";
	private final static String SQLNAME_FIND_BY_ID_NO = "findByIdNo";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yxw.mobileapp.biz.datastorage.dao.impl.DataInspectDao#
	 * findByBranchHospitalCodeAndInspectId
	 * (com.yxw.mobileapp.biz.datastorage.entity.DataInspect)
	 */
	@Override
	public List<DataInspect> findByBranchHospitalCodeAndInspectId(DataInspect dataInspect) {
		List<DataInspect> dataInspects = null;
		try {
			Assert.notNull(dataInspect.getBranchHospitalCode());
			Assert.notNull(dataInspect.getInspectId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("branchHospitalCode", dataInspect.getBranchHospitalCode());
			paramMap.put("inspectId", dataInspect.getInspectId());
			dataInspects = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_ID), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据分院CODE和检验ID查询检查入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_ID)), e);
			throw new SystemException(
					String.format("根据分院CODE和检查ID检验检查入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_ID)), e);
		}
		return dataInspects;
	}

	@Override
	public List<String> findByBranchHospitalCodeAndInspectIds(Map<String, Object> params) {
		List<String> list = null;
		try {
			Assert.notNull(params.get("hospitalCode"));
			Assert.notNull(params.get("inspectIds"));
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_IDS), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院CODE，分院CODE批量查询是否存在记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_IDS)), e);
			throw new SystemException(String.format("根据医院CODE，分院CODE批量查询是否存在记录出错!语句：%s",
					getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_INSPECT_IDS)), e);
		}
		return list;
	}

	@Override
	public List<DataInspect> findByIdNo(String idNo) {
		List<DataInspect> list = null;
		try {
			Assert.notNull(idNo, "身份证号码不能为空！");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idNo", idNo);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_ID_NO), params);
		} catch (Exception e) {
			logger.error(String.format("通过身份照查检验数据出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ID_NO)), e);
			throw new SystemException(String.format("通过身份照查检验数据出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ID_NO)), e);
		}
		return list;
	}
}
