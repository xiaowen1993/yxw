package com.yxw.easyhealth.biz.community.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.easyhealth.biz.community.dao.CommunityHealthCenterDao;
import com.yxw.easyhealth.biz.community.service.CommunityHealthCenterService;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

/**
 * 
 * @Package: com.yxw.easyhealth.biz.community.service.impl
 * @ClassName: CommunityHealthCenterServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	郑灏帆
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "communityHealthCenterService")
public class CommunityHealthCenterServiceImpl extends BaseServiceImpl<CommunityHealthCenter, String> implements CommunityHealthCenterService {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(CommunityHealthCenterServiceImpl.class);

	@Autowired
	CommunityHealthCenterDao communityHealthCenterDao;

	@Override
	protected BaseDao<CommunityHealthCenter, String> getDao() {
		return communityHealthCenterDao;
	}

	@Override
	public CommunityHealthCenter findCommunityHealthCenterById(Map<String, Object> params) {
		return communityHealthCenterDao.findCommunityHealthCenterById(params);
	}

	@Override
	public List<CommunityHealthCenter> findCommunityHealthCenterAll() {
		return communityHealthCenterDao.findCommunityHealthCenterAll();
	}

	@Override
	public List<String> findGroupByAdministrativeRegion() {
		return communityHealthCenterDao.findGroupByAdministrativeRegion();
	}

	@Override
	public List<CommunityHealthCenter> findByAdministrativeRegion(Map<String, Object> params) {
		return communityHealthCenterDao.findByAdministrativeRegion(params);
	}
}
