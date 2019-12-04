package com.yxw.hospitalmanager.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.hospitalmanager.dao.HospitalDao;
import com.yxw.hospitalmanager.entity.Hospital;
import com.yxw.hospitalmanager.service.HospitalService;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

@Service(value="hospitalService")
public class HospitalServiceImpl extends BaseServiceImpl<Hospital, String> implements HospitalService {
	
	private HospitalDao hospitalDao = SpringContextHolder.getBean(HospitalDao.class); 

	@Override
	protected BaseDao<Hospital, String> getDao() {
		return hospitalDao;
	}

	@Override
	public List<Hospital> findByName(String name) {
		return hospitalDao.findByName(name);
	}

	@Override
	public List<HospitalInfosVo> findAllHospitalInfos(String name) {
		return hospitalDao.findAllHospitalInfos(name);
	}

	@Override
	public boolean checkHospitalExistsByName(String name) {
		return !CollectionUtils.isEmpty(hospitalDao.findHospitalsByName(name));
	}

	@Override
	public boolean checkHospitalExistsByCode(String code) {
		return !CollectionUtils.isEmpty(hospitalDao.findHospitalsByName(code));
	}

}
