package com.yxw.platform.message.service;

import java.util.List;

import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.framework.mvc.service.BaseService;

public interface KeywordService extends BaseService<Keyword, String> {

	/**
	 * 获取某规则的关键字
	 * */
	public List<Keyword> findByRuleId(String ruleId);
}
