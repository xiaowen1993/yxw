package com.yxw.stats.dao.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysRegStatistical;

public interface SysRegStatisticalDao {

	public String findCurrRegisterStatsMaxDate(String hospitalId);

	public List<SysRegStatistical> findRegisterStatsData(Map<String, Object> params);

	public void batchInsertRegisterStatsData(List<SysRegStatistical> entitys);

}