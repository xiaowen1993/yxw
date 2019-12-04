package com.yxw.biz.deposit.service;

import java.util.List;

import com.yxw.vo.deposit.DepositStats;
import com.yxw.vo.deposit.DepositStatsRequest;

public interface DepositService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public List<DepositStats> getStatsInfos(DepositStatsRequest request);
	
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(DepositStatsRequest request);
	
	/**
	 * 按医院进行单月统计
	 * @param request
	 * @return
	 */
	public String statsInfoForMonth(DepositStatsRequest request);
	
	/**
	 * 按区域进行多月统计
	 * @param request
	 * @return
	 */
	public String statsAreaInfos(DepositStatsRequest request);
	
	/**
	 * 按区域进行单月统计。
	 * @param request
	 * @return
	 */
	public String statsAreaInfoForMonth(DepositStatsRequest request);
}
