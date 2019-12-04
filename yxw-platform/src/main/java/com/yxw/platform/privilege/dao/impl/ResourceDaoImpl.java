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
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.ResourceDao;

/**
 * @Package: com.yxw.platform.user.dao.impl
 * @ClassName: ResourceDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource, String> implements ResourceDao {
	private static Logger logger = LoggerFactory.getLogger(OrganizationDaoImpl.class);

	private static final String SQLNAME_FIND_BY_NAME = "findByName";

	private static final String SQLNAME_FIND_BY_CODE = "findByCode";

	private static final String SQLNAME_FIND_BY_ABSTR = "findByAbstr";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.OrganizationDao#findByName(java.lang.String)
	 */
	@Override
	public List<Resource> findByName(String name) {
		try {
			Assert.notNull(name);
			List<Resource> resourceList = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_NAME), name);
			return resourceList;
		} catch (Exception e) {
			logger.error(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
			throw new SystemException(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.OrganizationDao#findByCode(java.lang.String)
	 */
	@Override
	public Resource findByCode(String code) {
		try {
			Assert.notNull(code);
			Resource resource = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_CODE), code);
			return resource;
		} catch (Exception e) {
			logger.error(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
			throw new SystemException(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.ResourceDao#findByAbstr(java.lang.String)
	 */
	@Override
	public Resource findByAbstr(String abstr) {
		try {
			Assert.notNull(abstr);
			Resource resource = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ABSTR), abstr);
			return resource;
		} catch (Exception e) {
			logger.error(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ABSTR)), e);
			throw new SystemException(String.format("查找资源出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ABSTR)), e);
		}
	}

}
