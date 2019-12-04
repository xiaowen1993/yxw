package com.yxw.solr.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.yxw.framework.config.SystemConfig;

public class InitDataServlet extends HttpServlet {

	private static final long serialVersionUID = -6344012358605200647L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SystemConfig.loadSystemConfig();
	}
	
}
