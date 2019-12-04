package com.yxw.platform.app.color.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.app.color.dao.AppColorDao;
import com.yxw.platform.app.color.service.AppColorService;

@Service(value = "colorService")
public class AppColorServiceImpl extends BaseServiceImpl<AppColor, String> implements AppColorService {

	private Logger logger = LoggerFactory.getLogger(AppColorService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Resource
	private AppColorDao dao;

	@Override
	protected BaseDao<AppColor, String> getDao() {
		return dao;
	}

	@Override
	public void saveAndCacheDatas(List<AppColor> entities) {
		// 先删除数据库数据
		dao.deleteAll();
		// 批量写入
		for (AppColor appColor : entities) {
			if (StringUtils.isBlank(appColor.getId())) {
				appColor.setId(PKGenerator.generateId());
			}
		}
		dao.batchInsert(entities);

		// 写入缓存
		List<Object> objs = new ArrayList<Object>();
		objs.add(entities);
		serveComm.set(CacheType.APP_COLOR_CACHE, "saveAppColors", objs);
	}

	@Override
	public boolean save(AppColor color) {
		boolean result = false;

		try {
			String id = color.getId();
			if (StringUtils.isNotBlank(id)) {
				dao.update(color);
			} else {
				id = dao.add(color);
				if (StringUtils.isBlank(id)) {
					logger.info("save color into db error.");
					return false;
				}
			}
			
			// 写入缓存
			List<Object> objs = new ArrayList<Object>();
			List<AppColor> list = new ArrayList<>();
			list.add(color);
			objs.add(list);
			serveComm.set(CacheType.APP_COLOR_CACHE, "AddOrUpdate", objs);
		} catch (Exception e) {
			logger.error("save appColor error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return result;
	}
	
	@Override
	public void deleteById(String id) {
		try {
			super.deleteById(id);
			List<Object> objs = new ArrayList<Object>();
			List<String> list = new ArrayList<>();
			list.add(id);
			objs.add(list);
			serveComm.delete(CacheType.APP_COLOR_CACHE, "delete", objs);
		} catch (Exception e) {
			logger.error("delete appColor error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
	}

}
