package com.yxw.solr.biz.medicalcard.service;

import java.util.Map;

import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.solr.vo.medicalcard.CardStats;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;

public interface MedicalcardService {

	/**
	 * 根据条件查询绑卡数据
	 * 
	 * @param request
	 * @param coreName
	 * @return
	 */
	public YxwResponse findCards(CardInfoRequest request);
	
	/**
	 * 找卡
	 * @param request
	 * @return
	 */
	public YxwResponse searchCards(CardInfoRequest request);

	/**
	 * 查询统计信息
	 * 
	 * @param request  （查询一段时间的统计信息）
	 * @return
	 */
	public YxwResponse getStatInfos(CardStatsRequest request);

	/**
	 * 统计昨天信息 （一天） 供轮询使用
	 * 
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithinPlatform(CardStatsRequest request);

	/**
	 * 统计昨天之前的信息 （一段时间） 业务表数据统计后放入统计表
	 * 
	 * @param request
	 * @return
	 */
	public String statsInfosWithinPlatform(CardStatsRequest request);
	
	public Map<String, Object> rebuildWithinPlatform(CardStatsRequest request);
	
	/**
	 * 无关平台
	 * 统计昨天信息 （一天） 供轮询使用 
	 * 
	 * @param request
	 * @return
	 */
	public String statsInfoForDayWithoutPlatform(CardStatsRequest request);

	/**
	 * 无关平台
	 * 统计昨天之前的信息 （一段时间） 业务表数据统计后放入统计表
	 * 
	 * @param request
	 * @return
	 */
	public String statsInfosWithoutPlatform(CardStatsRequest request);
	
	public Map<String, Object> rebuildWithoutPlatform(CardStatsRequest request);

	/**
	 * 获取某一天的统计信息（从统计表中获取）
	 */
	public CardStats getDailyStats(CardStatsRequest request);
	
	/**
	 * 获取一段时间内的统计 
	 */
	public Map<String, CardStats> getStatsForDays(CardStatsRequest request);
}
