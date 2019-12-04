package com.yxw.statistics.biz.hospitalmanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.statistics.biz.constants.CommonConstant;
import com.yxw.statistics.biz.hospitalmanager.entity.Hospital;
import com.yxw.statistics.biz.hospitalmanager.service.HospitalService;
import com.yxw.statistics.biz.vo.CommonVo;
import com.yxw.statistics.biz.vo.HospitalInfosVo;

@Controller
@RequestMapping(value="hospital/")
public class HospitalInfoContoller {
	
	private HospitalService service = SpringContextHolder.getBean(HospitalService.class);
	
	private Logger logger = LoggerFactory.getLogger(HospitalInfoContoller.class);
	
//	private AreaService areaService = SpringContextHolder.getBean(AreaService.class);

	@RequestMapping(value="index")
	public ModelAndView toIndex(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("biz/hospitalManager/hospitalList");
//		Map<String, ProvinceVo> map = areaService.findAllHiperLevel(CommonConstant.LEVEL_CITY);
//		logger.info(JSON.toJSONString(map.values()));
//		File file = new File("D:\\area.js");
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		try {
//			FileOutputStream fos = new FileOutputStream(file);
//			String str = JSON.toJSONString(map.values());
//			fos.write(str.getBytes("utf-8"));
//			fos.flush();
//			fos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		return view;
	}

	@ResponseBody
	@RequestMapping(value="findHospital", method=RequestMethod.POST)
	public RespBody findHospital(HttpServletRequest request, CommonVo vo) {
		List<HospitalInfosVo> entities = service.findAllHospitalInfos(vo.getHospitalName());
		// 数据转换  hospitalId-[HospitalInfosVo]
		Map<String, List<HospitalInfosVo>> hospitalMap = new HashMap<>();
		for (HospitalInfosVo hospital: entities) {
			String key = hospital.getHospitalId();
			if (hospitalMap.containsKey(hospital.getHospitalId())) {
				hospitalMap.get(key).add(hospital);
			} else {
				List<HospitalInfosVo> list = new ArrayList<>();
				list.add(hospital);
				hospitalMap.put(key, list);
			}
		}
		
		return new RespBody(Status.OK, hospitalMap);
	}
	
	@RequestMapping(value="addHospital")
	public ModelAndView toAddhospital(HttpServletRequest request, HospitalInfosVo vo) {
		ModelAndView view = new ModelAndView("biz/hospitalManager/addHospital");
		view.addObject(CommonConstant.COMMON_VO_KEY, vo);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value="check", method=RequestMethod.POST)
	public RespBody checkHospitalExists(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			String hospitalName = vo.getHospitalName();
			String hospitalCode = vo.getHospitalCode();
			if (StringUtils.isNotBlank(hospitalName)) {
				if (service.checkHospitalExistsByName(hospitalName)) {
					return new RespBody(Status.ERROR, "医院名称重复");
				} else {
					return new RespBody(Status.OK);
				}
			} else {
				if (service.checkHospitalExistsByCode(hospitalCode)) {
					return new RespBody(Status.ERROR, "医院代码重复");
				} else {
					return new RespBody(Status.OK);
				}
				
			}
		} catch (Exception e) {
			logger.error("保存医院信息异常...errorMsg: {}. errorCause: {}.", new Object[]{e.getMessage(), e.getCause()});
			return new RespBody(Status.ERROR, "校验异常");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST)
	public RespBody addHospital(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			String hospitalName = vo.getHospitalName();
			String hospitalCode = vo.getHospitalCode();
			String areaCode = vo.getAreaCode();
			String areaName = vo.getAreaName();
			if (StringUtils.isBlank(hospitalName) || StringUtils.isBlank(hospitalCode) || StringUtils.isBlank(areaCode) || StringUtils.isBlank(areaName)) {
				return new RespBody(Status.ERROR, "有必要数据没有录入");
			} else {
				if (StringUtils.isNotBlank(vo.getHospitalId())) {
					Hospital hospital = vo.convertToHospital();
					service.update(hospital);
					return new RespBody(Status.OK);
				} else {
					if (service.checkHospitalExistsByName(hospitalName)) {
						return new RespBody(Status.ERROR, "医院名称重复");
					}
					
					if (service.checkHospitalExistsByCode(hospitalCode)) {
						return new RespBody(Status.ERROR, "医院代码重复");
					}
					
					Hospital hospital = new Hospital();
					hospital.setName(hospitalName);
					hospital.setCode(hospitalCode);
					hospital.setAreaCode(areaCode);
					hospital.setAreaName(areaName);
					String id = service.add(hospital);
					return new RespBody(Status.OK, id);
				}
			}
		} catch (Exception e) {
			logger.error("保存医院信息异常...errorMsg: {}. errorCause: {}.", new Object[]{e.getMessage(), e.getCause()});
			return new RespBody(Status.ERROR, "保存医院信息异常");
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="deleteHospital", method=RequestMethod.POST)
	public RespBody deleteHospital(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			String hospitalId= vo.getHospitalId();
			if (StringUtils.isBlank(hospitalId)) {
				return new RespBody(Status.ERROR, "请输入ID");
			} else {
				service.deleteById(hospitalId);
				return new RespBody(Status.OK);
			}
		} catch (Exception e) {
			logger.error("删除医院信息异常...errorMsg: {}. errorCause: {}.", new Object[]{e.getMessage(), e.getCause()});
			return new RespBody(Status.ERROR, "删除医院信息异常");
		}
		
	}
	
}
