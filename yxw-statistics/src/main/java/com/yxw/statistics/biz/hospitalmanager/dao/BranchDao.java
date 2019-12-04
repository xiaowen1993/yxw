package com.yxw.statistics.biz.hospitalmanager.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.statistics.biz.hospitalmanager.entity.Branch;

public interface BranchDao extends BaseDao<Branch, String> {
	
	public List<Branch> findByNameAndHospitalId(String name, String hospitalId);
	
	public List<Branch> findByCodeAndHospitalId(String code, String hospitalId);
	
	public List<Branch> findAllByHospitalId(String hospitalId);
	
	public void deleteAllByHospitalId(String hospitalId);
	
	public List<Branch> findByNameOrCode(Branch entity);
}
