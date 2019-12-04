package com.yxw.easyhealth.biz.community.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.framework.mvc.dao.BaseDao;

public interface CommunityHealthCenterDao extends BaseDao<CommunityHealthCenter, String> {

	/**
	 * 分组得到 各分区 区名
	 * @param params
	 * @return
	 */
	List<String> findGroupByAdministrativeRegion();

	/**
	 * 根据分区 区名 查询 社康中心信息
	 * @param params
	 * @return
	 */
	List<CommunityHealthCenter> findByAdministrativeRegion(Map<String, Object> params);

	/**
	 * 根据 id 查询 社康中心信息
	 * @param params
	 * @return
	 */
	CommunityHealthCenter findCommunityHealthCenterById(Map<String, Object> params);

	/**
	 * 查询所有 社康中心信息
	 * @param params
	 * @return
	 */
	List<CommunityHealthCenter> findCommunityHealthCenterAll();

}
