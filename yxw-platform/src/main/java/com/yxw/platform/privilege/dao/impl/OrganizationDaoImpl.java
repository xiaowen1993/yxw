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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.OrganizationDao;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.vo.OrganizationVo;

/**
 * @Package: com.yxw.platform.user.dao.impl
 * @ClassName: OrganizationDaoImpl
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
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, String> implements OrganizationDao {
	private static Logger logger = LoggerFactory.getLogger(OrganizationDaoImpl.class);

	private static final String SQLNAME_FIND_BY_NAME = "findByName";

	private static final String SQLNAME_FIND_BY_CODE = "findByCode";

	private final static String SQLNAME_FIND_LIST_BY_PAGE = "findListByPage";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.OrganizationDao#findByName(java.lang.String)
	 */
	@Override
	public List<Organization> findByName(String name) {
		try {
			Assert.notNull(name);
			List<Organization> organizationList = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_NAME), name);
			return organizationList;
		} catch (Exception e) {
			logger.error(String.format("查找组织出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
			throw new SystemException(String.format("查找组织出错！语句：%s", getSqlName(SQLNAME_FIND_BY_NAME)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.OrganizationDao#findByCode(java.lang.String)
	 */
	@Override
	public Organization findByCode(String code) {
		try {
			Assert.notNull(code);
			Organization organization = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_CODE), code);
			return organization;
		} catch (Exception e) {
			logger.error(String.format("查找组织出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
			throw new SystemException(String.format("查找组织出错！语句：%s", getSqlName(SQLNAME_FIND_BY_CODE)), e);
		}
	}

	public PageInfo<OrganizationVo> findListVoByPage(Map<String, Object> params, Page<OrganizationVo> page) {
		try {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<OrganizationVo> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PAGE), params);
			return new PageInfo<OrganizationVo>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
		}
	}
}
