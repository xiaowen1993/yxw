package com.yxw.stats.service.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysRegStatistical;

public interface YxDataSysRegStatisticalService {

	public String findCurrRegisterStatsMaxDate(String hospitalId);

	public void batchInsertRegisterStatsData(List<YxDataSysRegStatistical> entitys);

}