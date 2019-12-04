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

import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.RoleResource;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.RoleResourceDao;

/**
 * @Package: com.yxw.platform.privilege.dao.impl
 * @ClassName: RoleResourceDaoImpl
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
public class RoleResourceDaoImpl extends BaseDaoImpl<RoleResource, String> implements RoleResourceDao {
	private static Logger logger = LoggerFactory.getLogger(RoleResourceDaoImpl.class);

	private static final String SQLNAME_FIND_BY_ROLEID = "findResourceByRoleId";
	private static final String SQLNAME_FIND_BY_ROLEIDS = "findResourceByRoleIds";

	private static final String SQLNAME_DEL_BY_ROLEIDS = "deleteByRoleIds";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleResourceDao#findByRoleId(java.lang.String)
	 */
	@Override
	public List<Resource> findResourceByRoleId(String roleId) {
		try {
			Assert.notNull(roleId);
			List<Resource> resourceList = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_ROLEID), roleId);
			return resourceList;
		} catch (Exception e) {
			logger.error(String.format("查找角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEID)), e);
			throw new SystemException(String.format("查找角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEID)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleResourceDao#findResourceByRoleIds(java.util.List)
	 */
	@Override
	public List<Resource> findResourceByRoleIds(List<String> roleIds) {
		try {
			Assert.notNull(roleIds);
			List<Resource> resourceList = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_ROLEIDS), roleIds);
			return resourceList;
		} catch (Exception e) {
			logger.error(String.format("查找角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEIDS)), e);
			throw new SystemException(String.format("查找角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEIDS)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.RoleResourceDao#deleteByRoleIds(java.util.List)
	 */
	@Override
	public void deleteByRoleIds(List<String> roleIds) {
		try {
			sqlSession.delete(SQLNAME_DEL_BY_ROLEIDS, roleIds);
		} catch (Exception e) {
			logger.error(String.format("删除角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEIDS)), e);
			throw new SystemException(String.format("删除角色对应的资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ROLEIDS)), e);
		}

	}

}
