package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.ExtensionCountDao;
import com.yxw.stats.entity.platform.ExtensionCount;

@Service(value = "extensionCountService")
public class ExtensionCountServiceImpl extends BaseServiceImpl<ExtensionCount, String> implements ExtensionCountService {

	private static Logger logger = LoggerFactory.getLogger(ExtensionCountServiceImpl.class);

	@Autowired
	private ExtensionCountDao extensionCountDao;

	@Override
	protected BaseDao<ExtensionCount, String> getDao() {
		// TODO Auto-generated method stub
		return extensionCountDao;
	}

	public List<ExtensionCount> findExtensionCountByDate(Map params) {
		return extensionCountDao.findExtensionCountByDate(params);
	}
}
