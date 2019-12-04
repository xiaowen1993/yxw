package com.yxw.easyhealth.biz.usercenter.dao;

import com.yxw.commons.entity.mobile.biz.usercenter.People;
import com.yxw.framework.mvc.dao.BaseDao;

public interface PeopleDao extends BaseDao<People, String> {
	/**
	 * 通过身份证获取人物信息(大人:idNo, 小孩:guardIdNo)
	 * @param userType
	 * @param idNo
	 * @return
	 */
	public People findByIdTypeAndIdNo(int idType, String idNo, String hashTableName);

	/**
	 * 通过身份证获取人物信息(大人:idNo, 小孩:guardIdNo)
	 * @param userType
	 * @param idNo
	 * @return
	 */
	public People findByNameAndGuardIdTypeAndGuardIdNo(String name, int guardIdType, String guardIdNo, String hashTableName);

}
