package com.yxw.platform.app.color.service;

import java.util.List;

import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.framework.mvc.service.BaseService;

public interface AppColorService extends BaseService<AppColor, String> {
	/**
	 * 保存并缓存数据
	 * @param entities
	 */
	public void saveAndCacheDatas(List<AppColor> entities);
	
	public boolean save(AppColor color);
	
}
