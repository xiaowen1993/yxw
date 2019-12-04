package com.yxw.easyhealth.biz.usercenter.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

public interface FamilyService extends BaseService<Family, String> {
	/**
	 * 保存家人信息
	 * @param family
	 * @return
	 */
	public Map<String, Object> saveFamilyInfo(Family family);

	/**
	 * 更新家人信息
	 * @param family
	 * @return
	 */
	public Map<String, Object> updateFamilyInfo(Family family);

	/**
	 * 获取这个人的所有家人信息
	 * @param openId
	 * @return
	 */
	public List<Family> findFamiliesByOpenId(String openId);

	/**
	 * 这个保存的是本人的信息
	 * @param user
	 * @return
	 */
	public Map<String, Object> saveFamilyInfo(EasyHealthUser user);

	/**
	 * 查询家人信息 
	 * @param familyId
	 * @param openId
	 * @return
	 */
	public Family findFamilyInfo(String familyId, String openId);

	/**
	 * 查询本人信息
	 * @param openId
	 * @return
	 */
	public Family findSelfInfo(String openId);

	/**
	 * 异步同步就诊卡
	 * @param family
	 * @param HopitalId
	 * @param hospitalCode
	 * @return
	 */
	public Map<String, Object> syncMedicalcard(Family family, String hospitalId, String hospitalCode, String appCode);

	/**
	 * 判断能否绑定家人
	 * @param family
	 * @return
	 */
	public Map<String, Object> checkCanBindFamily(Family family);

	/**
	 * 解绑家人信息
	 * @param family
	 * @return
	 */
	public Map<String, Object> unbindFamily(Family family);

	/**
	 * 找所有的家人（包括自己）
	 * @param openId
	 * @return
	 */
	public List<Family> findAllFamilies(String openId);

	/**
	 * 找小孩的信息
	 * @param openId
	 * @param hashTableName
	 * @param name
	 * @param guardIdType
	 * @param guardIdNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIDAndNameAndGuardIdNo(String openId, String hashTableName, String name, int guardIdType,
			String guardIdNo, int state);

	/**
	 * 找大人的信息
	 * @param openId
	 * @param hashTableName
	 * @param idType
	 * @param idNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIdAndIdNo(String openId, String hashTableName, int idType, String idNo, int state);
}
