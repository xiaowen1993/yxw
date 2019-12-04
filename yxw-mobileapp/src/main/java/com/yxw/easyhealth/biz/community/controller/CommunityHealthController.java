package com.yxw.easyhealth.biz.community.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.easyhealth.biz.community.service.CommunityHealthCenterService;
import com.yxw.easyhealth.biz.community.service.CommunityOrganizationSchmagService;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

/**
 * 神康中心
 * 
 * @Package: com.yxw.mobileapp.biz.register.controller
 * @ClassName: HistoryRegDoctorController
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-10-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("easyhealth/communitycenter/communityHealth")
public class CommunityHealthController {
	private static Logger logger = LoggerFactory.getLogger(CommunityHealthController.class);
	@Autowired
	private CommunityOrganizationSchmagService communityOrganizationSchmagService;
	@Autowired
	private CommunityHealthCenterService communityHealthCenterService;

	@ResponseBody
	@RequestMapping("getAdministrativeRegion")
	public ModelAndView getAdministrativeRegion(ModelMap modelMap) {
		List<String> administrativeRegionList = null;
		try {
			//administrativeRegionList = communityHealthCenterService.findGroupByAdministrativeRegion();
		} catch (Exception e) {
			logger.error("加载社区中心分区信息。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}
		modelMap.put("administrativeRegionList", administrativeRegionList);

		return new ModelAndView("/easyhealth/biz/community/administrativeRegionList");
	}

	@RequestMapping("/getCommunityHealthByAR")
	public ModelAndView getCommunityHealthByAR(ModelMap modelMap, HttpServletRequest request) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		String administrativeRegion = request.getParameter("administrativeRegion");
		if (!StringUtils.isBlank(administrativeRegion)) {
			params.put("administrativeRegion", administrativeRegion);
		}
		String organizationName = request.getParameter("organizationName");
		if (!StringUtils.isBlank(organizationName)) {
			params.put("organizationName", organizationName);
			modelMap.put("organizationName", organizationName);
		}

		List<CommunityHealthCenter> communityHealthCenterList = communityHealthCenterService.findByAdministrativeRegion(params);

		modelMap.put("communityHealthCenterList", communityHealthCenterList);
		modelMap.put("administrativeRegion", administrativeRegion);

		modelMap.putAll(params);

		return new ModelAndView("/easyhealth/biz/community/communityHealthCenterList");
	}

	@RequestMapping("/getCommunityHealthOnlyOneById")
	public ModelAndView getCommunityHealthOnlyOneById(ModelMap modelMap, HttpServletRequest request) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> paramsOrg = new HashMap<String, Object>();
		String communityId = request.getParameter("communityId");
		if (!StringUtils.isBlank(communityId)) {
			params.put("id", communityId);
			paramsOrg.put("organizationId", communityId);
		}
		CommunityHealthCenter communityHealthCenter = communityHealthCenterService.findCommunityHealthCenterById(params);
		modelMap.put("communityHealthCenter", communityHealthCenter);
		modelMap.put("communityId", communityId);

		List<CommunityOrganizationSchmag> organizationSchmagsList = communityOrganizationSchmagService.findOrganizaionByCondition(paramsOrg);

		Map<String, List<CommunityOrganizationSchmag>> organizationMap = new HashMap<String, List<CommunityOrganizationSchmag>>();

		for (CommunityOrganizationSchmag item : organizationSchmagsList) {
			List<CommunityOrganizationSchmag> imporEntityList = organizationMap.get(item.getWeek());

			if (imporEntityList == null) {
				imporEntityList = new ArrayList<CommunityOrganizationSchmag>();
			}

			imporEntityList.add(item);
			organizationMap.put(item.getWeek(), imporEntityList);
		}
		for (int i = 1; i <= 7; i++) {
			modelMap.put("organizationSchmagsList" + i, organizationMap.get(i + ""));
		}

		modelMap.putAll(params);

		return new ModelAndView("/easyhealth/biz/community/communityHealthCenterDetail");
	}

	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping("/getOrganizationSchmagByWeek")
	public Object getOrganizationSchmagByWeek(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		String week = request.getParameter("week");
		String indexSizeStr = request.getParameter("indexSize");
		int indexSize = 1;
		int endSize = 20;
		if (!StringUtils.isBlank(indexSizeStr)) {
			indexSize = Integer.valueOf(indexSizeStr);
			endSize = endSize * indexSize;
		}
		if (StringUtils.isBlank(week)) {
			Calendar calendar = Calendar.getInstance();
			logger.info(calendar.get(Calendar.DAY_OF_WEEK) + "");
			Integer nowWeek = calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
			week = nowWeek + "";
		}
		params.put("week", week);
		modelMap.put("week", week);
		modelMap.put("indexSize", indexSize);

		try {
			List<CommunityOrganizationSchmag> organizationSchmagsList = communityOrganizationSchmagService.findOrganizaionByCondition(params);
			boolean indexSizeAdd = true;
			if (organizationSchmagsList.size() > 0) {
				indexSizeAdd = organizationSchmagsList.size() < endSize ? false : true;
				endSize = organizationSchmagsList.size() < endSize ? organizationSchmagsList.size() : endSize;

				organizationSchmagsList = organizationSchmagsList.subList(0, endSize);
			}
			modelMap.put("indexSizeAdd", indexSizeAdd);
			modelMap.put("organizationSchmagsList", organizationSchmagsList);
			return new RespBody(Status.OK, modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

	@RequestMapping("/getCommunityOrganizaWeekDetail")
	public ModelAndView getCommunityOrganizaWeekDetail(ModelMap modelMap, HttpServletRequest request) {

		return new ModelAndView("/easyhealth/biz/community/communityOrganizaWeekDetail");
	}

	@RequestMapping("/getCommunitySoushuo")
	public ModelAndView getCommunitySoushuo(ModelMap modelMap, HttpServletRequest request) {

		return new ModelAndView("/easyhealth/biz/community/communitySoushuo");
	}

	@ResponseBody
	@RequestMapping("/getCommunityHealthByParams")
	public Object getCommunityHealthByParams(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();

		String organizationName = request.getParameter("organizationName");
		if (!StringUtils.isBlank(organizationName)) {
			params.put("organizationName", organizationName);
			modelMap.put("organizationName", organizationName);
		}

		try {
			List<CommunityHealthCenter> communityHealthCenterList = communityHealthCenterService.findByAdministrativeRegion(params);
			modelMap.put("communityHealthCenterList", communityHealthCenterList);
			return new RespBody(Status.OK, modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

}
