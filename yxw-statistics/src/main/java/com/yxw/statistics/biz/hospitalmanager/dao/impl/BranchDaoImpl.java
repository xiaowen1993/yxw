package com.yxw.statistics.biz.hospitalmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.statistics.biz.hospitalmanager.dao.BranchDao;
import com.yxw.statistics.biz.hospitalmanager.entity.Branch;

@Repository
public class BranchDaoImpl extends BaseDaoImpl<Branch, String> implements BranchDao {

	private static Logger logger = LoggerFactory.getLogger(BranchDao.class);
	
	private final static String SQLNAME_FIND_BY_NAME_AND_HOSPITAL_ID = "findByNameAndHospitalId";
	
	private final static String SQLNAME_FIND_BY_CODE_AND_HOSPITAL_ID = "findByCodeAndHospitalId";
	
	private final static String SQLNAME_FIND_ALL_BY_HOSPITAL_ID = "findAllByHospitalId";
	
	private final static String SQLNAME_DELETE_ALL_BY_HOSPITAL_ID = "deleteAllByHospitalId";
	
	private final static String SQLNAME_FIND_BY_NAME_OR_CODE = "findByNameOrCode";
	
	@Override
	public List<Branch> findByNameAndHospitalId(String name, String hospitalId) {
		try {
			Assert.notNull(name, "name不能为空");
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("name", name);
			queryMap.put("hospitalId", hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_NAME_AND_HOSPITAL_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过分院名称和医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_AND_HOSPITAL_ID)), e);
			throw new SystemException(String.format("通过分院名称和医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_AND_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<Branch> findByCodeAndHospitalId(String code, String hospitalId) {
		try {
			Assert.notNull(code, "code不能为空");
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("code", code);
			queryMap.put("hospitalId", hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_CODE_AND_HOSPITAL_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过分院Code和医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE_AND_HOSPITAL_ID)), e);
			throw new SystemException(String.format("通过分院Code和医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE_AND_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<Branch> findAllByHospitalId(String hospitalId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Map<String, String> queryMap = new HashMap<>();
			queryMap.put("hospitalId", hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_BY_HOSPITAL_ID), queryMap);
		} catch (Exception e) {
			logger.error(String.format("通过分医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("通过医院ID找分院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public void deleteAllByHospitalId(String hospitalId) {
		try {
			Assert.notNull(hospitalId, "hospitalId不能为空");
			sqlSession.delete(getSqlName(SQLNAME_DELETE_ALL_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("删除医院下的分院出错！语句：%s", getSqlName(SQLNAME_DELETE_ALL_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("删除医院下的分院出错！语句：%s", getSqlName(SQLNAME_DELETE_ALL_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public List<Branch> findByNameOrCode(Branch entity) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_NAME_OR_CODE), entity);
		} catch (Exception e) {
			logger.error(String.format("通过name|code找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_OR_CODE)), e);
			throw new SystemException(String.format("通过name|code找分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME_OR_CODE)), e);
		}
	}

}
