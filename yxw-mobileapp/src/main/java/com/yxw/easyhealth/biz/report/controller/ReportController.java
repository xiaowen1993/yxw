package com.yxw.easyhealth.biz.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.easyhealth.biz.report.constants.ReportConstant;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.easyhealth.datas.manager.ReportsBizManager;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.inspection.Examine;
import com.yxw.interfaces.vo.inspection.Inspect;
import com.yxw.utils.DateUtils;

@Controller
@RequestMapping("app/report/")
public class ReportController extends BaseAppController {
	private final static String HOSPITAL_LIST_KEY = "hospitals";
	private final static String FAMILY_LIST_KEY = "families";
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	private final static String FORWARD_URL = "app/report/reportIndex";

	private ReportsBizManager reportsBizManager = SpringContextHolder.getBean(ReportsBizManager.class);
	private RuleConfigManager ruleConfigManger = SpringContextHolder.getBean(RuleConfigManager.class);

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

		if (StringUtils.isBlank(vo.getAreaCode())) {
			vo.setAreaCode(getAreaCode(request));
		}

		view = new ModelAndView("/easyhealth/biz/report/index");

		// 加载家人信息
		List<Family> families = familyService.findAllFamilies(vo.getOpenId());
		if (CollectionUtils.isEmpty(families)) {
			// 加本人
			familyService.saveFamilyInfo(this.getEasyHealthUser(request));
			families = familyService.findAllFamilies(vo.getOpenId());
			view.addObject(FAMILY_LIST_KEY, families);
		} else {
			view.addObject(FAMILY_LIST_KEY, families);
		}

		// 加载医院信息
		List<HospitalInfoByEasyHealthVo> hospitals = null;

		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(vo.getAppCode());
		requestGetParameters.add(BizConstant.OPTION_REPORT_QUERY);
		requestGetParameters.add(vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", requestGetParameters);
		if (CollectionUtils.isNotEmpty(results)) {
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

		view.addObject(HOSPITAL_LIST_KEY, hospitals != null ? hospitals : new ArrayList<HospitalInfoByEasyHealthVo>());

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject(BizConstant.URL_PARAM_FORWARD_NAME, FORWARD_URL);
		return view;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getDatas", method = RequestMethod.POST)
	public Object getDatas(ReportsParamsVo vo, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取就诊卡

		// 报告查询类，还是需要走配置 -- 获取最大查询月份
		RuleQuery ruleQuery = ruleConfigManger.getRuleQueryByHospitalCode(vo.getHospitalCode());
		Integer queryMonths = ruleQuery.getReportRecordQueryMaxMonths();
		String endDate = DateUtils.today();
		String beginDate = DateUtils.getFirstDayOfMonth(endDate, queryMonths == null ? -12 : -1 * queryMonths.intValue());

		List<Object> list = new ArrayList<>();

		Map<String, Object> params = new HashMap<>();
		params.putAll(JSON.parseObject(JSON.toJSONString(vo), Map.class));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		// Thread saveDataThread = null;
		switch (vo.getReportType()) {
		case ReportConstant.REPROT_TYPE_EXAMINE:
			// 检查
			// list = reportService.getExamineList(medicalCards, beginDate);
			List<Examine> examinesList = reportsBizManager.getExamineList(params);
			list.addAll(examinesList);
			// 数据入库
			// saveDataThread = new Thread(new SaveDataExamineRunnable(examinesList));
			// saveDataThread.start();
			break;
		case ReportConstant.REPORT_TYPE_INSPECT:
			// 检验
			// list = reportService.getInspectList(medicalCards, beginDate);
			List<Inspect> inspectsList = reportsBizManager.getInspectList(params);
			list.addAll(inspectsList);
			// 数据入库
			// saveDataThread = new Thread(new SaveDataInspectRunnable(inspectsList));
			// saveDataThread.start();
			break;
		case ReportConstant.REPORT_TYPE_CHECKUP:
			// 体检 （预留，目前健康易没有）
			// list = reportService.getCheckupList(medicalCards, beginDate);
			// list = reportsBizManager.getCheckupList(params);
			break;
		default:
			break;
		}

		resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, list);
		return new RespBody(Status.OK, resultMap);
	}
}
