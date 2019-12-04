/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.OrderCountDao;
import com.yxw.stats.entity.platform.OrderCount;

/**
 * @Package: com.yxw.platform.statistics.service.impl
 * @ClassName: StatisticsServiceImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年9月9日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "orderCountService")
public class OrderCountServiceImpl extends BaseServiceImpl<OrderCount, String> implements OrderCountService {
	public static Logger logger = LoggerFactory.getLogger(OrderCountServiceImpl.class);
	@Autowired
	private OrderCountDao orderCountDao;

	@Override
	protected BaseDao<OrderCount, String> getDao() {
		return orderCountDao;
	}

	/**
	 * 当天挂号订单统计
	 * @param map
	 * @return
	 */
	public List<OrderCount> findRegOrderCountByDate(Map map) {
		return orderCountDao.findRegOrderCountByDate(map);
	}
}
