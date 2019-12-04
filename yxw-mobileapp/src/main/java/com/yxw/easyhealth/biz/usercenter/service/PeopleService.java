package com.yxw.easyhealth.biz.usercenter.service;

import com.yxw.commons.entity.mobile.biz.usercenter.People;
import com.yxw.framework.mvc.service.BaseService;

public interface PeopleService extends BaseService<People, String> {
	/**
	 * 通过身份证获取人物信息(大人:idNo, 小孩:guardIdNo)
	 * @param userType
	 * @param idNo
	 * @return
	 */
	public People findByIdNo(int idType, String idNo);

	public People findByNameAndGuardIdTypeAndGuardIdNo(String name, int idType, String idNo);

	/**
	 * 保存人的信息
	 * @param people
	 */
	public void savePeople(People people);
}
