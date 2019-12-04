package com.yxw.biz.register.service;

import java.util.List;

import com.yxw.vo.register.RegStats;
import com.yxw.vo.register.RegStatsRequest;

public interface RegisterService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public List<RegStats> getStatsInfos(RegStatsRequest request);
	
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(RegStatsRequest request);
	
	/**
	 * 按医院进行单月统计
	 * @param request
	 * @return
	 */
	public String statsInfoForMonth(RegStatsRequest request);
	
	/**
	 * 按区域进行多月统计
	 * @param request
	 * @return
	 */
	public String statsAreaInfos(RegStatsRequest request);
	
	/**
	 * 按区域进行单月统计。
	 * @param request
	 * @return
	 */
	public String statsAreaInfoForMonth(RegStatsRequest request);
}
