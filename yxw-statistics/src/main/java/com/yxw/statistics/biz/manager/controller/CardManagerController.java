package com.yxw.statistics.biz.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.statistics.biz.constants.CommonConstant;
import com.yxw.statistics.biz.manager.service.CardManagerService;
import com.yxw.statistics.biz.vo.CardManagerVo;
import com.yxw.statistics.biz.vo.CommonVo;

@Controller
@RequestMapping("manager/card")
public class CardManagerController {
	
	private Logger logger = LoggerFactory.getLogger(CardManagerController.class);
	
	private CardManagerService service = SpringContextHolder.getBean(CardManagerService.class);
	
	@RequestMapping("/index")
	public ModelAndView toView(HttpServletRequest request, CommonVo vo) {
		ModelAndView view = new ModelAndView("biz/manager/cardManager");
		view.addObject(CommonConstant.COMMON_VO_KEY, vo);
		view.addObject(CommonConstant.COMMON_ENTITY_KEY, getHospitals());
		view.addObject("branches", getBranches());
		view.addObject("platforms", getPlatforms());
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/getCards")
	public RespBody getOrders(HttpServletRequest request, CardManagerVo vo) {
		String result = service.getCards(vo);
		return new RespBody(Status.OK, result);
	}
	
	private List<Hospital> getHospitals() {  
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
	
	private List<BranchHospital> getBranches() {
		List<BranchHospital> branchHospitals = new ArrayList<>();
		
		BranchHospital branch0 = new BranchHospital();
		branch0.setName("万江院区");
		branch0.setCode("0007");
		branchHospitals.add(branch0);
		
		BranchHospital branch1 = new BranchHospital();
		branch1.setName("红楼院区");
		branch1.setCode("0001");
		branchHospitals.add(branch1);
		
		BranchHospital branch2 = new BranchHospital();
		branch2.setName("第一门诊部");
		branch2.setCode("0003");
		branchHospitals.add(branch2);
		
		BranchHospital branch3 = new BranchHospital();
		branch3.setName("普济院区");
		branch3.setCode("0004");
		branchHospitals.add(branch3);
		
		return branchHospitals;
	}
	
	private List<Platform> getPlatforms() {
		List<Platform> platforms = new ArrayList<>();
		Platform platform1 = new Platform();
		platform1.setCode("1");
		platform1.setName("标准平台微信");
		platforms.add(platform1);
		
		Platform platform2 = new Platform();
		platform2.setCode("2");
		platform2.setName("标准平台支付宝");
		platforms.add(platform2);
		return platforms;
	}
	
}
 