package com.yxw.hospitalmanager.service.impl;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.hospitalmanager.dao.PlatformDao;
import com.yxw.hospitalmanager.entity.Platform;
import com.yxw.hospitalmanager.service.PlatformService;

@Service(value="platformService")
public class PlatformServiceImpl extends BaseServiceImpl<Platform, String> implements PlatformService {

	private PlatformDao dao = SpringContextHolder.getBean(PlatformDao.class);
	
	@Override
	protected BaseDao<Platform, String> getDao() {
		return dao;
	}

}
