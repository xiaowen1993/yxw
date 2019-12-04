package com.yxw.stats.service.project.alipay;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.UserStatsDao;
import com.yxw.stats.entity.project.UserStats;
import com.yxw.stats.service.project.UserStatsService;

@Service(value = "alipayUserStatsService")
public class UserStatsServiceImpl implements UserStatsService {

	private UserStatsDao userStatsDao = SpringContextHolder.getBean("alipayUserStatsDao");

	public String findCurrUserStatsMaxDate(String hospitalId) {

		return userStatsDao.findCurrUserStatsMaxDate(hospitalId);
	}

	public List<UserStats> findUserStatsData(Map<String, Object> params) {
		return userStatsDao.findUserStatsData(params);
	}

	public void batchInsertUserStatsData(List<UserStats> entitys) {
		userStatsDao.batchInsertUserStatsData(entitys);
	}
}