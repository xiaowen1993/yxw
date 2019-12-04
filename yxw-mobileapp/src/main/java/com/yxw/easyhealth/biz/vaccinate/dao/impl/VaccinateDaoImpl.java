package com.yxw.easyhealth.biz.vaccinate.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.mobile.biz.vaccinate.Vaccinate;
import com.yxw.easyhealth.biz.vaccinate.dao.VaccinateDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

@Repository
public class VaccinateDaoImpl extends BaseDaoImpl<Vaccinate, String> implements VaccinateDao {

	private Logger logger = LoggerFactory.getLogger(VaccinateDaoImpl.class);

	private final static String SQLNAME_FIND_VACCINATE_CLINICS_BY_REGION_NAME = "findVaccinateClinicsByRegionName";

	@Override
	public List<Vaccinate> findVaccinateClinicsByRegionName(String regionName) {
		List<Vaccinate> vaccinateClinics = null;
		try {
			vaccinateClinics = sqlSession.selectList(getSqlName(SQLNAME_FIND_VACCINATE_CLINICS_BY_REGION_NAME), regionName);
		} catch (Exception e) {
			logger.error(String.format("根据地区获取所有的接种门诊！语句：%s", getSqlName(SQLNAME_FIND_VACCINATE_CLINICS_BY_REGION_NAME)), e);
			throw new SystemException(String.format("根据地区获取所有的接种门诊！语句：%s", getSqlName(SQLNAME_FIND_VACCINATE_CLINICS_BY_REGION_NAME)), e);
		}
		return vaccinateClinics;
	}

}
