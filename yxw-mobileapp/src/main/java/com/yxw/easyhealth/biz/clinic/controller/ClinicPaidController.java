package com.yxw.easyhealth.biz.clinic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;
import com.yxw.utils.DateUtils;

@Controller
@RequestMapping("app/clinic/paid/")
public class ClinicPaidController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(ClinicPaidController.class);

	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@RequestMapping(value = "/index")
	public ModelAndView toPaidView(CommonParamsVo vo, HttpServletRequest request) {
		ModelAndView modelAndView = null;

		List<HospitalInfoByEasyHealthVo> hospitalInfos = null;
		List<Object> params = new ArrayList<Object>();
		params.add(vo.getAppCode());
		params.add(BizConstant.OPTION_CLINIC_PAY);
		params.add(vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.HOSPITAL_AND_OPTION_CACHE, "getHospitalByOption", params);

		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			hospitalInfos = JSON.parseArray(source, HospitalInfoByEasyHealthVo.class);
		}

		for (int i = hospitalInfos.size() - 1; i >= 0; i--) {
			HospitalInfoByEasyHealthVo hospitalInfoByEasyHealthVo = hospitalInfos.get(i);
			params.clear();
			params.add(hospitalInfoByEasyHealthVo.getHospitalCode());
			List<Object> result = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
			if (!CollectionUtils.isEmpty(result)) {
				CodeAndInterfaceVo codeAndInterfaceVo = (CodeAndInterfaceVo) result.get(0);

				if (codeAndInterfaceVo.getStatus() == 0) {
					hospitalInfos.remove(i);
				}
			}
		}

		modelAndView = new ModelAndView("/easyhealth/biz/clinic/paidIndex");
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		modelAndView.addObject("hospitals", hospitalInfos);

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "getDatas", method = RequestMethod.POST)
	public Object getDatas(ClinicQueryVo vo, Integer dateType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 查询已缴费的
			// vo.setPayStatus(OrderConstant.STATE_PAYMENT);
			// vo.setClinicStatus(ClinicConstant.STATE_PAY_SUCCESS);

			// 判断时间段
			// 0: 全部
			// 1: 今天
			// 2: 近3天
			// 3: 近一周
			// 4: 近30天
			long beginTime = 0;
			long endTime = 0;
			Date today = new Date();

			switch (dateType.intValue()) {
			case 1:
				beginTime = DateUtils.getDateBegin(today).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 2:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -2))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 3:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -6))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			case 4:
				beginTime = DateUtils.getDateBegin(DateUtils.StringToDateYMD(DateUtils.getDayForDate(today, -29))).getTime();
				endTime = DateUtils.getDateEnd(today).getTime();
				break;
			default:
				break;
			}

			vo.setBeginTime(beginTime);
			vo.setEndTime(endTime);

			List<ClinicRecord> list = clinicService.findPaidRecords(vo);

			// 已缴费的入库
			/*Thread dataStorageThead = new Thread(new SaveDataPayFeeRunnable(list, vo));
			dataStorageThead.start();*/

			resultMap.put("list", list);
		} catch (Exception e) {
			logger.error("获取已缴费数据异常. errorMsg: {}, cause: {}", new Object[] { e.getMessage(), e.getCause() });
		}
		return new RespBody(Status.OK, resultMap);
	}
}
