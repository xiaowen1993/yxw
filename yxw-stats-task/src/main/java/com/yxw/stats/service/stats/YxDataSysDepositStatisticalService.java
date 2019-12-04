package com.yxw.stats.service.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysDepositStatistical;

public interface YxDataSysDepositStatisticalService {

	public String findCurrDepositStatsMaxDate(String hospitalId);

	public void batchInsertDepositStatsData(List<YxDataSysDepositStatistical> entitys);
}