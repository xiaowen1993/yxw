package com.yxw.stats.dao.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysDepositStatistical;

public interface SysDepositStatisticalDao {

	public String findCurrDepositStatsMaxDate(String hospitalId);

	public List<SysDepositStatistical> findDepositStatsData(Map<String, Object> params);

	public void batchInsertDepositStatsData(List<SysDepositStatistical> entitys);
}