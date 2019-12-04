package com.yxw.statistics.biz.stats.controller;

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
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.statistics.biz.constants.CommonConstant;
import com.yxw.statistics.biz.stats.service.OrderStatsService;
import com.yxw.statistics.biz.vo.StatsVo;

@Controller
@RequestMapping("stats/main")
public class MainStatsController {

	private Logger logger = LoggerFactory.getLogger(MainStatsController.class);
	
	private OrderStatsService statsService = SpringContextHolder.getBean(OrderStatsService.class); 
	
	@RequestMapping("/index")
	public ModelAndView showDetail(HttpServletRequest request, StatsVo vo) {
		ModelAndView view = new ModelAndView("biz/stats/mainStats");
		view.addObject(CommonConstant.COMMON_VO_KEY, vo);
		view.addObject(CommonConstant.COMMON_ENTITY_KEY, getHospitals());
		
		return view;
	}
	
	public List<Hospital> getHospitals() {  
		List<Hospital> hospitals = new ArrayList<>();
		
		Hospital hospital1 = new Hospital();
		hospital1.setName("东莞人民医院");
		hospital1.setCode("dgsrmyy");
		hospitals.add(hospital1);
		
		Hospital hospital2 = new Hospital();
		hospital2.setName("乱来的医院");
		hospital2.setCode("woluannongde");
		hospitals.add(hospital2);
		
		Hospital hospital3 = new Hospital();
		hospital3.setName("乔版主的医院");
		hospital3.setCode("qiaobangzhude");
		hospitals.add(hospital3);
		
		Hospital hospital4 = new Hospital();
		hospital4.setName("东京三楼医院");
		hospital4.setCode("dongjingsanlou");
		hospitals.add(hospital4);
		
		return hospitals;
	}
	
	@ResponseBody
	@RequestMapping("/getKpiInfos")
	public RespBody getKPIInfos(HttpServletRequest request, StatsVo vo) {
		String result = statsService.getKPIInfos(vo);
		return new RespBody(Status.OK, result);
	}
	
	@ResponseBody
	@RequestMapping("/getStatsInfo")
	public RespBody getStatsInfo(HttpServletRequest request, StatsVo vo) {
		String result = statsService.getStatsInfo(vo);
		return new RespBody(Status.OK, result);
	}
}
