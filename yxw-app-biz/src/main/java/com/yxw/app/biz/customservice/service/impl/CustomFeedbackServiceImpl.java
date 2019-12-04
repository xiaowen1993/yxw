package com.yxw.app.biz.customservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.app.biz.customservice.dao.CustomFeedbackDao;
import com.yxw.app.biz.customservice.service.CustomFeedbackService;
import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

/**
 * 客服中心
 * 
 * @Author: luob
 * @Create Date: 2015-10-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "customFeedbackService")
public class CustomFeedbackServiceImpl extends BaseServiceImpl<Feedback, String> implements CustomFeedbackService {

	private CustomFeedbackDao customFeedbackDao = SpringContextHolder.getBean(CustomFeedbackDao.class);

	@Override
	protected BaseDao<Feedback, String> getDao() {
		return customFeedbackDao;
	}

	@Override
	public List<Feedback> findFeedbackByOpenId(String openId, String type) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("openId", openId);
		params.put("type", type);
		return customFeedbackDao.findFeedbackByOpenId(params);
	}
}
