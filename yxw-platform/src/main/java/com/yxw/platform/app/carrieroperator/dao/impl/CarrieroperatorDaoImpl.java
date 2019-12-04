package com.yxw.platform.app.carrieroperator.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.app.carrieroperator.dao.CarrieroperatorDao;

@Repository
public class CarrieroperatorDaoImpl extends BaseDaoImpl<Carrieroperator, Long> implements CarrieroperatorDao {

	private static Logger logger = LoggerFactory.getLogger(CarrieroperatorDaoImpl.class);

	private final static String SQLNAME_FIND_SORTING = "findSorting";
	private final static String SQLNAME_FIND_BY_ID = "findById";
	private final static String SQLNAME_FIND_BY_OPERATION_POSITION = "findByOperationPosition";

	@Override
	public List<String> findSorting(Map<String, Object> params) {
		try {
			Assert.notNull(params.get("operationPosition"), "operationPosition不能为空");
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_SORTING), params);
		} catch (Exception e) {
			logger.error(String.format("根据运营消息排序出错！语句：%s", getSqlName(SQLNAME_FIND_SORTING)), e);
			throw new SystemException(String.format("根据运营消息排序出错！语句：%s", getSqlName(SQLNAME_FIND_SORTING)), e);
		}
	}

	@Override
	public Carrieroperator findCarrieroperatorById(Map<String, Object> params) {
		try {
			Assert.notNull(params.get("id"), "id 不能为空");
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID查找出错！语句：%s", getSqlName(SQLNAME_FIND_SORTING)), e);
			throw new SystemException(String.format("根据ID查找出错！语句：%s", getSqlName(SQLNAME_FIND_SORTING)), e);
		}
	}

	/**
	 * 根据运营位置查找
	 */
	@Override
	public List<Carrieroperator> findByOperationPosition(String operationPosition) {
		try {
			Assert.notNull(operationPosition);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_OPERATION_POSITION), operationPosition);
		} catch (Exception e) {
			logger.error(String.format("根据运营位置查找出错！语句：%s", getSqlName(SQLNAME_FIND_BY_OPERATION_POSITION)), e);
			throw new SystemException(String.format("根据运营位置查找出错！语句：%s", getSqlName(SQLNAME_FIND_BY_OPERATION_POSITION)), e);
		}
	}

}
