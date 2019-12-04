package com.yxw.mobileapp.biz.register.dao;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;

public interface StopRegisterRecordDao extends BaseDao<StopRegisterRecord, String> {
	public void deleteByLaunchTime(String min, String max);
}
