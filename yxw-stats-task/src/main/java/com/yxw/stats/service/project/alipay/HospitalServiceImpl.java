package com.yxw.stats.service.project.alipay;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.HospitalDao;
import com.yxw.stats.entity.project.Hospital;
import com.yxw.stats.service.project.HospitalService;

@Service(value = "alipayHospitalService")
public class HospitalServiceImpl implements HospitalService {

	private HospitalDao hospitalDao = SpringContextHolder.getBean("alipayHospitalDao");

	public List<Hospital> findAllHospital() {
		return hospitalDao.findAllHospital();
	}

	public List<Hospital> findAllHospital(Long id) {
		return hospitalDao.findAllHospital(id);
	}
}