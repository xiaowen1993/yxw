package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.OrderCount;

@Repository("orderCountDao")
public class OrderCountDaoImpl extends BaseDaoImpl<OrderCount, String> implements OrderCountDao {
	private static Logger logger = LoggerFactory.getLogger(OrderCountDaoImpl.class);

	private final static String SQLNAME_FIND_REG_ORDER_COUNT_BY_DATE = "findRegOrderCountByDate";

	@Override
	public List<OrderCount> findRegOrderCountByDate(Map map) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_REG_ORDER_COUNT_BY_DATE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数统计当天挂号订单统计出错！语句：%s", getSqlName(SQLNAME_FIND_REG_ORDER_COUNT_BY_DATE)), e);
			throw new SystemException(String.format("根据参数统当天计挂号订单统计出错！语句：%s", getSqlName(SQLNAME_FIND_REG_ORDER_COUNT_BY_DATE)), e);
		}
	}

}
