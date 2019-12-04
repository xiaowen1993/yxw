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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.RoleResource;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.ResourceDao;
import com.yxw.platform.privilege.dao.RoleDao;
import com.yxw.platform.privilege.dao.RoleResourceDao;
import com.yxw.platform.privilege.service.RoleService;

/**
 * @Package: com.yxw.platform.privilege.service.impl
 * @ClassName: RoleServiceImpl
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
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleResourceDao roleResourceDao;
	
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<Role, String> getDao() {
		return roleDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleService#findByRoleName(java.lang.String)
	 */
	@Override
	public Role findByRoleName(String name) {
		return roleDao.findByRoleName(name);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleService#findByCode(java.lang.String)
	 */
	@Override
	public Role findByCode(String code) {
		return roleDao.findByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleService#findAllAvailable()
	 */
	@Override
	public List<Role> findAllAvailable() {
		return roleDao.findAllAvailable();
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleService#add(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RespBody add(String name, String code, Integer available, String memo, String resourceIds, String createrId) {
		Role tmpRole = this.findByRoleName(name);
		if (tmpRole != null) {
			return new RespBody(Status.ERROR, name + " 名称已存在");
		}

		tmpRole = this.findByCode(code);
		if (tmpRole != null) {
			return new RespBody(Status.ERROR, code + " 编码已存在");
		}

		Role role = new Role(name, code, memo, available);
		role.setCp(createrId);
		role.setCt(new Date());

		super.add(role);

		//保存角色拥有的资源
		if (org.apache.commons.lang3.StringUtils.isNotBlank(resourceIds)) {
			String roleId = role.getId();
			String[] resourceIdsArr = resourceIds.split(",");
			List<RoleResource> roleResourceList = new ArrayList<RoleResource>(resourceIdsArr.length);
			for (String resourceId : resourceIdsArr) {
				roleResourceList.add(new RoleResource(roleId, resourceId));
			}
			roleResourceDao.batchInsert(roleResourceList);

			List<Resource> resourceList = resourceDao.findByIds(Arrays.asList(resourceIdsArr));
			// roleResourceCache.updateRoleResource(roleId, resourceList);
			List<Object> params = new ArrayList<Object>();
			params.add(roleId);
			params.add(resourceList);
			serveComm.set(CacheType.ROLE_RESOURCE_CACHE, "updateRoleResource", params);
		}

		return new RespBody(Status.OK);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.RoleService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RespBody update(String id, String name, String code, Integer available, String memo, String resourceIds, String createrId) {
		Role tmpRole = this.findByRoleName(name);
		if (tmpRole != null && !StringUtils.equals(tmpRole.getId(), id)) {
			return new RespBody(Status.ERROR, name + " 名称已存在");
		}

		tmpRole = this.findByCode(code);
		if (tmpRole != null && !StringUtils.equals(tmpRole.getId(), id)) {
			return new RespBody(Status.ERROR, code + " 编码已存在");
		}

		Role role = super.findById(id);
		role.setName(name);
		role.setCode(code);
		role.setAvailable(available);
		role.setMemo(memo);
		role.setEp(createrId);
		role.setName(name);

		super.update(role);

		//先删除,再添加;
		roleResourceDao.deleteByRoleIds(Arrays.asList(id));
		// roleResourceCache.delRoleResource(id);
		List<Object> params = new ArrayList<Object>();
		params.add(new String[]{id});
		serveComm.delete(CacheType.ROLE_RESOURCE_CACHE, "delRoleResource", params);
		
		//保存角色拥有的资源
		if (org.apache.commons.lang3.StringUtils.isNotBlank(resourceIds)) {
			String[] resourceIdsArr = resourceIds.split(",");
			List<RoleResource> roleResourceList = new ArrayList<RoleResource>(resourceIdsArr.length);
			for (String resourceId : resourceIdsArr) {
				roleResourceList.add(new RoleResource(id, resourceId));
			}
			roleResourceDao.batchInsert(roleResourceList);

			List<Resource> resourceList = resourceDao.findByIds(Arrays.asList(resourceIdsArr));
			// roleResourceCache.updateRoleResource(id, resourceList);
			params.clear();
			params.add(id);
			params.add(resourceList);
			serveComm.set(CacheType.ROLE_RESOURCE_CACHE, "updateRoleResource", params);
		}

		return new RespBody(Status.OK);
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#batchDelete(java.util.List)
	 */
	@Override
	public void batchDelete(List<String> ids) {
		roleResourceDao.deleteByRoleIds(ids);
		super.batchDelete(ids);

		// roleResourceCache.delRoleResource(ids.toArray(new String[ids.size()]));
		List<Object> params = new ArrayList<Object>();
		params.add(ids.toArray(new String[ids.size()]));
		serveComm.delete(CacheType.ROLE_RESOURCE_CACHE, "delRoleResource", params);
	}

}
