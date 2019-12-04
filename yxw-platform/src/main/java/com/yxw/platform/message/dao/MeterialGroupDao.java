package com.yxw.platform.message.dao;

import com.yxw.commons.entity.platform.message.MeterialGroup;
import com.yxw.framework.mvc.dao.BaseDao;

public interface MeterialGroupDao extends BaseDao<MeterialGroup, String> {
	public void deleteLogic(MeterialGroup meterialGroup);
}
