package com.yxw.statistics.biz.stats.service;

import com.yxw.statistics.biz.vo.StatsVo;

public interface ClinicStatsService {
	/**
	 * 获取统计信息
	 * @param statsVo
	 * @return
	 */
	public String getStatsInfo(StatsVo statsVo);
	
}
