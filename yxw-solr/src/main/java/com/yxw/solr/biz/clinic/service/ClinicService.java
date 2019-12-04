package com.yxw.solr.biz.clinic.service;

import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.clinic.ClinicStatsRequest;

public interface ClinicService {
	/**
	 * 获取统计信息 -- 某日
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(ClinicStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithinPlatform(ClinicStatsRequest request);
	
	public Map<String, Object> rebuildWithinPlatform(ClinicStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(ClinicStatsRequest request);
	
	/**
	 * 统计所有信息 索引重建
	 * @param request
	 * @return
	 */
	public String statsInfosWithoutPlatform(ClinicStatsRequest request);
	
	public Map<String, Object> rebuildWithoutPlatform(ClinicStatsRequest request);
	
	/**
	 * 统计昨天的信息 只做新增、优化操作
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(ClinicStatsRequest request);
}
