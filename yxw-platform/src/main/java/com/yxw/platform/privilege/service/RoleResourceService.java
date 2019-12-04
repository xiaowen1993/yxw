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
package com.yxw.platform.privilege.service;

import java.util.List;

import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.RoleResource;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.privilege.service
 * @ClassName: RoleResourceService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RoleResourceService extends BaseService<RoleResource, String> {
	public List<Resource> findResourceByRoleId(String roleId);

	public List<Resource> findResourceByRoleIds(List<String> roleIds);

	public void deleteByRoleIds(List<String> roleIds);
}
