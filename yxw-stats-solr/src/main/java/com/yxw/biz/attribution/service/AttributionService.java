package com.yxw.biz.attribution.service;

import java.util.List;

import com.yxw.vo.attribution.AttributionStats;
import com.yxw.vo.attribution.AttributionStatsRequest;
import com.yxw.vo.attribution.CityVo;

public interface AttributionService {
	
	/**
	 * 获取所有的城市列表信息
	 * @return
	 */
	public List<CityVo> getCities();
	
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public List<AttributionStats> getStatsInfos(AttributionStatsRequest request);
	
	/**
	 * 按城市进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(AttributionStatsRequest request);
	
	/**
	 * 按城市进行单月统计
	 * @param request
	 * @return
	 */
	public String statsInfoForMonth(AttributionStatsRequest request);
	
}
