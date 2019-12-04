package com.yxw.easyhealth.biz.usercenter.dao;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.framework.mvc.dao.BaseDao;

public interface FamilyDao extends BaseDao<Family, String> {
	/**
	 * 通过openId找家人
	 * @param openId
	 * @param hashTableName
	 * @return
	 */
	public List<Family> findFamiliesByOpenId(String openId, String hashTableName, int state);

	/**
	 * 找大人的信息
	 * @param openId
	 * @param hashTableName
	 * @param idType
	 * @param idNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIdAndIdNo(String openId, String hashTableName, int idType, String idNo, int state);

	/**
	 * 找小孩的信息
	 * @param openId
	 * @param hashTableName
	 * @param name
	 * @param guardIdType
	 * @param guardIdNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIDAndNameAndGuardIdNo(String openId, String hashTableName, String name, int guardIdType, String guardIdNo,
			int state);

	/**
	 * 查询本人信息
	 * @param openId
	 * @return
	 */
	public Family findSelfInfo(String openId, String hashTableName);

	/**
	 * 查询家人信息
	 * @param familyId
	 * @param openId
	 * @return
	 */
	public Family findFamilyInfo(String familyId, String openId, String hashTableName);

	/**
	 * 找到所有的家人
	 * @param openId
	 * @param hashTableName
	 * @param state
	 * @return
	 */
	public List<Family> findAllFamilies(String openId, String hashTableName, int state);
}
