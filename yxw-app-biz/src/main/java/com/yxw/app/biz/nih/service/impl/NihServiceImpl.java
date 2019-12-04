package com.yxw.app.biz.nih.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.app.biz.nih.dao.NihRecordDao;
import com.yxw.app.biz.nih.service.NihService;
import com.yxw.commons.entity.mobile.biz.nih.NihRecord;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

@Service(value = "nihService")
public class NihServiceImpl extends BaseServiceImpl<NihRecord, String> implements NihService {

	@Autowired
	private NihRecordDao dao;

	@Override
	public List<NihRecord> findByUserIdAndAppCode(String userId, String appCode) {
		return dao.findByUserIdAndAppCode(userId, appCode, SimpleHashTableNameGenerator.getNihHashTable(userId, true));
	}

	@Override
	public List<NihRecord> findByAppCode(String appCode) {
		// 后台使用，同时查10表
		// return dao.findByAppCode(appCode);
		return null;
	}

	@Override
	public List<NihRecord> findByUserId(String userId) {
		return dao.findByUserId(userId, SimpleHashTableNameGenerator.getNihHashTable(userId, true));
	}

	@Override
	protected BaseDao<NihRecord, String> getDao() {
		return this.dao;
	}

}
