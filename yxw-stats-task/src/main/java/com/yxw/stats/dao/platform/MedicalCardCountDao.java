package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.stats.entity.platform.MedicalCardCount;

public interface MedicalCardCountDao extends BaseDao<MedicalCardCount, String> {
	/**
	 * 统计绑卡
	 * @param map
	 * @return
	 */
	public abstract List<MedicalCardCount> findMedicalCardCountByDate(Map map);

}
