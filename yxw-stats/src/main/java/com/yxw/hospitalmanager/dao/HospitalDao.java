package com.yxw.hospitalmanager.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.hospitalmanager.entity.Hospital;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

public interface HospitalDao extends BaseDao<Hospital, String> {
	/**
	 * 按医院名称模糊找医院
	 * @param name
	 * @return
	 */
	public List<Hospital> findByName(String name);
	
	/**
	 * 按医院名称模糊找医院、平台关系
	 * @param name
	 * @return
	 */
	public List<HospitalInfosVo> findAllHospitalInfos(String name);
	
	public List<Hospital> findHospitalsByName(String name);
	
	public List<Hospital> findHospitalsByCode(String code);
}
