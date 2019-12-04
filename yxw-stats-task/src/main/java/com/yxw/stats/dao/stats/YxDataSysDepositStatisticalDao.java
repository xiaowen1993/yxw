package com.yxw.stats.dao.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysDepositStatistical;

public interface YxDataSysDepositStatisticalDao {
	public String findCurrDepositStatsMaxDate(String hospitalId);

	public void batchInsertDepositStatsData(List<YxDataSysDepositStatistical> entitys);
}