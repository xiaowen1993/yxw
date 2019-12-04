/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Ordering;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * @Package: com.yxw.easyhealth.biz.user
 * @ClassName: LoginController
 * @Statement: <p>
 *             健康易首页
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth")
public class EhIndexController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(EhIndexController.class);

	// private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	// private HospitalAndOptionCache hospitalAndOptionCache = SpringContextHolder.getBean(HospitalAndOptionCache.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String appCode = getAppCode(request);
		ModelAndView view = new ModelAndView("easyhealth/biz/index/" + appCode + "Index");

		//1	首页导航	IndexNav
		//2	首页	Index
		//3	智能搜索	IntelligentSearch
		//4	分级诊疗	SubDoctor
		//5	个人中心	MyCenter

		// IndexNav + Index
		// List<AppOptionalModule> appOptionalModules = appOptionalModuleService.getCacheAppOptionalModulesHasAppOptionals();

		List<Object> params = new ArrayList<>();
		List<String> strList = new ArrayList<>();
		strList.add("Index");
		strList.add("IndexNav");
		params.add(strList);
		List<Object> result = serveComm.get(CacheType.APP_OPTIONAL_CACHE, "getOptionsByModules", params);
		String source = JSON.toJSONString(result.get(0));
		@SuppressWarnings("unchecked")
		Map<String, JSONArray> optionsMap = JSON.parseObject(source, Map.class);

		List<AppOptional> indexList = new ArrayList<>();
		Map<String, AppOptional> indexNavMap = new HashMap<>();

		for (Entry<String, JSONArray> entry : optionsMap.entrySet()) {
			List<AppOptional> options = entry.getValue().toJavaList(AppOptional.class);
			for (AppOptional optional : options) {
				if (optional.getVisible() == 1) {
					if (optional.getAppOptionalModule().getId().equals("1")) {
						indexNavMap.put(optional.getShowSort().toString(), optional);
					} else if (optional.getAppOptionalModule().getId().equals("2")) {
						indexList.add(optional);
					}
				}
			}
		}

		view.addObject("indexList", indexList);
		view.addObject("indexNavMap", indexNavMap);
		// logger.info(indexNavMap.toString());

		// 1启动页;2首页轮播
		List<Carrieroperator> carrieroperators2 = getCarrieroperatorFromCache("2");
		view.addObject("carrieroperators2", carrieroperators2);

		String menuCode = request.getParameter("menuCode");
		if (StringUtils.isBlank(menuCode)) {
			menuCode = "0";
		}
		view.addObject("menuCode", menuCode);

		// websocet配置
		initWebSocket(view);

		return view;
	}

	private void initWebSocket(ModelAndView view) {
		view.addObject("enableWebSocket", SystemConfig.getInteger("enableWebSocket", 0));
		view.addObject("commonWebSocketPath", SystemConfig.getStringValue("commonWebSocketPath", ""));
		view.addObject("sockJSWebSocketPath", SystemConfig.getStringValue("sockJSWebSocketPath", ""));
	}

	private List<Carrieroperator> getCarrieroperatorFromCache(String position) {
		List<Carrieroperator> resultList = null;
		List<Object> params = new ArrayList<Object>();
		params.add(position);
		List<Object> results = serveComm.get(CacheType.APP_CARRIEROPERATOR_CACHE, "getByOperationPosition", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<Carrieroperator>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, Carrieroperator.class));
		}

		return resultList;
	}

	/**
	 * 分诊医疗
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "listIndex")
	public ModelAndView listIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/index/listIndex");
	}

	/**
	 * 搜索首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "searchIndex")
	public ModelAndView searchIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("easyhealth/biz/index/searchIndex");

		/*		String areaCode = request.getParameter(BizConstant.AREACODE);
				if (StringUtils.isBlank(areaCode)) {
					areaCode = getAreaCode(request);
				}
				view.addObject(BizConstant.AREACODE, areaCode);*/

		String menuCode = request.getParameter("menuCode");
		if (StringUtils.isBlank(menuCode)) {
			menuCode = "1";
		}
		view.addObject("menuCode", menuCode);

		// websocet配置
		initWebSocket(view);

		return view;
	}

	/**
	 * 个人中心-首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userCenterIndex")
	public ModelAndView userCenterIndex(HttpServletRequest request, HttpServletResponse response) {
		String appCode = getAppCode(request);
		ModelAndView view = new ModelAndView("easyhealth/biz/index/" + appCode + "UserCenterIndex");

		String menuCode = request.getParameter("menuCode");
		if (StringUtils.isBlank(menuCode)) {
			menuCode = "3";
		}
		view.addObject("menuCode", menuCode);

		// websocet配置
		initWebSocket(view);

		return view;
	}

	/**
	 * 健康档案
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "healthList")
	public ModelAndView healthList(CommonParamsVo vo, HttpServletRequest request, HttpServletResponse response) {
		/*String forwardUrl = "/easyhealth/healthList";
		ModelAndView view = super.checkUserInfoPerfect(vo, request, forwardUrl);
		if (view != null) {
			return view;
		}

		String checkSessionUserKey = BizConstant.COMMON_SESSION_PARAMS + "_" + vo.getOpenId();
		CommonParamsVo tempVo = (CommonParamsVo) request.getSession().getAttribute(checkSessionUserKey);
		if (tempVo != null) {
			vo = tempVo;
			request.getSession().removeAttribute(checkSessionUserKey);
		}*/

		ModelAndView view = new ModelAndView("easyhealth/biz/static/healthList");
		return view;
	}

	/**
	 * 就诊评价
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "vote")
	public ModelAndView vote(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/vote");
	}

	/**
	 * 就医宝典beta
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "guideDetail")
	public ModelAndView guideDetail(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/guideDetail");
	}

	/**
	 * 职能候诊
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "waiting")
	public ModelAndView waiting(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/waiting");
	}

	/**
	 * 个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userCenterMyInfo")
	public ModelAndView userCenterMyInfo(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/userCenterMyInfo");
	}

	@RequestMapping(value = "building")
	public ModelAndView toBuilding(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/common/building");
	}

	/**
	 * 福康宝
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "fukangbao")
	public ModelAndView goToFuKangBao(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/fuKangBao");
	}

	/**
	 * 福康宝
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "fuKangBaoBuilding")
	public ModelAndView fuKangBaoBuilding(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/static/fuKangBaoBuilding");
	}

	/**
	 * 就医指南
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toHospitalInfo")
	public ModelAndView toHospitalInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("easyhealth/biz/static/hospitalInfo");
		List<Hospital> resultList = new ArrayList<Hospital>();

		List<JSONObject> hospitalsWithCloudFunction = new ArrayList<JSONObject>();
		;
		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(getAppCode(request));
		requestGetParameters.add(BizConstant.OPTION_GUIDE);
		requestGetParameters.add(getAreaCode(request));
		//				List<JSONObject> hospitalsWithCloudFunction = hospitalAndOptionCache.getHospitalJsonByOption(BizConstant.OPTION_GUIDE, "ShenZheng");
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalJsonByOption", requestGetParameters);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitalsWithCloudFunction = JSON.parseArray(source, JSONObject.class);
		}

		List<Hospital> hospitalsWithBasicInfo = new ArrayList<Hospital>();
		;
		List<Object> parameters = new ArrayList<Object>();
		//						List<Hospital> hospitalsWithBasicInfo = hospitalCache.queryAllHospBaseInfo();
		List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAllHospBaseInfo", parameters);
		if (CollectionUtils.isNotEmpty(result)) {
			String source = JSON.toJSONString(result);
			hospitalsWithBasicInfo = JSON.parseArray(source, Hospital.class);
		}

		for (Hospital hospital : hospitalsWithBasicInfo) {
			for (JSONObject jsonObject : hospitalsWithCloudFunction) {
				if (jsonObject != null && jsonObject.getString("hospitalId") != null) {
					if (jsonObject.getString("hospitalId").equals(hospital.getId())) {
						resultList.add(hospital);
						break;
					}
				}
			}
		}
		modelAndView.addObject("hospitals", resultList);

		return modelAndView;
	}

	/**
	 * 交通指南
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toTrafficInfo")
	public ModelAndView toTrafficInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("easyhealth/biz/static/trafficInfo");
		List<Hospital> resultList = new ArrayList<Hospital>();

		List<JSONObject> hospitalsWithCloudFunction = new ArrayList<JSONObject>();

		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(request.getParameter("appCode"));
		requestGetParameters.add(BizConstant.OPTION_TRAFFIC);
		//requestGetParameters.add("ShenZheng");
		requestGetParameters.add(getAreaCode(request));
		//		List<JSONObject> hospitalsWithCloudFunction = hospitalAndOptionCache.getHospitalJsonByOption(BizConstant.OPTION_TRAFFIC, "ShenZheng");
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalJsonByOption", requestGetParameters);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitalsWithCloudFunction = JSON.parseArray(source, JSONObject.class);
		}

		List<Hospital> hospitalsWithBasicInfo = new ArrayList<Hospital>();

		List<Object> parameters = new ArrayList<Object>();
		//						List<Hospital> hospitalsWithBasicInfo = hospitalCache.queryAllHospBaseInfo();
		List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAllHospBaseInfo", parameters);
		if (CollectionUtils.isNotEmpty(result)) {
			String source = JSON.toJSONString(result);
			hospitalsWithBasicInfo = JSON.parseArray(source, Hospital.class);
		}

		for (Hospital hospital : hospitalsWithBasicInfo) {
			for (JSONObject jsonObject : hospitalsWithCloudFunction) {
				if (jsonObject != null && jsonObject.getString("hospitalId") != null) {
					if (jsonObject.getString("hospitalId").equals(hospital.getId())) {
						resultList.add(hospital);
						break;
					}
				}
			}
		}

		Ordering<Hospital> ordering = new Ordering<Hospital>() {
			@Override
			public int compare(Hospital left, Hospital right) {
				return ( left.getSortIndex() != null && right.getSortIndex() != null ) ? ( left.getSortIndex() - right.getSortIndex() ) : 1;
			}
		};

		resultList = ordering.sortedCopy(resultList);

		modelAndView.addObject("hospitals", resultList);

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "index/changeAreaCode")
	public RespBody toHospitalInfo(HttpServletRequest request, HttpServletResponse response, String openId, String areaCode) {
		setAreaCode(request, areaCode);

		addAreaCodeCookie(EasyHealthConstant.SESSION_AREA_CODE, areaCode, request, response);

		return new RespBody(Status.OK);
	}

	public void addAreaCodeCookie(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response) {
		try {

			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookie.setDomain(request.getServerName()); // 请用自己的域
			cookie.setMaxAge(24 * 60 * 60 * EasyHealthConstant.COOKIES_USER_MAX_AGE); // cookie的有效期
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("add areaCode cookie is exception.", e);
		}
	}
}
