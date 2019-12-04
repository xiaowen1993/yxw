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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.OrganizationDao;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.service.OrganizationService;
import com.yxw.platform.privilege.vo.OrganizationVo;

/**
 * @Package: com.yxw.platform.privilege.service.impl
 * @ClassName: OrganizationServiceImpl
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
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, String> implements OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<Organization, String> getDao() {
		return organizationDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.OrganizationService#findByName(java.lang.String)
	 */
	@Override
	public List<Organization> findByName(String name) {
		return organizationDao.findByName(name);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.OrganizationService#findByCode(java.lang.String)
	 */
	@Override
	public Organization findByCode(String code) {
		return organizationDao.findByCode(code);
	}

	public PageInfo<OrganizationVo> findListVoByPage(Map<String, Object> parms, Page<OrganizationVo> page) {
		return organizationDao.findListVoByPage(parms, page);
	}

}
