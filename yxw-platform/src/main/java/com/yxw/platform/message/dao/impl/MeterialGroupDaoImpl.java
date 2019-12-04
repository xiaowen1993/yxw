package com.yxw.platform.message.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.message.MeterialGroup;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.MeterialGroupDao;

@Repository
public class MeterialGroupDaoImpl extends BaseDaoImpl<MeterialGroup, String> implements MeterialGroupDao {

	private static Logger logger = LoggerFactory.getLogger(MeterialGroupDaoImpl.class);
	private final String LOGIC_DELETE = "logicDelete";

	@Override
	public void deleteLogic(MeterialGroup meterialGroup) {
		Assert.notNull(meterialGroup);
		try {
			sqlSession.update(getSqlName(LOGIC_DELETE), meterialGroup);
		} catch (Exception e) {
			logger.error(String.format("更新对象出错！语句：%s", getSqlName(LOGIC_DELETE)), e);
			throw new SystemException(String.format("更新对象出错！语句：%s", getSqlName(LOGIC_DELETE)), e);
		}
	}

}
