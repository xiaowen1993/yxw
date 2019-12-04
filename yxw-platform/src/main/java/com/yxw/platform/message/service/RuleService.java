package com.yxw.platform.message.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.service.BaseService;

public interface RuleService extends BaseService<Rule, String> {
	public List<Rule> findByHospId(Map<String, Object> params);

	/**
	 * 删除规则下绑定的回复
	 * */
	public void deleteRuleReply(Map<String, Object> params);
}
