package com.yxw.platform.message.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.KeywordDao;

@Repository
public class KeywordDaoImpl extends BaseDaoImpl<Keyword, String> implements KeywordDao {
	private static Logger logger = LoggerFactory.getLogger(KeywordDaoImpl.class);
	private final static String FIND_LIST_BY_RULEID = "findListByRuleId";

	/**
	 * 获取某规则的关键字
	 * */
	@Override
	public List<Keyword> findByRuleId(String ruleId) {
		Assert.notNull(ruleId);
		try {
			return sqlSession.selectList(getSqlName(FIND_LIST_BY_RULEID), ruleId);
		} catch (Exception e) {
			logger.error(String.format("查询对象出错！语句：%s", getSqlName(FIND_LIST_BY_RULEID)), e);
			throw new SystemException(String.format("查询对象出错！语句：%s", getSqlName(FIND_LIST_BY_RULEID)), e);
		}
	}

}
