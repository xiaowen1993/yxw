package com.yxw.platform.message.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.dao.BaseDao;

public interface RuleDao extends BaseDao<Rule, String> {
	public List<Rule> findByHospId(Map<String, Object> params);

	/**
	 * 删除规则下绑定的回复
	 * */
	public void deleteRuleReply(Map<String, Object> params);

	/**
	 * 获取完全匹配 的规则
	 * */
	public List<Rule> getRuleByKeyword(Map<String, Object> params);

	/**
	 * 获取不完全匹配 的规则
	 * */
	public List<Rule> getRuleByHalfKeyword(Map<String, Object> params);
}
