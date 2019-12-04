package com.yxw.statistics.biz.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.statistics.biz.constants.CommonConstant;
import com.yxw.statistics.biz.vo.CommonVo;

@Controller
@RequestMapping("sys/hospital/")
public class HospitalController {

	private Logger logger = LoggerFactory.getLogger(HospitalController.class);

	@RequestMapping(value = "list")
	public ModelAndView hospitalList(HttpServletRequest request, CommonVo vo) {
		if (!CommonConstant.bizTypes.containsKey(vo.getBizType())) {
			logger.error("非法的业务类型. bizType: {}.", vo.getBizType());
		}
		ModelAndView view = new ModelAndView("/biz/homepage/hospitalList");
		view.addObject(CommonConstant.COMMON_VO_KEY, vo);
		view.addObject(CommonConstant.COMMON_ENTITY_KEY, getHospitals());
		return view;
	}

	private List<Hospital> getHospitals() {
		List<Hospital> hospitals = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			Hospital hospital = new Hospital();
			hospital.setName("医院" + i);
			hospital.setCode("code" + i);
			hospitals.add(hospital);
		}

		Hospital hospital = hospitals.get(0);
		if (hospital != null) {
			hospital.setName("东莞人民医院");
			hospital.setCode("dgsrmyy");
		}
		return hospitals;
	}
	
	@ResponseBody
	@RequestMapping(value = "findHospital")
	public RespBody findHospital(HttpServletRequest request, CommonVo vo) {
		return new RespBody(Status.OK, null);
	}
}
