/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.msgcenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.mobileapp.biz.msgcenter.service.MsgCenterService;

/**
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: MsgCenterController
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/app/msgcenter")
public class MsgCenterController extends BaseController<MsgCenter, String> {
	@Autowired
	private MsgCenterService msgCenterService;

	@Override
	protected BaseService<MsgCenter, String> getService() {
		return msgCenterService;
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public PageInfo<MsgCenter> list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		PageInfo<MsgCenter> msgCenters = msgCenterService.findListByPage(params, new Page<MsgCenter>(pageNum, pageSize));
		return msgCenters;
	}

	@RequestMapping(value = "/msgCenterListView")
	public ModelAndView msgCenterListView(HttpServletRequest request, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String menuCode = request.getParameter("menuCode");
		if (StringUtils.isBlank(menuCode)) {
			menuCode = "3";
		}
		params.put("menuCode", menuCode);
		
		params.put("enableWebSocket", SystemConfig.getInteger("enableWebSocket", 0));
		params.put("commonWebSocketPath", SystemConfig.getStringValue("commonWebSocketPath", ""));
		params.put("commonWebSocketPath", SystemConfig.getStringValue("commonWebSocketPath", ""));
		
		return new ModelAndView("/easyhealth/biz/msgcenter/msgCenterList", params);
	}

	@RequestMapping(value = "/detailView")
	public ModelAndView detailView(String userId, String id) {
		Map<String, Object> params = new HashMap<String, Object>();

		MsgCenter msgCenter = msgCenterService.findById(userId, id);
		MsgTemplate msgTemplate = JSON.parseObject(msgCenter.getMsgContent(), MsgTemplate.class);
		if (msgTemplate.getIconPath() != null) {
			msgTemplate.setIconPath(msgTemplate.getIconPath().replaceAll("\\\\", "/"));
		}
		if (msgTemplate.getAnimationPath() != null) {
			msgTemplate.setAnimationPath(msgTemplate.getAnimationPath().replaceAll("\\\\", "/"));
		}

		params.put("userId", userId);
		params.put("msgTemplate", msgTemplate);
		params.put("msgCenter", msgCenter);
		if (msgCenter.getIsRead() == 1) {
			msgCenterService.updateIsRead(userId, id, 2);
		}
		return new ModelAndView("/easyhealth/biz/msgcenter/detailView", params);
	}
}
