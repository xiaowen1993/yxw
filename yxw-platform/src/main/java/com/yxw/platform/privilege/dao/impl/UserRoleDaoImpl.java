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
package com.yxw.platform.privilege.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.UserRole;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.UserRoleDao;

/**
 * @Package: com.yxw.platform.privilege.dao.impl
 * @ClassName: UserRoleDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole, String> implements UserRoleDao {
	private static Logger logger = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	private static final String SQLNAME_FIND_ROLE_BY_USERID = "findRoleByUserId";

	private static final String SQLNAME_DEL_ROLE_BY_USERID = "deleteByUserId";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.UserRoleDao#findRoleCodeByUserId(java.lang.String)
	 */
	@Override
	public List<Role> findRoleByUserId(String userId) {
		try {
			Assert.notNull(userId);
			List<Role> roles = sqlSession.selectList(getSqlName(SQLNAME_FIND_ROLE_BY_USERID), userId);
			return roles;
		} catch (Exception e) {
			logger.error(String.format("查找用户角色出错！语句：%s", getSqlName(SQLNAME_FIND_ROLE_BY_USERID)), e);
			throw new SystemException(String.format("查找用户角色出错！语句：%s", getSqlName(SQLNAME_FIND_ROLE_BY_USERID)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.UserRoleDao#deleteByUserId(java.lang.String)
	 */
	@Override
	public void deleteByUserId(String userId) {
		try {
			Assert.notNull(userId);
			sqlSession.delete(getSqlName(SQLNAME_DEL_ROLE_BY_USERID), userId);
		} catch (Exception e) {
			logger.error(String.format("删除用户角色出错！语句：%s", getSqlName(SQLNAME_DEL_ROLE_BY_USERID)), e);
			throw new SystemException(String.format("删除用户角色出错！语句：%s", getSqlName(SQLNAME_DEL_ROLE_BY_USERID)), e);
		}
	}

}
