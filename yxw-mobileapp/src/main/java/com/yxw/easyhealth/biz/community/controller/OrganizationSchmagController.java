package com.yxw.easyhealth.biz.community.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.easyhealth.biz.community.service.CommunityHealthCenterService;
import com.yxw.easyhealth.biz.community.service.CommunityOrganizationSchmagService;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

/**
 * 神康中心
 * @Package: com.yxw.mobileapp.biz.register.controller
 * @ClassName: HistoryRegDoctorController
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-10-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("sysSchmag/communitycenter/organizationSchmag")
public class OrganizationSchmagController {
	private static Logger logger = LoggerFactory.getLogger(OrganizationSchmagController.class);

	@Autowired
	private CommunityOrganizationSchmagService communityOrganizationSchmagService;
	@Autowired
	private CommunityHealthCenterService communityHealthCenterService;

	@RequestMapping("/getOrganizationSchmagByCommunitId")
	public ModelAndView getOrganizationSchmagByCommunitId(ModelMap modelMap, HttpServletRequest request) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		String organizationId = request.getParameter("organizationId");
		if (!StringUtils.isBlank(organizationId)) {
			params.put("organizationId", organizationId);
			modelMap.put("organizationId", organizationId);
		}
		String organizationName = request.getParameter("organizationName");
		if (!StringUtils.isBlank(organizationName)) {
			modelMap.put("organizationName", organizationName);
		}
		String administrativeRegionQuert = request.getParameter("administrativeRegionQuert");
		if (!StringUtils.isBlank(administrativeRegionQuert)) {
			modelMap.put("administrativeRegionQuert", administrativeRegionQuert);
		}
		String organizationNameQuert = request.getParameter("organizationNameQuert");
		if (!StringUtils.isBlank(organizationNameQuert)) {
			modelMap.put("organizationNameQuert", organizationNameQuert);
		}

		List<CommunityOrganizationSchmag> organizationSchmagsList = communityOrganizationSchmagService.findOrganizaionByCondition(params);
		if (organizationSchmagsList.size() > 0) {
			modelMap.put("organizationSchmagsList", organizationSchmagsList);
		}
		modelMap.putAll(params);

		return new ModelAndView("//easyhealth/biz/community/bsmag/communitySchedulingManagement");
	}

	@RequestMapping("/getDialogAddDuty")
	public ModelAndView getDialogAddDuty(ModelMap modelMap, HttpServletRequest request) {

		return new ModelAndView("/easyhealth/biz/community/bsmag/dialog-addDuty");
	}

	/**
	 * 异步删除 社康排班医生信息
	 * @param medicalCardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteOrganization")
	public Object deleteOrganization(CommunityOrganizationSchmag organizationSchmag) {
		try {
			String id = organizationSchmag.getId();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			Map<String, Object> resultMap = communityOrganizationSchmagService.deleteOrganizaion(params);
			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

	/**
	 * 异步添加 社康排班医生信息
	 * @param medicalCardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addOrganization")
	public Object addOrganization(CommunityOrganizationSchmag organizationSchmag) {
		try {
			//			organizationSchmag.setOrganizationId("5ea08424-7939-11e5-9319-94de8047");
			//			organizationSchmag.setOrganizationName("深圳华侨城医院香山社区健康服务中心");
			//			organizationSchmag.setWeek("1");
			//			organizationSchmag.setTimeSlot("1");
			//			organizationSchmag.setDoctorName("刘德华");
			//			organizationSchmag.setPosition("心理医生");
			//			organizationSchmag.setSpecialty("心理科");
			//			organizationSchmag.setHospitalName("广州第一医院");
			Map<String, Object> resultMap = null;
			if (!StringUtils.isBlank(organizationSchmag.getId())) {
				resultMap = communityOrganizationSchmagService.updateOrganizaion(organizationSchmag);
			} else {
				resultMap = communityOrganizationSchmagService.addOrganizaion(organizationSchmag);
			}

			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "");
		}
	}

	@RequestMapping("/queryCcommunityHealth")
	public ModelAndView queryCcommunityHealth(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "20") int pageSize, ModelMap modelMap, HttpServletRequest request) {
		logger.info("社康中心分页查询,pageNum=[{}],pageSize=[{}]", new Object[] { pageNum, pageSize });
		HashMap<String, Object> params = new HashMap<String, Object>();
		String notQuery = request.getParameter("notQuery");
		boolean query = true;
		if (notQuery != null && "true".equals(notQuery)) {
			query = false;
		}
		String type = request.getParameter("type");
		params.put("type", type);

		String administrativeRegion = request.getParameter("administrativeRegion");
		if (!StringUtils.isBlank(administrativeRegion)) {
			params.put("administrativeRegion", administrativeRegion);
			modelMap.put("administrativeRegion", administrativeRegion);
		}
		String organizationNameQuert = request.getParameter("organizationNameQuert");
		if (!StringUtils.isBlank(organizationNameQuert)) {
			params.put("organizationName", organizationNameQuert);
			modelMap.put("organizationNameQuert", organizationNameQuert);
		}

		try {
			List<CommunityHealthCenter> communityHealthCenterList = communityHealthCenterService.findByAdministrativeRegion(params);
			List<String> administrativeRegionList = communityHealthCenterService.findGroupByAdministrativeRegion();

			modelMap.put("administrativeRegionList", administrativeRegionList);
			if (communityHealthCenterList.size() > 0) {
				//TODO 暂时没处理
				Page<CommunityHealthCenter> pager = new Page<CommunityHealthCenter>(pageNum, pageSize);

				modelMap.put("pager", pager);
			}

			modelMap.putAll(params);
		} catch (Exception e) {
			logger.error("加载社区中心分区信息。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new ModelAndView("/easyhealth/biz/community/bsmag/communityQuery");
	}
}
