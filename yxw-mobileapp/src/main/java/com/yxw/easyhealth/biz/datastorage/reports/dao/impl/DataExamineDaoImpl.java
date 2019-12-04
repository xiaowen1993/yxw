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

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataExamineDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataExamine;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataExamineDao
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0R
 */
@Repository
public class DataExamineDaoImpl extends BaseDaoImpl<DataExamine, String> implements DataExamineDao {
	private Logger logger = LoggerFactory.getLogger(DataExamineDaoImpl.class);

	private final static String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_ID = "findByBranchHospitalCodeAndcheckId";

	private final static String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_IDS = "findByBranchHospitalCodeAndcheckIds";

	private final static String SQLNAME_FIND_BY_ID_NO = "findByIdNo";

	private final static String SQLNAME_FIND_BY_EXAMINE_ID = "findByExamineId";

	private final static String SQLNAME_FIND_REPORTS_BY_ID_NO = "findReportsByIdNo";

	@Override
	public List<DataExamine> findByBranchHospitalCodeAndcheckId(DataExamine dataExamine) {
		List<DataExamine> dataExamines = null;
		try {
			Assert.notNull(dataExamine.getBranchHospitalCode());
			Assert.notNull(dataExamine.getCheckId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("branchHospitalCode", dataExamine.getBranchHospitalCode());
			paramMap.put("checkId", dataExamine.getCheckId());
			dataExamines = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_ID), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据分院CODE和检查ID查询检查入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_ID)), e);
			throw new SystemException(String.format("根据分院CODE和检查ID查询检查入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_ID)),
					e);
		}
		return dataExamines;
	}

	@Override
	public List<String> findByBranchHospitalCodeAndcheckIds(Map<String, Object> params) {
		List<String> list = null;
		try {
			Assert.notNull(params.get("hospitalCode"));
			// Assert.notNull(params.get("branchHospitalCode"));
			Assert.notNull(params.get("checkIds"));
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_IDS), params);
		} catch (Exception e) {
			logger.error(String.format("检测重复存在的检查Id出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_IDS)), e);
			throw new SystemException(String.format("检测重复存在的检查Id出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_CHECK_IDS)), e);
		}
		return list;
	}

	@Override
	public List<DataExamine> findByIdNo(String idNo) {
		List<DataExamine> dataExamines = null;
		try {
			Assert.notNull(idNo, "身份证号码不能为空！");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("idNo", idNo);
			dataExamines = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_ID_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过身份证号码查询检查报告出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ID_NO)), e);
			throw new SystemException(String.format("通过身份证号码查询检查报告出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ID_NO)), e);
		}
		return dataExamines;
	}

	@Override
	public DataExamine findByExamineId(String examineId) {
		DataExamine data = null;
		try {
			Assert.notNull(examineId, "检查Id不能为空！");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("examineId", examineId);
			data = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_EXAMINE_ID), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过ID查询检查报告出错!语句：%s", getSqlName(SQLNAME_FIND_BY_EXAMINE_ID)), e);
			throw new SystemException(String.format("通过ID查询检查报告出错!语句：%s", getSqlName(SQLNAME_FIND_BY_EXAMINE_ID)), e);
		}
		return data;
	}

	@Override
	public List<ReportsParamsVo> findReportsByIdNo(String idNo) {
		List<ReportsParamsVo> repoorts = null;
		try {
			Assert.notNull(idNo, "身份证号码不能为空！");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("idNo", idNo);
			repoorts = sqlSession.selectList(getSqlName(SQLNAME_FIND_REPORTS_BY_ID_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过身份证号码查询所有报告出错!语句：%s", getSqlName(SQLNAME_FIND_REPORTS_BY_ID_NO)), e);
			throw new SystemException(String.format("通过身份证号码查询所有报告出错!语句：%s", getSqlName(SQLNAME_FIND_REPORTS_BY_ID_NO)), e);
		}
		return repoorts;
	}
}
