/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.app.location.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.app.biz.area.service.AreaService;
import com.yxw.app.biz.location.service.AppLocationService;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * APP定位 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2016年4月20日
 */
@Controller
@RequestMapping("/sys/app/location")
public class AppLocationController extends BizBaseController<AppLocation, String> {

	private Logger logger = LoggerFactory.getLogger(AppLocationController.class);

	@Autowired
	private AppLocationService appLocationService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private PlatformService platformService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseService<AppLocation, String> getService() {
		return appLocationService;
	}

	@RequestMapping("/view")
	public ModelAndView toView(ModelMap modelMap) {
		List<Platform> platforms = platformService.findAll();

		List<Area> areas = areaService.getCacheOneLevelAreas();

		for (Area area : areas) {
			area.setChildAreas(areaService.getCacheTwoLevelAreasByParentId(area.getId()));
		}

		List<AppLocation> appLocations = appLocationService.getCacheAll();
		logger.info(JSON.toJSONString(appLocations));
		// appCode:areaId -- 缓存

		Map<String, String> appLocationMap = new HashMap<String, String>();
		for (AppLocation appLocation : appLocations) {
			if (appLocation.getStatus() == 1) {
				appLocationMap.put(appLocation.getCityId(), appLocation.getName());
			}
		}

		modelMap.put("areas", areas);
		modelMap.put("platforms", platforms);
		modelMap.put("appLocationMap", appLocationMap);
		logger.info(JSON.toJSONString(appLocationMap));

		return new ModelAndView("/sys/app/location/view");
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveLocation")
	public Object saveLocation(String jsonAppLocations, HttpServletRequest request, HttpServletResponse response) {
		try {
			// logger.info("保存...AppLocations:{}", jsonAppLocations);

			JSONArray arrays = JSONArray.parseArray(jsonAppLocations);

			if (arrays.size() > 0) {
				List<AppLocation> appLocations = new ArrayList<>();

				for (int i = 0; i < arrays.size(); i++) {
					Object object = arrays.get(i);
					appLocations.addAll(JSON.parseArray(((JSONArray) JSONPath.eval(object, "$.checkedDatas")).toJSONString(), AppLocation.class));
				}

				appLocationService.deleteAll();
				appLocationService.batchInsert(appLocations);

				// 获取一级-省(all)
				List<Area> areas = areaService.getCacheOneLevelAreas();
				Map<String, Area> areaMap = Maps.uniqueIndex(areas, new Function<Area, String>() {

					@Override
					public String apply(Area area) {
						return area.getIdPath();
					}

				});

				Map<String, List<AppLocation>> locationMap = new HashMap<String, List<AppLocation>>();
				for (AppLocation location : appLocations) {
					if (location.getLevel() == 2) {
						String parentIdPath = location.getCityId().substring(0, location.getCityId().lastIndexOf("/"));

						// 补全信息
						location.setProvinceId(areaMap.get(parentIdPath).getId());
						location.setProvinceName(areaMap.get(parentIdPath).getName());

						String key = location.getPlatformCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(location.getProvinceId());

						if (locationMap.containsKey(key)) {
							List<AppLocation> list = locationMap.get(key);
							list.add(location);
						} else {
							List<AppLocation> list = new ArrayList<>();
							list.add(location);
							locationMap.put(key, list);
						}
					}
				}

				logger.info(JSON.toJSONString(locationMap));
				if (!CollectionUtils.isEmpty(locationMap)) {
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(locationMap);
					serveComm.set(CacheType.APP_LOCATION_CACHE, "set", parameters);
				}
			}

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存失败，请重试。");
		}
	}

}
