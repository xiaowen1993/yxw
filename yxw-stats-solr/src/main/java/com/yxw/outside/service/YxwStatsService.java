package com.yxw.outside.service;

public interface YxwStatsService {
	/**
	 * 获取摘要
	 * @return
	 */
	public String getAllResume();
	
	/**
	 * 获取区域医院上线情况
	 * @return
	 */
	public String getAreaHospitalInfos();
	
	/**
	 * 获取绑卡的统计信息
	 * @param areaCode
	 * @param hospitalCode
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public String getCardDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth);
	
	/**
	 * 获取关注的统计信息
	 * @param areaCode
	 * @param hospitalCode
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public String getSubscribeDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth);
	
	/**
	 * 获取订单统计信息
	 * @param areaCode
	 * @param hospitalCode
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public String getOrderDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth);
	
	/**
	 * 返回所有的归属地信息 （默认拿上一个月的数据）
	 * @return
	 */
	public String getAttributionDatas();
	
	/**
	 * 获取性别统计信息
	 * @param areaCode
	 * @param hospitalCode
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public String getGenderDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth);
	
	/**
	 * 获取年龄段统计信息
	 * @param areaCode
	 * @param hospitalCode
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public String getAgeGroupDatas(String areaCode, String hospitalCode, String beginMonth, String endMonth);
}
