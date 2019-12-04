package com.yxw.platform.privilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.ResourceDao;
import com.yxw.platform.privilege.service.ResourceService;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, String> implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<Resource, String> getDao() {
		return resourceDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.ResourceService#findByName(java.lang.String)
	 */
	@Override
	public List<Resource> findByName(String name) {
		return resourceDao.findByName(name);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.ResourceService#findByCode(java.lang.String)
	 */
	@Override
	public Resource findByCode(String code) {
		return resourceDao.findByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.ResourceService#findByAbstr(java.lang.String)
	 */
	@Override
	public Resource findByAbstr(String abstr) {
		return resourceDao.findByAbstr(abstr);
	}

}
