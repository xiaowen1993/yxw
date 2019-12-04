package com.yxw.stats.dao.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysMedicalCardStatistical;

public interface YxDataSysMedicalCardStatisticalDao {
	public String findCurrMedicalCardStatsMaxDate(String hospitalId);

	public void batchInsertMedicalCardStatsData(List<YxDataSysMedicalCardStatistical> entitys);
}