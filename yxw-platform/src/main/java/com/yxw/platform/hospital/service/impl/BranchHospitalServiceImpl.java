/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.BranchHospitalDao;
import com.yxw.platform.hospital.service.BranchHospitalService;

/**
 * 后台分院管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "branchHospitalService")
public class BranchHospitalServiceImpl extends BaseServiceImpl<BranchHospital, String> implements BranchHospitalService {

	private Logger logger = LoggerFactory.getLogger(BranchHospitalServiceImpl.class);

	@Autowired
	private BranchHospitalDao branchHospitalDao;

	@Override
	public BranchHospital findBranchHospitalByCode(String code) {
		return branchHospitalDao.findBranchHospitalByCode(code);
	}

	@Override
	public List<BranchHospital> selectBranchHospitalsByHospitalId(String hospitalId) {
		return branchHospitalDao.selectBranchHospitalsByHospitalId(hospitalId);
	}

	@Override
	protected BaseDao<BranchHospital, String> getDao() {
		return branchHospitalDao;
	}

	@Override
	public void batchSaveBranchHospital(List<BranchHospital> branchHospitals) {
		for (BranchHospital branchHospital : branchHospitals) {
			//logger.info(JSONObject.toJSONString(branchHospital));
			if (StringUtils.isNotEmpty(branchHospital.getId())) {
				logger.info("更新： " + JSONObject.toJSONString(branchHospital));
				branchHospitalDao.update(branchHospital);
			} else {
				logger.info("新增： " + JSONObject.toJSONString(branchHospital));
				branchHospitalDao.add(branchHospital);
			}
		}
	}

	@Override
	public BranchHospital findHospitalByCodeForHospitalId(BranchHospital branchHospital) {
		return branchHospitalDao.findHospitalByCodeForHospitalId(branchHospital);
	}

	@Override
	public BranchHospital findHospitalByNameForHospitalId(BranchHospital branchHospital) {
		return branchHospitalDao.findHospitalByNameForHospitalId(branchHospital);
	}

	@Override
	public BranchHospital findHospitalByInterfaceId(BranchHospital branchHospital) {
		return branchHospitalDao.findHospitalByInterfaceId(branchHospital);
	}

	@Override
	public boolean isUniqueCodeForHospitalId(BranchHospital branchHospital) {
		String id = branchHospital.getId();
		BranchHospital entity = this.findHospitalByCodeForHospitalId(branchHospital);
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean isUniqueNameForHospitalId(BranchHospital branchHospital) {
		String id = branchHospital.getId();
		BranchHospital entity = this.findHospitalByNameForHospitalId(branchHospital);
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean isUniqueInterFace(BranchHospital branchHospital) {
		String id = branchHospital.getId();
		BranchHospital entity = this.findHospitalByInterfaceId(branchHospital);
		if (entity == null) {
			return true;
		} else {
			if (entity.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<BranchHospital> findByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return branchHospitalDao.findByHospitalId(hospitalId);
	}

	@Override
	public BranchHospital findBranchHospitalByHospitalCode(String code) {
		return branchHospitalDao.findBranchHospitalByHospitalCode(code);
	}

}
