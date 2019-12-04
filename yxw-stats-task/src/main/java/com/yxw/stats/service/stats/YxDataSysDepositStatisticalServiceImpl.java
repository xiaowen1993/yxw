package com.yxw.stats.service.stats;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.stats.YxDataSysDepositStatisticalDao;
import com.yxw.stats.entity.stats.YxDataSysDepositStatistical;

@Service(value = "yxDataSysDepositStatisticalService")
public class YxDataSysDepositStatisticalServiceImpl implements YxDataSysDepositStatisticalService {

	private YxDataSysDepositStatisticalDao yxDataSysDepositStatisticalDao = SpringContextHolder.getBean("yxDataSysDepositStatisticalDao");

	public String findCurrDepositStatsMaxDate(String hospitalId) {

		return yxDataSysDepositStatisticalDao.findCurrDepositStatsMaxDate(hospitalId);
	}

	public void batchInsertDepositStatsData(List<YxDataSysDepositStatistical> entitys) {
		yxDataSysDepositStatisticalDao.batchInsertDepositStatsData(entitys);
	}
}