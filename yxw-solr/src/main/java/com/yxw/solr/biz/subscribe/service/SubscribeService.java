package com.yxw.solr.biz.subscribe.service;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.subscribe.SubscribeStatsRequest;

public interface SubscribeService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(SubscribeStatsRequest request);
	
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
