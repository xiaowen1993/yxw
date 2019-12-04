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
package com.yxw.easyhealth.biz.location.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.app.biz.area.service.AreaService;
import com.yxw.app.biz.location.service.AppLocationService;
import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.commons.entity.platform.area.Area;

/**
 * APP定位 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2016年4月20日
 */
@Controller
@RequestMapping("/easyhealth/gps")
public class GPSController {

	private Logger logger = LoggerFactory.getLogger(GPSController.class);

	@Autowired
	private AreaService areaService;

	@Autowired
	private AppLocationService appLocationService;

	@RequestMapping("/view")
	public ModelAndView toView(ModelMap modelMap) {
		// 返回一个Map Map<省Id, List<appLocation>> (appLocation + 省名)
		// 拿所有的省份
		List<Area> areas = areaService.getCacheOneLevelAreas();

		Map<String, AppLocation> appLocations = new HashMap<String, AppLocation>();
		for (Area area : areas) {
			AppLocation appLocation = new AppLocation();
			appLocation.setId(area.getIdPath());
			appLocation.setName(area.getShortName());
			appLocation.setLevel(1);
			appLocation.setPinyin(area.getPinyin());

			appLocations.put(area.getIdPath(), appLocation);
		}

		// 拿选中的市，往前拼回省份
		List<AppLocation> selectedAppLocations = appLocationService.getCacheAll();

		for (AppLocation appLocation : selectedAppLocations) {
			if (appLocation.getLevel() == 2 && appLocation.getStatus() == 1) {
				String parentIdPath = appLocation.getId().substring(0, appLocation.getId().lastIndexOf("/"));
				appLocations.get(parentIdPath).getChildAppLocations().add(appLocation);
			}
		}

		logger.info("appLocations.size: {}", appLocations.size());
		modelMap.put("appLocations", appLocations.values());

		return new ModelAndView("/easyhealth/biz/location/gpsView");
	}

}
