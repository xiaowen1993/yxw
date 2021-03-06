package com.yxw.stats.service.project;

import java.util.List;

import com.yxw.stats.entity.project.Hospital;

public interface HospitalService {

	/**
	 * 查询所有医院，也可以指定不查询某个医院
	 * @param id
	 * @return
	 */

	public List<Hospital> findAllHospital();

	public List<Hospital> findAllHospital(Long id);

}