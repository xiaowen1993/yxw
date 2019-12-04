package com.yxw.biz.medicalcard.service;

import com.yxw.outside.vo.YxwResponse;
import com.yxw.vo.medicalcard.CardStatsRequest;

public interface MedicalcardService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public YxwResponse getStatsInfos(CardStatsRequest request);
	
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(CardStatsRequest request);
	
	/**
	 * 按医院进行单月统计	 -- 都不按照单月统计了
	 * @param request
	 * @return
	 */
	@Deprecated
	public String statsInfoForMonth(CardStatsRequest request);
	
	/**
	 * 按区域进行多月统计
	 * @param request
	 * @return
	 */
	public String statsAreaInfos(CardStatsRequest request);
	
	/**
	 * 按区域进行单月统计。 -- 都不按照单月统计了
	 * @param request
	 * @return
	 */
	@Deprecated
	public String statsAreaInfoForMonth(CardStatsRequest request);
}
