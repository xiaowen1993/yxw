package com.yxw.easyhealth.biz.community.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.easyhealth.biz.community.dao.CommunityOrganizationSchmagDao;
import com.yxw.easyhealth.biz.community.service.CommunityOrganizationSchmagService;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

/**
 * 
 * @Package: com.yxw.easyhealth.biz.community.service.impl
 * @ClassName: CommunityOrganizationSchmagServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	郑灏帆
 * @Create Date: 2015年10月24日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "communityOrganizationSchmagService")
public class CommunityOrganizationSchmagServiceImpl extends BaseServiceImpl<CommunityOrganizationSchmag, String> implements
		CommunityOrganizationSchmagService {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(CommunityOrganizationSchmagServiceImpl.class);

	@Autowired
	CommunityOrganizationSchmagDao communityOrganizationSchmagDao;

	@Override
	protected BaseDao<CommunityOrganizationSchmag, String> getDao() {
		return communityOrganizationSchmagDao;
	}

	@Override
	public CommunityOrganizationSchmag findCommunityOrganizationSchmagById(Map<String, Object> params) {
		return communityOrganizationSchmagDao.findCommunityOrganizationSchmagById(params);
	}

	@Override
	public List<CommunityOrganizationSchmag> findCommunityOrganizationSchmagAll() {
		return communityOrganizationSchmagDao.findCommunityOrganizationSchmagAll();
	}

	@Override
	public List<CommunityOrganizationSchmag> findOrganizaionByCondition(Map<String, Object> params) {
		return communityOrganizationSchmagDao.findOrganizaionByCondition(params);
	}

	@Override
	public Map<String, Object> addOrganizaion(CommunityOrganizationSchmag organizationSchmag) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			organizationSchmag.setCreateTime(new Date().getTime());
			organizationSchmag.setUpdateTime(new Date().getTime());
			String id = PKGenerator.generateId();
			organizationSchmag.setId(id);
			communityOrganizationSchmagDao.addOrganizaion(organizationSchmag);

			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			resultMap.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("社康中心-添加医生排班信息失败");
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "社康中心-添加医生排班信息失败");
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> updateOrganizaion(CommunityOrganizationSchmag organizationSchmag) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			organizationSchmag.setUpdateTime(new Date().getTime());
			communityOrganizationSchmagDao.updateOrganizaion(organizationSchmag);

			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			resultMap.put("id", organizationSchmag.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("社康中心-修改医生排班信息失败");
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "社康中心-修改医生排班信息失败");
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> deleteOrganizaion(Map<String, Object> params) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			communityOrganizationSchmagDao.deleteOrganizaion(params);

			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			//			resultMap.put("card", organizationSchmag.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("社康中心-删除医生排班信息失败");
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "社康中心-删除医生排班信息失败");
		}

		return resultMap;
	}
}
