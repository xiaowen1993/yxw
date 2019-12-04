/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月13日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * @author 自定义freemarker视图解析器,增加模板全局变量
 * @version 1.0
 * @since 2015年4月13日
 */

public class CustomFreeMarkerView extends FreeMarkerView {

	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		String path = request.getContextPath();
		String basePath =
				request.getScheme() + "://" + request.getServerName() + ( 80 == request.getServerPort() ? "" : ":" + request.getServerPort() ) + path
						+ "/";
		// 设置项目路径为全局变量
		model.put("path", path);
		model.put("basePath", basePath);
		super.exposeHelpers(model, request);
	}

}
