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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.msgpush.service.MsgCustomerService;

/**
 * 客服消息Controller
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: MsgCustomerController
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/msgpush/msgCustomer")
public class MsgCustomerController extends BaseController<MsgCustomer, String> {

	@Autowired
	private MsgCustomerService msgCustomerService;

	@Override
	protected BaseService<MsgCustomer, String> getService() {
		return this.msgCustomerService;
	}

	@RequestMapping(value = "/editCustomer")
	public ModelAndView edit(String id, String hospitalId) {
		MsgCustomer msgCustomer = null;
		if (id != null) {
			msgCustomer = msgCustomerService.findById(id);
		} else {
			msgCustomer = new MsgCustomer();
			msgCustomer.setHospitalId(hospitalId);
		}
		return new ModelAndView("/sys/msgpush/msgCustomerEdit", "entity", msgCustomer);
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/save")
	public RespBody save(MsgCustomer entity) {
		if (StringUtils.isNotBlank(entity.getId())) {
			msgCustomerService.update(entity);
		} else {
			msgCustomerService.add(entity);
		}
		return new RespBody(Status.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delCustomer")
	public RespBody delCustomer(String id) {
		if (StringUtils.isNotBlank(id)) {
			msgCustomerService.deleteById(id);
		}
		return new RespBody(Status.OK);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
}
