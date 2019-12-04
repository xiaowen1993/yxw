package com.yxw.stats.dao.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysMedicalCardStatistical;

public interface SysMedicalCardStatisticalDao {

	public String findCurrMedicalCardStatsMaxDate(String hospitalId);

	public List<SysMedicalCardStatistical> findMedicalCardStatsData(Map<String, Object> params);

	public void batchInsertMedicalCardStatsData(List<SysMedicalCardStatistical> entitys);
}