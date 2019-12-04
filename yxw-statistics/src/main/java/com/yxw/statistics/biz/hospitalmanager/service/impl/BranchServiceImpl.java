package com.yxw.statistics.biz.hospitalmanager.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.statistics.biz.hospitalmanager.dao.BranchDao;
import com.yxw.statistics.biz.hospitalmanager.entity.Branch;
import com.yxw.statistics.biz.hospitalmanager.service.BranchService;

@Service(value="branchService")
public class BranchServiceImpl extends BaseServiceImpl<Branch, String> implements BranchService {

	private BranchDao branchDao = SpringContextHolder.getBean(BranchDao.class);
	
	@Override
	public boolean checkBranchExistsByName(String name, String hospitalId) {
		return !CollectionUtils.isEmpty(branchDao.findByNameAndHospitalId(name, hospitalId));
	}

	@Override
	public boolean checkBranchExistsByCode(String code, String hospitalId) {
		return !CollectionUtils.isEmpty(branchDao.findByCodeAndHospitalId(code, hospitalId));
	}

	@Override
	protected BaseDao<Branch, String> getDao() {
		return branchDao;
	}

	@Override
	public List<Branch> findAllByHospitalId(String hospitalId) {
		return branchDao.findAllByHospitalId(hospitalId);
	}

	@Override
	public Boolean checkExistsByNameOrCode(Branch branch) {
		return !CollectionUtils.isEmpty(branchDao.findByNameOrCode(branch));
	}

}
