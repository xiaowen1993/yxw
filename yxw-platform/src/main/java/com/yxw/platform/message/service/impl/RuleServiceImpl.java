package com.yxw.platform.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.message.dao.RuleDao;
import com.yxw.platform.message.service.RuleService;

@Service
public class RuleServiceImpl extends BaseServiceImpl<Rule, String> implements RuleService {

	@Autowired
	private RuleDao ruleDao;

	@Override
	protected BaseDao<Rule, String> getDao() {
		return ruleDao;
	}

	public List<Rule> findByHospId(Map<String, Object> params) {
		return ruleDao.findByHospId(params);
	}

	/**
	 * 删除规则下绑定的回复
	 * */
	@Override
	public void deleteRuleReply(Map<String, Object> params) {
		ruleDao.deleteRuleReply(params);
	}
}
