package com.yxw.stats.service.stats;

import java.util.List;

import com.yxw.stats.entity.stats.YxDataSysMedicalCardStatistical;

public interface YxDataSysMedicalCardStatisticalService {

	public String findCurrMedicalCardStatsMaxDate(String hospitalId);

	public void batchInsertMedicalCardStatsData(List<YxDataSysMedicalCardStatistical> entitys);
}