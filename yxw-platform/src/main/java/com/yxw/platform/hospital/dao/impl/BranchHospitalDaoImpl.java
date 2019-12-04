/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.BranchHospitalDao;

/**
 * 后台分院管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class BranchHospitalDaoImpl extends BaseDaoImpl<BranchHospital, String> implements BranchHospitalDao {

	private static Logger logger = LoggerFactory.getLogger(BranchHospitalDaoImpl.class);

	private final static String SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE = "findBranchHospitalByCode";
	private final static String SQLNAME_SELECT_BRANCH_HOSPITALS_BY_HOSPITAL_ID = "selectBranchHospitalsByHospitalId";
	private final static String SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE_FOR_HOSPITAL_ID = "findHospitalByCodeForHospitalId";
	private final static String SQLNAME_FIND_BRANCH_HOSPITAL_BY_NAME_FOR_HOSPITAL_ID = "findHospitalByNameForHospitalId";
	private final static String SQLNAME_FIND_BRANCH_HOSPITAL_BY_INTERFACE_ID = "findHospitalByInterfaceId";
	private final static String SQLNAME_FIND_BY_HOSPITAL_ID = "findByHospitalId";
	private final static String SQLNAME_FIND_BRANCH_HOSPITAL_BY_HOSPITAL_CODE = "findBranchHospitalByHospitalCode";

	@Override
	public BranchHospital findBranchHospitalByCode(String code) {
		Assert.notNull(code);
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE), code);
		} catch (Exception e) {
			logger.error(String.format("根据code查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE)), e);
			throw new SystemException(String.format("根据code查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE)), e);
		}
	}

	@Override
	public List<BranchHospital> selectBranchHospitalsByHospitalId(String hospitalId) {
		Assert.notNull(hospitalId);
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_SELECT_BRANCH_HOSPITALS_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询分院列表出错！语句：%s", getSqlName(SQLNAME_SELECT_BRANCH_HOSPITALS_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询分院列表出错！语句：%s", getSqlName(SQLNAME_SELECT_BRANCH_HOSPITALS_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public BranchHospital findHospitalByCodeForHospitalId(BranchHospital branchHospital) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE_FOR_HOSPITAL_ID), branchHospital);
		} catch (Exception e) {
			logger.error(String.format("根据code及医院Id查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE_FOR_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据code及医院Id查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_CODE_FOR_HOSPITAL_ID)), e);
		}
	}

	@Override
	public BranchHospital findHospitalByNameForHospitalId(BranchHospital branchHospital) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_NAME_FOR_HOSPITAL_ID), branchHospital);
		} catch (Exception e) {
			logger.error(String.format("根据name及医院Id查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_NAME_FOR_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据name及医院Id查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_NAME_FOR_HOSPITAL_ID)), e);
		}
	}

	@Override
	public BranchHospital findHospitalByInterfaceId(BranchHospital branchHospital) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_INTERFACE_ID), branchHospital);
		} catch (Exception e) {
			logger.error(String.format("根据interfaceId查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_INTERFACE_ID)), e);
			throw new SystemException(String.format("根据interfaceId查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_INTERFACE_ID)), e);
		}
	}

	@Override
	public List<BranchHospital> findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据hospitalId查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据hospitalId查询分院出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
		}
	}

	@Override
	public BranchHospital findBranchHospitalByHospitalCode(String code) {
		Assert.notNull(code);
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_HOSPITAL_CODE), code);
		} catch (Exception e) {
			logger.error(String.format("根据code查询分院  保证医院及分院code唯一的前置条件查询出错！语句：%s", getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_HOSPITAL_CODE)), e);
			throw new SystemException(String.format("根据code查询分院  保证医院及分院code唯一的前置条件查询出错！语句：%s",
					getSqlName(SQLNAME_FIND_BRANCH_HOSPITAL_BY_HOSPITAL_CODE)), e);
		}
	}
}
