package com.yxw.easyhealth.biz.datastorage.clinic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataMzFeeDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFee;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * 门诊待缴费记录Dao实现
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataMZFeeDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class DataMzFeeDaoImpl extends BaseDaoImpl<DataMzFee, String> implements DataMzFeeDao {

	private Logger logger = LoggerFactory.getLogger(DataMzFeeDaoImpl.class);

	private final String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID = "findByBranchHospitalCodeAndMzFeeId";

	private final String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_IDS = "findByBranchHospitalCodeAndMzFeeIds";

	@Override
	public List<DataMzFee> findByBranchHospitalCodeAndMzFeeId(DataMzFee dataMZFee) {
		List<DataMzFee> list = null;
		try {
			Assert.notNull(dataMZFee.getBranchHospitalCode());
			Assert.notNull(dataMZFee.getMzFeeId());
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("branchHospitalCode", dataMZFee.getBranchHospitalCode());
			paramsMap.put("mzFeeId", dataMZFee.getMzFeeId());
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID), paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("根据分院CODE和检查ID查询待缴费记录入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID)), e);
			throw new SystemException(
					String.format("根据分院CODE和检查ID查询待缴费记录入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID)), e);
		}
		return list;
	}

	@Override
	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params) {
		List<String> list = null;
		try {
			Assert.notNull(params.get("hospitalCode"));
			// Assert.notNull(params.get("branchHospitalCode"));
			Assert.notNull(params.get("mzFeeIds"));
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_IDS), params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("查询重复的待缴费记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_IDS)), e);
			throw new SystemException(String.format("查询重复的待缴费记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_IDS)), e);
		}
		return list;
	}

}
