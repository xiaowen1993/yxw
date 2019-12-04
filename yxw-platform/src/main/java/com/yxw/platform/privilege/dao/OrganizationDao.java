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
package com.yxw.platform.privilege.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.vo.OrganizationVo;

/**
 * @Package: com.yxw.platform.user.dao
 * @ClassName: OrganizationDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface OrganizationDao extends BaseDao<Organization, String> {
	/**
	 * 根据名称找组织
	 * @param name 匹配方式为 like
	 * @return
	 */
	public List<Organization> findByName(String name);

	public Organization findByCode(String code);

	/**
	 * 自定义分页
	 * @param params
	 * @param page
	 * @return
	 */
	public PageInfo<OrganizationVo> findListVoByPage(Map<String, Object> params, Page<OrganizationVo> page);
}
