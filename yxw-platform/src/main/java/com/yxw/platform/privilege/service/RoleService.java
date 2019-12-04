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

import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.privilege.service
 * @ClassName: RoleService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RoleService extends BaseService<Role, String> {
	public Role findByRoleName(String roleName);

	public Role findByCode(String code);

	public List<Role> findAllAvailable();

	public RespBody add(String name, String code, Integer available, String memo, String resourceIds, String createrId);

	public RespBody update(String id, String name, String code, Integer available, String memo, String resourceIds, String createrId);
}
