package com.yxw.easyhealth.biz.vaccinate.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.mobile.biz.vaccinate.Vaccinate;
import com.yxw.easyhealth.biz.vaccinate.dao.VaccinateDao;
import com.yxw.easyhealth.biz.vaccinate.service.VaccinateService;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

@Service(value = "vaccinateService")
public class VaccinateServiceImpl extends BaseServiceImpl<Vaccinate, String> implements VaccinateService {
	private Logger logger = LoggerFactory.getLogger(VaccinateServiceImpl.class);

	@Autowired
	private VaccinateDao vaccinateDao;

	@Override
	public List<Vaccinate> findVaccinateClinicsByRegionName(String regionName) {
		return vaccinateDao.findVaccinateClinicsByRegionName(regionName);
	}

	@Override
	protected BaseDao<Vaccinate, String> getDao() {
		return vaccinateDao;
	}

}
