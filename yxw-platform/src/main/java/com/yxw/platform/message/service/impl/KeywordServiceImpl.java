package com.yxw.platform.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.message.dao.KeywordDao;
import com.yxw.platform.message.service.KeywordService;

@Service
public class KeywordServiceImpl extends BaseServiceImpl<Keyword, String> implements KeywordService {

	@Autowired
	private KeywordDao keywordDao;

	@Override
	protected BaseDao<Keyword, String> getDao() {
		return keywordDao;
	}

	/**
	 * 获取某规则的关键字
	 * */
	public List<Keyword> findByRuleId(String ruleId) {
		return keywordDao.findByRuleId(ruleId);
	}
}
