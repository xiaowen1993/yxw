package com.yxw.stats.service.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysClinicStatistical;

public interface YxDataSysClinicStatisticalService {

	public String findCurrClinicStatsMaxDate(String hospitalId);

	public void batchInsertClinicStatsData(List<YxDataSysClinicStatistical> entitys);
}