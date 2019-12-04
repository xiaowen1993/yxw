/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.app.biz.customservice.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.customservice.Feedback;
import com.yxw.framework.mvc.dao.BaseDao;

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
public interface CustomFeedbackDao extends BaseDao<Feedback, String> {

	public List<Feedback> findFeedbackByOpenId(Map<String, String> params);
}
