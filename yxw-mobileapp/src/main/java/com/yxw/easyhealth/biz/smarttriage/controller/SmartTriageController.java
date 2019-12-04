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
package com.yxw.easyhealth.biz.smarttriage.controller;

import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.mobile.biz.smarttriage.Disease;
import com.yxw.commons.entity.mobile.biz.smarttriage.Symptom;
import com.yxw.easyhealth.biz.smarttriage.service.DiseaseService;
import com.yxw.easyhealth.biz.smarttriage.service.SymptomService;
import com.yxw.easyhealth.biz.smarttriage.utils.PartUtils;

/**
 * 
 * @Package: com.yxw.easyhealth.biz.smarttriage.controller
 * @ClassName: SmartTriageController
 * @Statement: <p>
 *             智能分诊
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/smartTriage")
public class SmartTriageController {
	private static Logger logger = LoggerFactory.getLogger(SmartTriageController.class);

	private static final String STATUS = "status";

	private static final String MESSAGE = "message";

	@Autowired
	private SymptomService symptomService;

	@Autowired
	private DiseaseService diseaseService;

	/**
	 * 后台首页 症状管理
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/symptomBack")
	public ModelAndView backHome(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "5") int pageSize, ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", request.getAttribute("search"));
		// PageInfo<Symptom> symptoms = symptomService.findListByPage(params,
		// new Page<Symptom>(pageNum, pageSize));
		List<Symptom> symptoms = symptomService.findAll();
		modelMap.put("symptoms", symptoms);
		modelMap.put("partData", PartUtils.partMap);
		return new ModelAndView("symptom/symptomBack");
	}

	/**
	 * 症状添加跳转
	 * 
	 * @param symptom
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addSymptom")
	public ModelAndView addSymptom(Symptom symptom, ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("partData", PartUtils.partMap);
		return new ModelAndView("symptom/symptomEdit");
	}

	/**
	 * 症状更新跳转
	 * 
	 * @param id
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateSymptom")
	public ModelAndView updateSymptom(String id, ModelMap modelMap, HttpServletRequest request) {
		Symptom symptom = symptomService.findById(id);
		modelMap.put("symptom", symptom);
		modelMap.put("partData", PartUtils.partMap);
		return new ModelAndView("symptom/symptomEdit");
	}

	/**
	 * 症状添加/更新
	 * 
	 * @param symptom
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSymptom")
	public Object saveSymptom(Symptom symptom, ModelMap modelMap, HttpServletRequest request) {
		logger.info("症状:{}", JSON.toJSONString(symptom));
		Map<String, Object> resMap = new HashMap<String, Object>();
		boolean flag = symptomService.checkSymtomName(symptom);
		if (flag) {
			symptomService.save(symptom);
			resMap.put(STATUS, true);
		} else {
			resMap.put(STATUS, false);
			resMap.put(MESSAGE, "症状名称已经存在");
		}
		return resMap;
	}

	/**
	 * 症状显示切换
	 * 
	 * @param symptom
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showHideSwitch")
	public Object showHideSwitch(Symptom symptom, ModelMap modelMap, HttpServletRequest request) {
		logger.info("症状显示切换:{}", JSON.toJSONString(symptom));
		Map<String, Object> resMap = new HashMap<String, Object>();
		symptomService.save(symptom);
		resMap.put(STATUS, true);
		return resMap;
	}

	/**
	 * 后台首页 疾病管理
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/diseaseBack")
	public ModelAndView diseaseBack(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "5") int pageSize, ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", request.getAttribute("search"));
		List<Disease> diseases = diseaseService.findAll();
		modelMap.put("diseases", diseases);
		return new ModelAndView("symptom/diseaseBack");
	}

	/**
	 * 疾病添加跳转
	 * 
	 * @param disease
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addDisease")
	public ModelAndView addDisease(Disease disease, ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("disease", disease);
		List<Symptom> symptoms = symptomService.findAll();
		modelMap.put("symptomsUnCheck", symptoms);
		return new ModelAndView("symptom/diseaseEdit");
	}

	/**
	 * 疾病更新跳转
	 * 
	 * @param id
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateDisease")
	public ModelAndView updateDisease(String id, ModelMap modelMap, HttpServletRequest request) {

		// 查询所有显示症状
		List<Symptom> symptomsAll = symptomService.findAll();

		// 查询当前疾病关联的状态
		List<Symptom> symptomsCheck = symptomService.findSymptomByDiseaseId(id);

		List<Symptom> symptomsUnCheck = new ArrayList<Symptom>();
		// 取出当前疾病未关联的状态
		if (CollectionUtils.isEmpty(symptomsCheck)) {
			symptomsUnCheck = symptomsAll;
		} else {
			if (!CollectionUtils.isEmpty(symptomsAll)) {
				Map<String, Symptom> checkMap = new HashMap<String, Symptom>();
				for (Symptom symptom : symptomsCheck) {
					checkMap.put(symptom.getId(), symptom);
				}
				for (Symptom all : symptomsAll) {
					if (!checkMap.containsKey(all.getId())) {
						symptomsUnCheck.add(all);
					}
				}
			}
		}
		modelMap.put("symptomsUnCheck", symptomsUnCheck);
		modelMap.put("symptomsCheck", symptomsCheck);
		Disease disease = diseaseService.findById(id);
		modelMap.put("disease", disease);
		return new ModelAndView("symptom/diseaseEdit");
	}

	/**
	 * 疾病添加/更新
	 * 
	 * @param disease
	 * @param symptoms
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDisease")
	public Object saveDisease(Disease disease, String symptoms, ModelMap modelMap, HttpServletRequest request) {
		logger.info("疾病:{}, symptoms:{}", JSON.toJSONString(disease), symptoms);
		Map<String, Object> resMap = new HashMap<String, Object>();
		boolean flag = diseaseService.checkDisease(disease);
		if (flag) {
			diseaseService.save(disease, symptoms);
			resMap.put(STATUS, true);
		} else {
			resMap.put(STATUS, false);
			resMap.put(MESSAGE, "疾病名称已经存在");
		}
		return resMap;
	}

	/**
	 * 智能分诊首页 即症状首页
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/triageIndex")
	public ModelAndView triageIndex(ModelMap modelMap, HttpServletRequest request) {
		logger.info("智能分诊首页");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isHide", 1);
		List<Symptom> symptoms = symptomService.findAllByIsHide(params);
		modelMap.put("symptoms", symptoms);
		return new ModelAndView("easyhealth/biz/triage/triageIndex");
	}

	/**
	 * 智能分诊首页 即症状首页
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/triageBuilding")
	public ModelAndView triageBuilding(ModelMap modelMap, HttpServletRequest request) {
		return new ModelAndView("easyhealth/biz/triage/triageBuilding");
	}

	/**
	 * 智能分诊疾病列表
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trigeList")
	public ModelAndView trigeList(String symptoms, ModelMap modelMap, HttpServletRequest request) {
		logger.info("智能分诊疾病列表 symptoms:{}", symptoms);
		List<Disease> diseases = null;
		List<String> symptomIds = new ArrayList<String>();
		if (StringUtils.isNotBlank(symptoms)) {
			String[] item = symptoms.split(",");
			for (String str : item) {
				symptomIds.add(str);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("symptomIds", symptomIds);
			diseases = diseaseService.findDiseaseBySymptomIds(params);
		}
		modelMap.put("diseases", diseases);
		// 刷新
		modelMap.put("symptoms", symptoms);
		return new ModelAndView("easyhealth/biz/triage/triageList");
	}

	/**
	 * 疾病详细
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/triageDetail")
	public ModelAndView triageDetail(String id, String symptoms, ModelMap modelMap, HttpServletRequest request) {
		logger.info("疾病详细 id:{}", id);
		Disease disease = diseaseService.findById(id);
		modelMap.put("disease", disease);
		modelMap.put("symptoms", symptoms);
		return new ModelAndView("easyhealth/biz/triage/triageDetail");
	}
}
