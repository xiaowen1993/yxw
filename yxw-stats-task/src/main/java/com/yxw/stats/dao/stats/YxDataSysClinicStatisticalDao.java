package com.yxw.stats.dao.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysClinicStatistical;

public interface YxDataSysClinicStatisticalDao {

	public String findCurrClinicStatsMaxDate(String hospitalId);

	public void batchInsertClinicStatsData(List<YxDataSysClinicStatistical> entitys);
}