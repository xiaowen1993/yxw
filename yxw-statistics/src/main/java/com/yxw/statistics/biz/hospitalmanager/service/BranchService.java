package com.yxw.statistics.biz.hospitalmanager.service;

import java.util.List;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.statistics.biz.hospitalmanager.entity.Branch;

public interface BranchService extends BaseService<Branch, String> {

	public boolean checkBranchExistsByName(String name, String hospitalId);
	
	public boolean checkBranchExistsByCode(String code, String hospitalId);
	
	public List<Branch> findAllByHospitalId(String hospitalId);
	
	public Boolean checkExistsByNameOrCode(Branch branch);
}
