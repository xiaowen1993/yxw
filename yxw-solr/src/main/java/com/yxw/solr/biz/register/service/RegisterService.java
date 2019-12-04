package com.yxw.solr.biz.register.service;

import java.util.List;
import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.solr.vo.register.RegStatsRequest;

public interface RegisterService {
	/**
	 * 查询挂号订单
	 * @param request
	 * @return
	 */
	public YxwResponse findOrders(RegInfoRequest request);
	
	/**
	 * 统计昨天之前的信息 （一段时间） 业务表数据统计后放入统计表
	 * @param request
	 * @return
	 */
	public String statsInfosWithinPlatform(RegStatsRequest request);
	
	/**
	 * 有关平台数据重建
	 * @param request
	 * @return
	 */
	public Map<String, Object> rebuildWithinPlatform(RegStatsRequest request);
	
	/**
	 * 统计昨天的信息
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(RegStatsRequest request);
	
	/**
	 * 统计昨天之前的信息 （一段时间） 业务表数据统计后放入统计表
	 * @param request
	 * @return
	 */
	public String statsInfosWithoutPlatform(RegStatsRequest request);
	
	/**
	 * 无关平台数据重建
	 * @param request
	 * @return
	 */
	public Map<String, Object> rebuildWithoutPlatform(RegStatsRequest request);
	
	/**
	 * 统计昨天的信息
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(RegStatsRequest request);
	
	/**
	 * 获取统计信息
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(RegStatsRequest request);
	
	/**
	 * 提供给his外部接口查询挂号订单数据
	 * @param regInfoRequests
	 * @return
	 */
	public YxwResult orderQuery(List<RegInfoRequest> regInfoRequests);
	
}
