package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.ExtensionCount;

public interface ExtensionCountService extends BaseService<ExtensionCount, String> {

	public abstract List<ExtensionCount> findExtensionCountByDate(Map params);
}
