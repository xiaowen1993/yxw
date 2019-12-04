package com.yxw.stats.service.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataUserStats;

public interface YxDataUserStatsService {
	public String findCurrUserStatsMaxDate(String hospitalId);

	public void batchInsertUserStatsData(List<YxDataUserStats> entitys);
}