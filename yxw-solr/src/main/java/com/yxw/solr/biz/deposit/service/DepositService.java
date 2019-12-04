package com.yxw.solr.biz.deposit.service;

import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.deposit.DepositStatsRequest;

public interface DepositService {
	/**
	 * 获取统计信息 -- 某日
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(DepositStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithinPlatform(DepositStatsRequest request);
	
	public Map<String, Object> rebuildWithinPlatform(DepositStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(DepositStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithoutPlatform(DepositStatsRequest request);
	
	public Map<String, Object> rebuildWithoutPlatform(DepositStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(DepositStatsRequest request);
}
