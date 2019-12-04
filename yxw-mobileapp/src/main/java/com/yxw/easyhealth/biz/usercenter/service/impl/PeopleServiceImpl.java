package com.yxw.easyhealth.biz.usercenter.service.impl;

import org.springframework.stereotype.Service;

import com.yxw.commons.entity.mobile.biz.usercenter.People;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.easyhealth.biz.usercenter.dao.PeopleDao;
import com.yxw.easyhealth.biz.usercenter.service.PeopleService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

@Service(value = "peopleService")
public class PeopleServiceImpl extends BaseServiceImpl<People, String> implements PeopleService {

	private PeopleDao peopleDao = SpringContextHolder.getBean(PeopleDao.class);

	@Override
	public People findByIdNo(int idType, String idNo) {
		String hashTableName = SimpleHashTableNameGenerator.getPeopleHashTable(idNo, true);
		return peopleDao.findByIdTypeAndIdNo(idType, idNo, hashTableName);
	}

	@Override
	public void savePeople(People people) {
		people.setCreateTime(System.currentTimeMillis());
		peopleDao.add(people);
	}

	@Override
	protected BaseDao<People, String> getDao() {
		return peopleDao;
	}

	@Override
	public People findByNameAndGuardIdTypeAndGuardIdNo(String name, int idType, String idNo) {
		String hashTableName = SimpleHashTableNameGenerator.getPeopleHashTable(idNo, true);
		return peopleDao.findByNameAndGuardIdTypeAndGuardIdNo(name, idType, idNo, hashTableName);
	}

}
