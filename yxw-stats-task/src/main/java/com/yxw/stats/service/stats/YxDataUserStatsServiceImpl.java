package com.yxw.stats.service.stats;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.stats.YxDataUserStatsDao;
import com.yxw.stats.entity.stats.YxDataUserStats;

@Repository("yxDataUserStatsService")
public class YxDataUserStatsServiceImpl implements YxDataUserStatsService {

	private Logger logger = LoggerFactory.getLogger(YxDataUserStatsServiceImpl.class);

	private YxDataUserStatsDao yxDataUserStatsDao = SpringContextHolder.getBean("yxDataUserStatsDao");

	public String findCurrUserStatsMaxDate(String hospitalId) {

		return yxDataUserStatsDao.findCurrUserStatsMaxDate(hospitalId);
	}

	public void batchInsertUserStatsData(List<YxDataUserStats> entitys) {

		yxDataUserStatsDao.batchInsertUserStatsData(entitys);
	}

}