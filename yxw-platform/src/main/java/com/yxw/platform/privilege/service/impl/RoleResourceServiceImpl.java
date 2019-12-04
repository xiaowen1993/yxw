/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.RoleResource;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.RoleResourceDao;
import com.yxw.platform.privilege.service.RoleResourceService;

/**
 * @Package: com.yxw.platform.privilege.service.impl
 * @ClassName: RoleResourceServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResource, String> implements RoleResourceService {
	@Autowired
	private RoleResourceDao roleResourceDao;

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<RoleResource, String> getDao() {
		return roleResourceDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleResourceService#findResourceByRoleId(java.lang.String)
	 */
	@Override
	public List<Resource> findResourceByRoleId(String roleId) {
		return roleResourceDao.findResourceByRoleId(roleId);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleResourceService#findResourceByRoleIds(java.util.List)
	 */
	@Override
	public List<Resource> findResourceByRoleIds(List<String> roleIds) {
		return roleResourceDao.findResourceByRoleIds(roleIds);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleResourceService#deleteByRoleIds(java.util.List)
	 */
	@Override
	public void deleteByRoleIds(List<String> roleIds) {
		roleResourceDao.deleteByRoleIds(roleIds);
	}

}
