package com.yxw.solr.outside.service;

import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;

public interface YxwCardService {
	/**
	 * 获取绑卡统计信息
	 * @param request
	 * @return
	 */
	public String getStatsInfos(CardStatsRequest request);
	
	/**
	 * 获取就诊卡信息
	 * @param request
	 * @return
	 */
	public String findMedicalcards(CardInfoRequest request);
	
	/**
	 * 供外部调用，通过卡号、医院、平台、分院等信息找到卡信息
	 * 平台必要
	 * 分院-1则不列入条件、绑卡状态-1则不列入条件
	 * @param request
	 * @return
	 */
	public String searchCards(CardInfoRequest request);
	
	/**
	 * 统计某一天的数据 -- 预防自动统计异常的问题。
	 * @param request
	 * @return
	 */
	public String statsDailyInfo(CardStatsRequest request);
	
}
