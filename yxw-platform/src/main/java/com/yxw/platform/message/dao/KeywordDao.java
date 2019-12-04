package com.yxw.platform.message.dao;

import java.util.List;

import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.framework.mvc.dao.BaseDao;

public interface KeywordDao extends BaseDao<Keyword, String> {
	/**
	 * 获取某规则的关键字
	 * */
	public List<Keyword> findByRuleId(String ruleId);
}
