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

import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.UserRole;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.UserRoleDao;
import com.yxw.platform.privilege.service.UserRoleService;

/**
 * @Package: com.yxw.platform.privilege.service.impl
 * @ClassName: UserRoleServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, String> implements UserRoleService {
	@Autowired
	private UserRoleDao userRoleDao;

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<UserRole, String> getDao() {
		return userRoleDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserRoleService#findRoleCodeByUserId(java.lang.String)
	 */
	@Override
	public List<Role> findRoleByUserId(String userId) {
		return userRoleDao.findRoleByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserRoleService#deleteByUserId(java.lang.String)
	 */
	@Override
	public void deleteByUserId(String userId) {
		userRoleDao.deleteByUserId(userId);
	}

}
