package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.DepositOrderCountDao;
import com.yxw.stats.entity.platform.DepositOrderCount;

@Service(value = "depositOrderCountService")
public class DepositOrderCountServiceImpl extends BaseServiceImpl<DepositOrderCount, String> implements DepositOrderCountService {
	public static Logger logger = LoggerFactory.getLogger(DepositOrderCountServiceImpl.class);

	@Autowired
	private DepositOrderCountDao depositOrderCountDao;

	@Override
	protected BaseDao<DepositOrderCount, String> getDao() {
		// TODO Auto-generated method stub
		return depositOrderCountDao;
	}

	/**
	 * 当天门诊订单统计
	 * 
	 * @param map
	 * @return
	 */
	public List<DepositOrderCount> findDepositOrderCountByDate(Map map) {
		return depositOrderCountDao.findDepositOrderCountByDate(map);
	}
}
