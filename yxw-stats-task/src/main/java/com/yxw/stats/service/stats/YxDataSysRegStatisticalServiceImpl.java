package com.yxw.stats.service.stats;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.stats.YxDataSysRegStatisticalDao;
import com.yxw.stats.entity.stats.YxDataSysRegStatistical;

@Service(value = "yxDataSysRegStatisticalService")
public class YxDataSysRegStatisticalServiceImpl implements YxDataSysRegStatisticalService {

	private YxDataSysRegStatisticalDao yxDataSysRegStatisticalDao = SpringContextHolder.getBean("yxDataSysRegStatisticalDao");

	public String findCurrRegisterStatsMaxDate(String hospitalId) {

		return yxDataSysRegStatisticalDao.findCurrRegisterStatsMaxDate(hospitalId);
	}

	public void batchInsertRegisterStatsData(List<YxDataSysRegStatistical> entitys) {
		yxDataSysRegStatisticalDao.batchInsertRegisterStatsData(entitys);
	}

}