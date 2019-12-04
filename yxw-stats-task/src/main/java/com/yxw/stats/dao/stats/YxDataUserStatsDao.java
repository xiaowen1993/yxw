package com.yxw.stats.dao.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataUserStats;

public interface YxDataUserStatsDao {
	public String findCurrUserStatsMaxDate(String hospitalId);

	public void batchInsertUserStatsData(List<YxDataUserStats> entitys);
}