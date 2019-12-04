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

import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.UserRole;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.user.dao
 * @ClassName: UserRoleDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface UserRoleDao extends BaseDao<UserRole, String> {
	/**
	 * 以用户ID查找角色
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleByUserId(String userId);

	public void deleteByUserId(String userId);
}
