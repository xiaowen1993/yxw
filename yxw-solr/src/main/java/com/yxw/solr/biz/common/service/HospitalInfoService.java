package com.yxw.solr.biz.common.service;

import java.util.List;
import java.util.Map;

import com.yxw.solr.vo.HospitalInfosVo;
import com.yxw.solr.vo.StatsHospitalInfosVo;

public interface HospitalInfoService {
	public Map<String, Map<String, List<String>>> getInfos();
	
	/**
	 * 获取所有医院、平台、交易方式、分院信息 -- for轮询
	 * @return
	 */
	public Map<String, Map<String, Map<String, List<String>>>> getAllInfosMap();
	
	public List<StatsHospitalInfosVo> getAllInfos();
	
	public List<StatsHospitalInfosVo> getStdInfos();
	
	/**
	 * 通过appId，获取医院、平台、交易方式 -- for外部接口
	 * @param appId
	 * @return
	 */
	public Map<String, Map<String, List<String>>> getInfosMapByAppId(String appId);
	
	public List<HospitalInfosVo> getInfosByAppId(String appId);
	
	/**
	 * 获取标准平台医院信息（关注统计使用）
	 * areaCode
	 * @return
	 */
	public Map<String, List<StatsHospitalInfosVo>> getAllStdInfos();
}
