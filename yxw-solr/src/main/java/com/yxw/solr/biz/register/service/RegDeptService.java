package com.yxw.solr.biz.register.service;

import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.register.RegDeptStatsRequest;

public interface RegDeptService {
	
	/**
	 * 获取统计信息 -- 某日
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(RegDeptStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithinPlatform(RegDeptStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(RegDeptStatsRequest request);
	
	/**
	 * 统计从某天开始，以及这天之后到昨天的数据
	 * @param request
	 * @return
	 */
	public Map<String, Object> rebuildWithinPlatform(RegDeptStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithoutPlatform(RegDeptStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(RegDeptStatsRequest request);
	
	/**
	 * 统计从某天开始，以及这天之后到昨天的数据
	 * @param request
	 * @return
	 */
	public Map<String, Object> rebuildWithoutPlatform(RegDeptStatsRequest request);
}
