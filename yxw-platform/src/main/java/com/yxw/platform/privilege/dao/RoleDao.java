package com.yxw.platform.privilege.dao;

import java.util.List;

import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.framework.mvc.dao.BaseDao;

public interface RoleDao extends BaseDao<Role, String> {
	public Role findByRoleName(String name);

	public Role findByCode(String code);

	public List<Role> findAllAvailable();
}
