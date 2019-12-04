package com.yxw.platform.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.RuleDao;

@Repository
public class RuleDaoImpl extends BaseDaoImpl<Rule, String> implements RuleDao {

	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	private final static String FIND_BY_HOSPID = "findByHospId";
	private final static String DELETE_RULE_REPLY = "deleteRuleReply";
	private final static String FIND_RULE_BY_KEYWORD = "findRuleByKeyword";
	private final static String FIND_RULE_BY_HALF_KEYWORD = "findRuleByHalfKeyword";

	@Override
	public List<Rule> findByHospId(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(FIND_BY_HOSPID), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_BY_HOSPID)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_BY_HOSPID)), e);
		}
	}

	/**
	 * 删除规则下绑定的回复
	 * */
	public void deleteRuleReply(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			sqlSession.delete(getSqlName(DELETE_RULE_REPLY), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(DELETE_RULE_REPLY)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(DELETE_RULE_REPLY)), e);
		}
	}

	/**
	 * 获取完全匹配 的规则
	 * */
	@Override
	public List<Rule> getRuleByKeyword(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(FIND_RULE_BY_KEYWORD), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_RULE_BY_KEYWORD)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_RULE_BY_KEYWORD)), e);
		}
	}

	/**
	 * 获取不完全匹配 的规则
	 * */
	@Override
	public List<Rule> getRuleByHalfKeyword(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(FIND_RULE_BY_HALF_KEYWORD), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_RULE_BY_HALF_KEYWORD)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_RULE_BY_HALF_KEYWORD)), e);
		}
	}
}
