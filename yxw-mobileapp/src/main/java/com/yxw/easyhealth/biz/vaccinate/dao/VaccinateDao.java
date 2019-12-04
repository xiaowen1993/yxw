package com.yxw.easyhealth.biz.vaccinate.dao;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.vaccinate.Vaccinate;
import com.yxw.framework.mvc.dao.BaseDao;

public interface VaccinateDao extends BaseDao<Vaccinate, String> {

	/**
	 * 根据地区获取所有的接种门诊
	 */
	public abstract List<Vaccinate> findVaccinateClinicsByRegionName(String regionName);

}
