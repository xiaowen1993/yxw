package com.yxw.stats.dao.project.alipay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.stats.dao.project.HospitalDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.Hospital;

@Repository("alipayHospitalDao")
public class HospitalDaoImpl extends BaseDaoImpl<Hospital, Long> implements HospitalDao {

	private Logger logger = LoggerFactory.getLogger(HospitalDaoImpl.class);

	private final static String SQLNAME_FIND_ALL_HOSPITAL = "findAllHospital";

	@Override
	public List<Hospital> findAllHospital() {
		List<Hospital> list = null;
		try {

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_HOSPITAL));
		} catch (Exception e) {
			logger.error(String.format("查询项目平台所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL)), e);
			throw new SystemException(String.format("查询项目平台所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL)), e);
		}
		return list;
	}

	@Override
	public List<Hospital> findAllHospital(Long id) {
		List<Hospital> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_HOSPITAL), params);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL)), e);
			throw new SystemException(String.format("查询项目平台所有医院出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_HOSPITAL)), e);
		}
		return list;
	}

}