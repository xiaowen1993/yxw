package com.yxw.framework.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.JstlView;

public class CustomJspView extends JstlView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ (80 == request.getServerPort() ? "" : ":" + request.getServerPort()) + path + "/";
		// 设置项目路径为全局变量
		model.put("path", path);
		model.put("basePath", basePath);
		super.renderMergedOutputModel(model, request, response);
	}
}
