package com.yxw.platform.terminal.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.terminal.Terminal;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.terminal.dao.TerminalDao;

@Repository
public class TerminalDaoImpl extends BaseDaoImpl<Terminal, String> implements TerminalDao {
	private Logger logger = LoggerFactory.getLogger(TerminalDaoImpl.class);

	private final static String SQLNAME_FIND_BY_HOSPITAL_ID = "findByHospitalId";

	@Override
	public List<Terminal> findByHospitalId(String hospitalId) {
		try {
			Assert.notNull(hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID查询终端设备功能出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("根据医院ID查询终端设备功能出错！语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITAL_ID)), e);
		}
	}

}
