/**
 * Copyright© 2014-2018 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2018年6月5日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.terminal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.service.BaseService;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2018年6月5日  
 */

@Controller
@RequestMapping("/app/terminal")
public class TerminalController extends BaseController<MsgCenter, String> {

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2018年6月5日 
	 * @return 
	 */
	@Override
	protected BaseService<MsgCenter, String> getService() {
		// TODO Auto-generated method stub
		return null;
	}

}
