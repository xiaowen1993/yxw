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

import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.msgpush.service.MsgTemplateContentService;

/**
 * 模板消息内容controller
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: MsgTemplateContentController
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
@RequestMapping("/msgpush/msgTemplateContent")
public class MsgTemplateContentController extends BaseController<MsgTemplateContent, String> {
	@Autowired
	private MsgTemplateContentService msgTemplateContentService;

	@Override
	protected BaseService<MsgTemplateContent, String> getService() {
		return this.msgTemplateContentService;
	}

}
