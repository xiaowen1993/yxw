package com.yxw.hospitalmanager.service;

import java.util.List;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.hospitalmanager.entity.PlatformSetting;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

public interface PlatformSettingService extends BaseService<PlatformSetting, String> {

	/**
	 * 根据hospitalId找数据
	 * @param hospitalId
	 * @return
	 */
	public List<PlatformSetting> findAllByHospitalId(String hospitalId);
	
	/**
	 * 根据hospitalId找数据，经过转换的
	 * @param hospitalId
	 * @return
	 */
	public List<HospitalInfosVo> findAllSettings(String hospitalId);
	
	/**
	 * 删除某医院下的所有关系
	 * @param hospitalId
	 */
	public void deleteAllByHospitalId(String hospitalId);
	
	/**
	 * 检测是否在不同医院中存在同一个appId
	 * @param hospitalId
	 * @param appId
	 * @return
	 */
	public boolean checkExistsByHospitalIdAndAppId(String hospitalId, String appId);
	
	/**
	 * @param hospitalId
	 * @param appId
	 * @return
	 */
	public boolean checkExistsByHospitalIdAndAppIdAndPlatformId(String hospitalId, String appId, String platformId);
	
	/**
	 * @param hospitalId
	 * @param appId
	 * @return
	 */
	public boolean checkExistsByHospitalIdAndPlatformIdAndTradeId(String hospitalId, String platformId, String tradeId);
	
}
