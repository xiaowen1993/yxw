package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.MedicalCardCount;

public interface MedicalCardCountService extends BaseService<MedicalCardCount, String> {

	/**
	 * 统计绑卡
	 * @param map
	 * @return
	 */
	public abstract List<MedicalCardCount> findMedicalCardCountByDate(Map map);
}
