package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.DepositOrderCount;

@Repository("depositOrderCountDao")
public class DepositOrderCountDaoImpl extends BaseDaoImpl<DepositOrderCount, String> implements DepositOrderCountDao {
	private static Logger logger = LoggerFactory.getLogger(DepositOrderCountDaoImpl.class);

	private final static String SQLNAME_FIND_DEPOSIT_ORDER_COUNT_BY_DATE = "findDepositOrderCountByDate";

	@Override
	public List<DepositOrderCount> findDepositOrderCountByDate(Map map) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_DEPOSIT_ORDER_COUNT_BY_DATE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数统计当天门诊订单统计出错！语句：%s", getSqlName(SQLNAME_FIND_DEPOSIT_ORDER_COUNT_BY_DATE)), e);
			throw new SystemException(String.format("根据参数统当天计门诊订单统计出错！语句：%s", getSqlName(SQLNAME_FIND_DEPOSIT_ORDER_COUNT_BY_DATE)), e);
		}
	}

}
