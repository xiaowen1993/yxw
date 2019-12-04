package com.yxw.hospitalmanager.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.hospitalmanager.entity.Platform;
import com.yxw.hospitalmanager.entity.PlatformSetting;
import com.yxw.hospitalmanager.entity.TradeMode;
import com.yxw.hospitalmanager.service.PlatformService;
import com.yxw.hospitalmanager.service.PlatformSettingService;
import com.yxw.hospitalmanager.service.TradeModeService;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

@Controller
@RequestMapping(value="sys/hospital/setting/")
public class SettingController {
	
	private Logger logger = LoggerFactory.getLogger(SettingController.class);
	
	private PlatformSettingService service = SpringContextHolder.getBean(PlatformSettingService.class);
	
	private PlatformService platformService = SpringContextHolder.getBean(PlatformService.class);
	
	private TradeModeService tradeModeService = SpringContextHolder.getBean(TradeModeService.class);
	
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request, HospitalInfosVo vo) {
		ModelAndView view = new ModelAndView("hospital/setting");
		view.addObject("commonParams", vo);
		
		// 初始化数据
		List<Platform> platforms = platformService.findAll();
		List<TradeMode> tradeModes = tradeModeService.findAll();
		view.addObject("platforms", platforms);
		try {
			view.addObject("tradeModes", URLEncoder.encode(JSON.toJSONString(tradeModes), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value="getSettings", method = RequestMethod.POST)
	public RespBody getSettings(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			List<HospitalInfosVo> entitis = service.findAllSettings(vo.getHospitalId());
			return new RespBody(Status.OK, entitis);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="deleteSetting", method = RequestMethod.POST)
	public RespBody deleteSetting(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			service.deleteById(vo.getId());
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="addSetting", method = RequestMethod.POST)
	public RespBody addSetting(HttpServletRequest request, PlatformSetting entity) {
		try {
			// 检测 ：在不同医院是否存在同样的appId
			if (service.checkExistsByHospitalIdAndAppId(entity.getHospitalId(), entity.getAppId())) {
				return new RespBody(Status.ERROR, "保存失败！不同医院的平台appId重复.");
			} 
			
			// 检测 ：同一个医院不同平台请使用不同的appId
			if (service.checkExistsByHospitalIdAndAppIdAndPlatformId(entity.getHospitalId(), entity.getAppId(), entity.getPlatformId())) {
				return new RespBody(Status.ERROR, "保存失败！同一个医院的不同平台的AppId重复.");
			}
			
			// 检测 ：同一个医院同一个平台支付方式不能重复
			if (service.checkExistsByHospitalIdAndPlatformIdAndTradeId(entity.getHospitalId(), entity.getPlatformId(), entity.getTradeId())) {
				return new RespBody(Status.ERROR, "保存失败！同一个医院的同一平台的支付方式重复.");
			}
			
			service.add(entity);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR, "保存失败！");
		}
	}
	
}
