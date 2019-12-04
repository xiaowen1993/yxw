package com.yxw.biz.common.service;

import java.util.List;
import java.util.Map;

import com.yxw.vo.AreaHospitalInfosVo;
import com.yxw.vo.HospitalInfosVo;
import com.yxw.vo.StatsHospitalInfosVo;

public interface HospitalInfoService {
	public Map<String, Map<String, List<String>>> getInfos();
	
	/**
	 * 获取所有医院、平台、交易方式、分院信息 -- for轮询
	 * @return
	 */
	public Map<String, Map<String, Map<String, List<String>>>> getAllInfosMap();
	
	public List<StatsHospitalInfosVo> getAllInfos();
	
	public List<StatsHospitalInfosVo> getHospitalInfos();
	
	/**
	 * 通过appId，获取医院、平台、交易方式 -- for外部接口
	 * @param appId
	 * @return
	 */
	public Map<String, Map<String, List<String>>> getInfosMapByAppId(String appId);
	
	public List<HospitalInfosVo> getInfosByAppId(String appId);
	
	/**
	 * 获取标准平台医院信息（关注统计使用） -- 只显示上线的
	 * areaCode
	 * @return
	 */
	public Map<String, List<StatsHospitalInfosVo>> getHospitalInfosGroupByAreaCode();
	
	/**
	 * 获取所有的医院信息（无关是否上线）
	 * @return
	 */
	public Map<String, List<StatsHospitalInfosVo>> getAllHospitalInfos();
	
	/**
	 * 获取区域医院信息
	 * @return
	 */
	public List<AreaHospitalInfosVo> getAreaHospitalInfos();
	
}
