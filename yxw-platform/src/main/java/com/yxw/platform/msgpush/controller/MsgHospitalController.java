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
package com.yxw.platform.msgpush.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.vo.MsgPushHospitalVo;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.msgpush.service.MsgTemplateService;

/**
 * 医院列表
 * 
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: HospitalController
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月2日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/msgpush/hospital")
public class MsgHospitalController extends BaseController<Hospital, String> {

	@Autowired
	private MsgTemplateService msgTemplateService;

	@Override
	protected BaseService<Hospital, String> getService() {
		return null;
	}

	@RequestMapping(value = "/hospitalList")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize, ModelMap modelMap, HttpServletRequest request, @RequestParam(
			required = false, defaultValue = "") String search) {

		List<Hospital> hospitalList = (List<Hospital>) request.getSession().getAttribute(UserConstant.HOSPITAL_LIST);
		PageInfo<MsgPushHospitalVo> hospitals = null;
		if (hospitalList.size() > 0) {
			String[] hospitalIds = new String[hospitalList.size()];
			for (int i = 0; i < hospitalList.size(); i++) {
				hospitalIds[i] = hospitalList.get(i).getId();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 设置搜索条件
			params.put("search", search);
			//把session中保存的ID做查询条件
			params.put("hospitalIds", hospitalIds);
			hospitals = msgTemplateService.findHospListByPage(params, new Page<MsgPushHospitalVo>(pageNum, pageSize));
		}
		modelMap.put("search", search);

		return new ModelAndView("/sys/msgpush/hospitalList", "hospitals", hospitals);
	}
}
