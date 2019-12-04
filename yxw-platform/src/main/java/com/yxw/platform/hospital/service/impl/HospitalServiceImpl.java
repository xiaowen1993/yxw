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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeInterfaceAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.HospitalDao;
import com.yxw.platform.hospital.service.HospitalService;

/**
 * 后台医院管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "hospitalService")
public class HospitalServiceImpl extends BaseServiceImpl<Hospital, String> implements HospitalService {

	@Autowired
	private HospitalDao hospitalDao;

	@Override
	protected BaseDao<Hospital, String> getDao() {
		return hospitalDao;
	}

	@Override
	public void updateStatus(Hospital hospital) {
		hospitalDao.updateStatus(hospital);

		BaseDatasManager cacheManager = SpringContextHolder.getBean(BaseDatasManager.class);
		//cacheManager.updateHospitalStatus(hospital.getCode(), hospital.getStatus());
		//cacheManager.updateHospStatus(hospital);

		//modify by homer.yang@2016-05-26
		List<CodeAndInterfaceVo> vos = queryCodeAndInterfaceById(hospital.getId());
		cacheManager.updateHospitalForBranch(vos, hospital);
	}

	@Override
	public Hospital findHospitalByCode(String code) {
		return hospitalDao.findHospitalByCode(code);
	}

	@Override
	public List<Hospital> findHospitalByCodes(List<String> codes) {
		return hospitalDao.findHospitalByCodes(codes);
	}

	@Override
	public String add(Hospital entity) {
		// TODO Auto-generated method stub
		String id = super.add(entity);
		BaseDatasManager cacheManager = SpringContextHolder.getBean(BaseDatasManager.class);
		cacheManager.updateHospital(entity, CacheConstant.UPDATE_OP_TYPE_ADD);
		return id;

	}

	@Override
	public void delete(Hospital entity) {
		// TODO Auto-generated method stub
		super.delete(entity);
		BaseDatasManager cacheManager = SpringContextHolder.getBean(BaseDatasManager.class);
		cacheManager.updateHospital(entity, CacheConstant.UPDATE_OP_TYPE_DEL);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		// super.deleteById(id);
		Hospital hospital = super.findById(id);
		delete(hospital);

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		super.deleteAll();
		BaseDatasManager cacheManager = SpringContextHolder.getBean(BaseDatasManager.class);
		cacheManager.deleteAllHospital();
	}

	@Override
	public void update(Hospital entity) {
		// TODO Auto-generated method stub
		super.update(entity);
		BaseDatasManager cacheManager = SpringContextHolder.getBean(BaseDatasManager.class);
		cacheManager.updateHospital(entity, CacheConstant.UPDATE_OP_TYPE_UPDATE);
	}

	public void updateRuleModifyTime(Hospital entity) {
		super.update(entity);
	}

	@Override
	public Hospital findHospitalByCodeAndID(Hospital hospital) {
		return hospitalDao.findHospitalByCodeAndID(hospital);
	}

	@Override
	public Hospital findHospitalByNameAndID(Hospital hospital) {
		return hospitalDao.findHospitalByNameAndID(hospital);
	}

	@Override
	public Hospital findHospitalByName(String name) {
		return hospitalDao.findHospitalByName(name);
	}

	@Override
	public boolean isUniqueCode(Hospital hospital) {
		String id = hospital.getId();
		Hospital entity = hospitalDao.findHospitalByCode(hospital.getCode());
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
	public boolean isUniqueName(Hospital hospital) {
		String id = hospital.getId();
		Hospital entity = hospitalDao.findHospitalByName(hospital.getName());
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
	public void publishRule(Hospital hospital) {
		// TODO Auto-generated method stub
		hospitalDao.updatePublishRuleInfo(hospital);
	}

	@Override
	public List<HospitalCodeAndAppVo> queryCodeAndApp() {
		// TODO Auto-generated method stub
		return hospitalDao.queryCodeAndApp();
	}

	@Override
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndApp() {
		return hospitalDao.queryCodeAndInterfaceIdAndApp();
	}

	@Override
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndAppByAppId(String appId) {
		return hospitalDao.queryCodeAndInterfaceIdAndAppByAppId(appId);
	}

	@Override
	public PageInfo<Hospital> findHadBranchByPage(Map<String, Object> params, Page<Hospital> page) {
		// TODO Auto-generated method stub
		return hospitalDao.findHadBranchByPage(params, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.platform.hospital.service.HospitalService#findByHospitalIds(java
	 * .lang.String[])
	 */
	@Override
	public List<Hospital> findByHospitalIds(String[] hospitalIds) {
		// TODO Auto-generated method stub
		return hospitalDao.findByHospitalIds(hospitalIds);
	}

	@Override
	public HospIdAndAppSecretVo findAppSecretByAppId(Map<String, Object> params) {
		return hospitalDao.findAppSecretByAppId(params);
	}

	public List<HospIdAndAppSecretVo> findAppSecretByHospitalId(String hospitalId) {
		return hospitalDao.findAppSecretByHospitalId(hospitalId);
	}

	public List<HospIdAndAppSecretVo> findAllAppSecret() {
		return hospitalDao.findAllAppSecret();
	}

	@Override
	public String findAppIdByHospitalId(Map<String, Object> params) {
		return hospitalDao.findAppIdByHospitalId(params);
	}

	public List<CodeAndInterfaceVo> queryCodeAndInterfaceById(String hospitalId) {
		return hospitalDao.queryCodeAndInterfaceById(hospitalId);
	}

	public List<CodeAndInterfaceVo> queryAllCodeAndInterfaceIds() {
		return hospitalDao.queryAllCodeAndInterfaceIds();
	}

	@Override
	public Map<String, Object> isValidActivated(String hospitalId) {
		// TODO Auto-generated method stub
		return hospitalDao.isValidActivated(hospitalId);
	}

	@Override
	public Hospital findHospitalByBranchHospitalCode(String code) {
		return hospitalDao.findHospitalByBranchHospitalCode(code);
	}

	@Override
	public List<HospitalCodeAndAppVo> queryCodeAndAppByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		return hospitalDao.queryCodeAndAppByHospitalId(hospitalId);
	}

	@Override
	public void updateForRuleEditTime(Hospital hospital) {
		// TODO Auto-generated method stub
		hospitalDao.updateForRuleEditTime(hospital);
	}

	/**
	 * 根据地区代码获取医院列表
	 */
	@Override
	public List<Hospital> getHospitalByAreaCode(String areaCode) {

		return hospitalDao.getHospitalByAreaCode(areaCode);
	}

	@Override
	public List<HospitalInfoByEasyHealthVo> queryHospitalAndOptionByAppCodeAndBizCode(Map<String, Object> params) {
		return hospitalDao.queryHospitalAndOptionByAppCodeAndBizCode(params);
	}

	@Override
	public List<Hospital> getAvailableHospitalByAreaCode(String areaCode) {
		return hospitalDao.getAvailableHospitalByAreaCode(areaCode);
	}

	@Override
	public List<Hospital> findAllByStatus() {
		return hospitalDao.findAllByStatus();
	}
}
