package com.yxw.stats.dao.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysRegStatistical;

public interface YxDataSysRegStatisticalDao {
	public String findCurrRegisterStatsMaxDate(String hospitalId);

	public void batchInsertRegisterStatsData(List<YxDataSysRegStatistical> entitys);
}