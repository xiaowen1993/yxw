package com.yxw.platform.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.message.MeterialGroup;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.message.dao.MeterialGroupDao;
import com.yxw.platform.message.service.MeterialGroupService;

@Service
public class MeterialGroupServiceImpl extends BaseServiceImpl<MeterialGroup, String> implements MeterialGroupService {

	@Autowired
	private MeterialGroupDao meterialGroupDao;

	@Override
	protected BaseDao<MeterialGroup, String> getDao() {
		return meterialGroupDao;
	}

	@Override
	public void logicDelete(MeterialGroup meterialGroup) {
		meterialGroupDao.deleteLogic(meterialGroup);
	}

}
