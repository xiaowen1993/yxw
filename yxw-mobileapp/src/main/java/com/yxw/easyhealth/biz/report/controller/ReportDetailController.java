package com.yxw.easyhealth.biz.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.easyhealth.datas.manager.ReportsBizManager;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.inspection.ExamineDetail;
import com.yxw.interfaces.vo.inspection.InspectDetail;

@Controller
@RequestMapping("app/report/detail")
public class ReportDetailController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(ReportDetailController.class);

	private ReportsBizManager reportsBizManager = SpringContextHolder.getBean(ReportsBizManager.class);
	private RuleConfigManager ruleConfigManger = SpringContextHolder.getBean(RuleConfigManager.class);

	@RequestMapping(value = "index")
	public ModelAndView toView(ReportsParamsVo vo) {
		ModelAndView modelAndView = null;
		try {
			//logger.info("进入查看详情...");
			switch (vo.getReportType()) {
			case 1:
				// logger.info("检验报告列表控制");
				RuleQuery ruleQuery = ruleConfigManger.getRuleQueryByHospitalCode(vo.getHospitalCode());
				modelAndView = new ModelAndView("/easyhealth/biz/report/inspectionDetail");
				modelAndView.addObject(BizConstant.RULE_CONFIG_PARAMS_KEY, ruleQuery);
				break;
			case 2:
				// logger.info("检查报告列表控制");
				modelAndView = new ModelAndView("/easyhealth/biz/report/examineDetail");
				break;
			case 3:
				// logger.info("体检报告列表控制");
				modelAndView = new ModelAndView("/easyhealth/biz/report/checkupDetail");
				break;
			default:

				break;
			}
			modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;
	}

	@RequestMapping("/reportGraph")
	public ModelAndView img(String fileAddress) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/report/reportGraph");
		modelAndView.addObject("fileAddress", fileAddress);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "getInspectDetail", method = RequestMethod.POST)
	public Object getInspectDetail(ReportsParamsVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospitalCode", vo.getHospitalCode());
			params.put("branchHospitalCode", vo.getBranchHospitalCode());
			params.put("inspectId", vo.getDetailId());
			List<InspectDetail> details = reportsBizManager.getInspectDetail(params);
			resultMap.put("detail", details);
			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取检验报告详情失败");
			return new RespBody(Status.ERROR, "获取检验报告详情失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "getExamineDetail", method = RequestMethod.POST)
	public Object getExamineDetail(ReportsParamsVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospitalCode", vo.getHospitalCode());
			params.put("branchHospitalCode", vo.getBranchHospitalCode());
			params.put("checkId", vo.getDetailId());
			params.put("checkType", vo.getCheckType());
			ExamineDetail details = reportsBizManager.getExamineDetail(params);
			resultMap.put("detail", details);
			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取检验报告详情失败");
			return new RespBody(Status.ERROR, "获取检验报告详情失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "getCheckupDetail", method = RequestMethod.POST)
	public Object getCheckupDetail(ReportsParamsVo vo) {
		try {
			// Map<String, Object> params = new HashMap<String, Object>();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取体检报告详情失败");
		}
		return null;
	}
}
