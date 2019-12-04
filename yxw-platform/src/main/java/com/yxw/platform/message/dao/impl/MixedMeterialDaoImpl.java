package com.yxw.platform.message.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.MixedMeterialDao;

@Repository
public class MixedMeterialDaoImpl extends BaseDaoImpl<MixedMeterial, String> implements MixedMeterialDao {

	private final static String FIND_ENTITY_BY_IDS = "findEntityByIds";
	private final static String FIND_MIXED_METERIAL_BY_IDS = "findMixedMeterialByIds";
	private final static String GET_METERIAL_BY_REPLYID = "getMeterialByReplyId";
	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	/**
	 * 根据id数组查询图文
	 * */
	@Override
	public List<MixedMeterial> findEntityByIds(String[] ids) {
		Assert.notNull(ids);
		try {
			return sqlSession.selectList(getSqlName(FIND_ENTITY_BY_IDS), ids);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_ENTITY_BY_IDS)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_ENTITY_BY_IDS)), e);
		}
	}

	/**
	 * 根据回复ID获取相应素材
	 * */
	@Override
	public MixedMeterial getMeterialByReplyId(String replyId) {
		try {
			Assert.notNull(replyId);
			return sqlSession.selectOne(getSqlName(GET_METERIAL_BY_REPLYID), replyId);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_METERIAL_BY_REPLYID)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_METERIAL_BY_REPLYID)), e);
		}
	}

	/**
	 * 根据id数组查询图文
	 * */
	@Override
	public List<MixedMeterial> findMixedMeterialsByIds(String[] ids) {
		Assert.notNull(ids);
		try {
			return sqlSession.selectList(getSqlName(FIND_MIXED_METERIAL_BY_IDS), ids);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_MIXED_METERIAL_BY_IDS)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_MIXED_METERIAL_BY_IDS)), e);
		}
	}
}
