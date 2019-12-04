package com.yxw.app.biz.customservice.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.framework.mvc.service.BaseService;

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
public interface CustomFeedbackService extends BaseService<Feedback, String> {

	public List<Feedback> findFeedbackByOpenId(String openId, String type);
}
