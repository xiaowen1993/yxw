package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.stats.entity.platform.ExtensionCount;

public interface ExtensionCountDao extends BaseDao<ExtensionCount, String> {

	public abstract List<ExtensionCount> findExtensionCountByDate(Map params);

}
