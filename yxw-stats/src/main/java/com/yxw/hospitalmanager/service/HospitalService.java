package com.yxw.hospitalmanager.service;

import java.util.List;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.hospitalmanager.entity.Hospital;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

public interface HospitalService extends BaseService<Hospital, String> {
	public List<Hospital> findByName(String name);
	
	public List<HospitalInfosVo> findAllHospitalInfos(String name);
	
	public boolean checkHospitalExistsByName(String name);
	
	public boolean checkHospitalExistsByCode(String code);
}
