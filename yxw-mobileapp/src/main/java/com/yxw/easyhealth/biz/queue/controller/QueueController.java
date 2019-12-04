package com.yxw.easyhealth.biz.queue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.easyhealth.biz.queue.constants.QueueConstants;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.QueuesParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.easyhealth.datas.manager.QueuesBizManager;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.intelligent.waiting.ExamineQueue;
import com.yxw.interfaces.vo.intelligent.waiting.MZQueue;
import com.yxw.interfaces.vo.intelligent.waiting.MedicineQueue;

@Controller
@RequestMapping("app/queue/")
public class QueueController extends BaseAppController {
	private final static String HOSPITAL_LIST_KEY = "hospitals";
	private final static String FAMILY_LIST_KEY = "families";
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	private QueuesBizManager queuesBizManager = SpringContextHolder.getBean(QueuesBizManager.class);
	private final static String FORWARD_URL = "/easyhealth/queue/index";

	@RequestMapping(value = "/index")
	public ModelAndView toPayView(CommonParamsVo vo, HttpServletRequest request) {
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		ModelAndView view = null;
		/*ModelAndView view = super.checkUserInfoPerfect(vo, request, FORWARD_URL);
		if (view != null) {
			return view;
		}

		String checkSessionUserKey = BizConstant.COMMON_SESSION_PARAMS + "_" + vo.getOpenId();
		CommonParamsVo tempVo = (CommonParamsVo) request.getSession().getAttribute(checkSessionUserKey);
		if (tempVo != null) {
			vo = tempVo;
			request.getSession().removeAttribute(checkSessionUserKey);
		}*/

		// 设定areaCode
		/*		if (StringUtils.isBlank(vo.getAreaCode())) {
					vo.setAreaCode(getAreaCode(request));
				}*/

		view = new ModelAndView("/easyhealth/biz/queue/queueIndex");
		// 加载家人信息
		List<Family> families = familyService.findAllFamilies(vo.getOpenId());
		view.addObject(FAMILY_LIST_KEY, families);

		// 加载医院信息
		List<HospitalInfoByEasyHealthVo> hospitals = null;
		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(vo.getAppCode());
		requestGetParameters.add(BizConstant.OPTION_ONLINE_FILING);
		requestGetParameters.add(vo.getAreaCode());
		//				List<HospitalInfoByEasyHealthVo> hospitals = hospitalAndOptionCache.getHospitalByOption(BizConstant.OPTION_SMART_WAIT, vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", requestGetParameters);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitals = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		List<Object> params = new ArrayList<Object>();
		for (int i = hospitals.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitals.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(result)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					hospitals.remove(i);
				}
			}
		}

		view.addObject(HOSPITAL_LIST_KEY, hospitals);

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject(BizConstant.URL_PARAM_FORWARD_NAME, FORWARD_URL);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "getQueueList", method = RequestMethod.POST)
	public Object getQueueList(QueuesParamsVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Object> list = new ArrayList<>();
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("hospitalCode", vo.getHospitalCode());
		reqMap.put("branchHospitalCode", vo.getBranchHospitalCode());
		reqMap.put("patCardNo", vo.getPatCardNo());
		reqMap.put("patCardType", vo.getPatCardType());

		switch (vo.getQueueType()) {
		case QueueConstants.QUEUE_TYPE_CHECK:
			List<ExamineQueue> examineQueues = queuesBizManager.getExamineQueue(reqMap);
			if (!CollectionUtils.isEmpty(examineQueues)) {
				list.addAll(examineQueues);
			}
			break;
		case QueueConstants.QUEUE_TYPE_MZ_WAIT:
			List<MZQueue> mzQueues = queuesBizManager.getMzQueue(reqMap);
			if (!CollectionUtils.isEmpty(mzQueues)) {
				list.addAll(mzQueues);
			}
			break;
		case QueueConstants.QUEUE_TYPE_MEDICINE:
			List<MedicineQueue> medicineQueues = queuesBizManager.getMedicineQueue(reqMap);
			if (!CollectionUtils.isEmpty(medicineQueues)) {
				list.addAll(medicineQueues);
			}
			break;
		case QueueConstants.QUEUE_TYPE_REPORT:
			// 报告出具，暂时没做。
			break;
		default:
			break;
		}

		resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, list);
		return new RespBody(Status.OK, resultMap);
	}
}
