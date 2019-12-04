package com.yxw.biz.clinic.ClinicService;

import java.util.List;

import com.yxw.vo.clinic.ClinicStats;
import com.yxw.vo.clinic.ClinicStatsRequest;

public interface ClinicService {
	/**
	 * 获取统计信息 医院、区域、月份 （传入的月份，会截取前7位。）
	 * @param request
	 * @return
	 */
	public List<ClinicStats> getStatsInfos(ClinicStatsRequest request);
	
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(ClinicStatsRequest request);
	
	/**
	 * 按医院进行单月统计
	 * @param request
	 * @return
	 */
	public String statsInfoForMonth(ClinicStatsRequest request);
	
	/**
	 * 按区域进行多月统计
	 * @param request
	 * @return
	 */
	public String statsAreaInfos(ClinicStatsRequest request);
	
	/**
	 * 按区域进行单月统计。
	 * @param request
	 * @return
	 */
	public String statsAreaInfoForMonth(ClinicStatsRequest request);
}
