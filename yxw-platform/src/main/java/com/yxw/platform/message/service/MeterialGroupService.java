package com.yxw.platform.message.service;

import com.yxw.commons.entity.platform.message.MeterialGroup;
import com.yxw.framework.mvc.service.BaseService;

public interface MeterialGroupService extends BaseService<MeterialGroup, String> {

	public void logicDelete(MeterialGroup meterialGroup);
}
