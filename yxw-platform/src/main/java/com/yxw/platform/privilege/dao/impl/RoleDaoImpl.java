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
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.RoleDao;

/**
 * @Package: com.yxw.platform.privilege.dao.impl
 * @ClassName: RoleDaoImpl
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
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

	private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

	private static final String SQLNAME_FIND_BY_ROLENAME = "findByRoleName";

	private static final String SQLNAME_FIND_BY_CODE = "findByCode";

	private static final String SQLNAME_FIND_ALL_AVAILABLE = "findAllAvailable";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleDao#findByRoleName(java.lang.String)
	 */
	@Override
	public Role findByRoleName(String name) {
		try {
			Assert.notNull(name);
			Role role = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ROLENAME), name);
			return role;
		} catch (Exception e) {
			logger.error(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLENAME)), e);
			throw new SystemException(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLENAME)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleDao#findByCode(java.lang.String)
	 */
	@Override
	public Role findByCode(String code) {
		try {
			Assert.notNull(code);
			Role role = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_CODE), code);
			return role;
		} catch (Exception e) {
			logger.error(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
			throw new SystemException(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleDao#findAllAvailable()
	 */
	@Override
	public List<Role> findAllAvailable() {
		try {
			List<Role> roleList = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_AVAILABLE));
			return roleList;
		} catch (Exception e) {
			logger.error(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
			throw new SystemException(String.format("查找角色出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
		}
	}

}
