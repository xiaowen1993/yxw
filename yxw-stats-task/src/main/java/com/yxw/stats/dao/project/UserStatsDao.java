package com.yxw.stats.dao.project;

import java.util.List;
import java.util.Map;

import com.yxw.stats.entity.project.UserStats;

public interface UserStatsDao {

	public String findCurrUserStatsMaxDate(String hospitalId);

	public List<UserStats> findUserStatsData(Map<String, Object> params);

	public void batchInsertUserStatsData(List<UserStats> entitys);
}