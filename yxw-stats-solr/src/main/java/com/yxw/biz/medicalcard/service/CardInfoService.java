package com.yxw.biz.medicalcard.service;

import com.yxw.vo.medicalcard.CardInfoStatsRequest;

public interface CardInfoService {
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(CardInfoStatsRequest request);
	
	/**
	 * 按区域进行多月统计 年龄段
	 * @param request
	 * @return
	 */
	public String statsAreaAgeGroupInfos(CardInfoStatsRequest request);
	
	/**
	 * 按区域进行多月统计 性别
	 * @param request
	 * @return
	 */
	public String statsAreaGenderInfos(CardInfoStatsRequest request);
	
	public String statsAreaInfos(CardInfoStatsRequest request);
}
