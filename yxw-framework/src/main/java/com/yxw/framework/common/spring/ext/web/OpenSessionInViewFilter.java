package com.yxw.framework.common.spring.ext.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/** 
 * @Project:  Kinth-Base
 * @packpage: com.kinth.base.spring.ext.web                       
 * @className: OpenSessionInViewFilter
 * @Statement: 
 * <p>
 *   缩窄OpenSessionInViewFilter的过滤范围,请求css、js、图片等静态内容时不创建Session连接.
 *   在web.xml中可配置excludeSuffixs参数,多个后缀名以','分割.
 * </p>                                   
 * @JDK version used:  JDK 1.6                    
 * @Author： Yuce
 * @E-mail: yu.ce@foxmail.com              
 * @Create Date：2015-3-26
 * @Modified By：                                         
 * @Modified Date:                                     
 * @Why&What is modified:  <p></p> 
 * @Version: 1.0             
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {

	private static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";
	private static final String[] DEFAULT_EXCLUDE_SUFFIXS = { ".js", ".css", ".jpg", ".gif" };
	private static final String[] DEFAULT_INCLUDE_SUFFIXS = { ".action", ".do" };

	private static String[] excludeSuffixs = null;

	/**
	 * 重载过滤控制函数,忽略特定后缀名的请求.
	 */
	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();

		//对必须INCLUDE的URL直接返回false，省略后面对EXCLUDE后缀名的判断.
		for (String suffix : DEFAULT_INCLUDE_SUFFIXS) {
			if (path.endsWith(suffix))
				return false;
		}

		for (String suffix : excludeSuffixs) {
			if (path.endsWith(suffix))
				return true;
		}

		return false;
	}

	/**
	 * 初始化excludeSuffixs参数.
	 */
	@Override
	protected void initFilterBean() throws ServletException {

		String excludeSuffixStr = getFilterConfig().getInitParameter(EXCLUDE_SUFFIXS_NAME);

		if (StringUtils.isNotBlank(excludeSuffixStr)) {
			excludeSuffixs = excludeSuffixStr.split(",");
			//处理匹配字符串为".后缀名"
			for (int i = 0; i < excludeSuffixs.length; i++) {
				excludeSuffixs[i] = "." + excludeSuffixs[i];
			}
		} else {
			excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
		}
	}

}
