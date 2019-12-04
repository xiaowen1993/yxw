package com.yxw.stats.service.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysClinicStatistical;

public interface SysClinicStatisticalService {

	public String findCurrClinicStatsMaxDate(String hospitalId);

	public List<SysClinicStatistical> findClinicStatsData(Map<String, Object> params);

	public void batchInsertClinicStatsData(List<SysClinicStatistical> entitys);
}