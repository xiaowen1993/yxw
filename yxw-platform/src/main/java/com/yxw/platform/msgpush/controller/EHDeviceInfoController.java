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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.commons.entity.platform.msgpush.EHDeviceInfo;
import com.yxw.platform.msgpush.service.EHDeviceInfoService;

/**
 * 设备信息
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: EHDeviceInfoController
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
@RequestMapping("/msgpush/ehDeviceInfo")
public class EHDeviceInfoController extends BaseController<EHDeviceInfo, String> {
	@Autowired
	private EHDeviceInfoService ehDeviceInfoService;

	@Override
	protected BaseService<EHDeviceInfo, String> getService() {
		return ehDeviceInfoService;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadDeviceInfo")
	public RespBody uploadDeviceInfo(EHDeviceInfo ehDeviceInfo) {
		//		ehDeviceInfoService.modifyDeviceInfo(ehDeviceInfo);
		//		return new RespBody(Status.OK);
		boolean status = ehDeviceInfoService.saveDeviceInfo(ehDeviceInfo);
		if (status) {
			return new RespBody(Status.OK);
		} else {
			return new RespBody(Status.ERROR);
		}

	}

}
