package com.yxw.stats.service.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.SysRegStatistical;

public interface SysRegStatisticalService {

	public String findCurrRegisterStatsMaxDate(String hospitalId);

	public List<SysRegStatistical> findRegisterStatsData(Map<String, Object> params);

	public void batchInsertRegisterStatsData(List<SysRegStatistical> entitys);

}