/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * 后台接入平台管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/platform")
public class PlatformController extends BaseController<Platform, String> {

	@Autowired
	private PlatformService platformService;

	@Override
	protected BaseService<Platform, String> getService() {
		return platformService;
	}
}
