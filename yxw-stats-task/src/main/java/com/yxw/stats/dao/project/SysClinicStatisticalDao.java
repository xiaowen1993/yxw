package com.yxw.stats.dao.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysClinicStatistical;

public interface SysClinicStatisticalDao {

	public String findCurrClinicStatsMaxDate(String hospitalId);

	public List<SysClinicStatistical> findClinicStatsData(Map<String, Object> params);

	public void batchInsertClinicStatsData(List<SysClinicStatistical> entitys);
}