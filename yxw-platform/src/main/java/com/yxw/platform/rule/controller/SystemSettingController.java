/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-28</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.platform.rule.SystemSetting;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.controller.HospitalController;
import com.yxw.platform.rule.service.SystemSettingService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: SwitchRuleConfigController
 * @Statement: <p>
 *             系统配置
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-28
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/systemSetting/")
public class SystemSettingController extends BizBaseController<SystemSetting, String> {

	private Logger logger = LoggerFactory.getLogger(HospitalController.class);

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private SystemSettingService systemSettingService = SpringContextHolder.getBean(SystemSettingService.class);

	@Override
	protected BaseService<SystemSetting, String> getService() {
		return systemSettingService;
	}

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/sys/systemsetting/sysPush");
		List<SystemSetting> list = systemSettingService.findAll();
		if (list != null && list.size() > 0) {
			SystemSetting systemSetting = list.get(0);
			modelAndView.addObject("systemSetting", systemSetting);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public RespBody save(SystemSetting systemSetting) {
		try {
			String id = systemSetting.getId();
			if (StringUtils.isNotBlank(id)) {
				systemSettingService.update(systemSetting);
			} else {
				String systemSettingId = PKGenerator.generateId();
				systemSetting.setId(systemSettingId);
				systemSettingService.add(systemSetting);
			}
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error("保存系统全局配置异常，异常信息:{}", e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
}
