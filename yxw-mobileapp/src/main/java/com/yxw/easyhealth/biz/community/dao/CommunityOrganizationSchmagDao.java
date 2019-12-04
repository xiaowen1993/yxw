package com.yxw.easyhealth.biz.community.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.framework.mvc.dao.BaseDao;

public interface CommunityOrganizationSchmagDao extends BaseDao<CommunityOrganizationSchmag, String> {

	/**
	 * 根据查询条件得到  该社康中心 排班医生信息
	 * @param params
	 * @return
	 */
	List<CommunityOrganizationSchmag> findOrganizaionByCondition(Map<String, Object> params);

	/**
	 * 根据 id 查询 该社康中心 排班医生信息
	 * @param params
	 * @return
	 */
	CommunityOrganizationSchmag findCommunityOrganizationSchmagById(Map<String, Object> params);

	/**
	 * 查询所有该社康中心 排班医生信息
	 * @param params
	 * @return
	 */
	List<CommunityOrganizationSchmag> findCommunityOrganizationSchmagAll();

	/**
	 * 添加
	 * @param organizationSchmag
	 * @return
	 */
	void addOrganizaion(CommunityOrganizationSchmag organizationSchmag);

	/**
	 * 修改
	 * @param organizationSchmag
	 * @return
	 */
	void updateOrganizaion(CommunityOrganizationSchmag organizationSchmag);

	/**
	 * 删除
	 * @param organizationSchmag
	 * @return
	 */
	void deleteOrganizaion(Map<String, Object> params);

}
