/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年12月11日</p>
 *  <p> Created by homer.yang</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.vaccinate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.mobile.biz.vaccinate.Vaccinate;
import com.yxw.easyhealth.biz.vaccinate.service.VaccinateService;

/**
 * 疫苗接种
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月11日
 */
@Controller
@RequestMapping("/easyhealth/vaccinate")
public class VaccinateController {
	private static Logger logger = LoggerFactory.getLogger(VaccinateController.class);

	@Autowired
	private VaccinateService vaccinateService;

	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/vaccinate/vaccinateIndex");
	}

	@RequestMapping(value = "region")
	public ModelAndView region(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/vaccinate/regionList");
	}

	@RequestMapping(value = "schedule")
	public ModelAndView schedule(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/vaccinate/vaccinateSchedule");
	}

	@RequestMapping(value = "clinic")
	public ModelAndView clinic(HttpServletRequest request, HttpServletResponse response, String regionName, ModelMap modelMap) {
		if (StringUtils.isNoneBlank(regionName)) {
			modelMap.put("regionName", regionName);

			List<Vaccinate> vaccinateClinics = vaccinateService.findVaccinateClinicsByRegionName(regionName);
			if (vaccinateClinics != null) {
				modelMap.put("vaccinateClinics", vaccinateClinics);
			}
		}

		return new ModelAndView("easyhealth/biz/vaccinate/clinicList", modelMap);
	}

}
