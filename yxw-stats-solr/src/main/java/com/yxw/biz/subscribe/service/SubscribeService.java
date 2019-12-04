package com.yxw.biz.subscribe.service;

import java.util.List;

import com.yxw.vo.subscribe.SubscribeStats;
import com.yxw.vo.subscribe.SubscribeStatsRequest;

public interface SubscribeService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public List<SubscribeStats> getStatsInfos(SubscribeStatsRequest request);
	
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(SubscribeStatsRequest request);
	
	/**
	 * 按医院进行单月统计
	 * @param request
	 * @return
	 */
	public String statsInfoForMonth(SubscribeStatsRequest request);
	
	/**
	 * 按区域进行多月统计
	 * @param request
	 * @return
	 */
	public String statsAreaInfos(SubscribeStatsRequest request);
	
	/**
	 * 按区域进行单月统计。
	 * @param request
	 * @return
	 */
	public String statsAreaInfoForMonth(SubscribeStatsRequest request);
}
