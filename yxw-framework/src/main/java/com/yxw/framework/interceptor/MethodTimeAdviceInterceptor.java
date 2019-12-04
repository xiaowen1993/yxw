/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年2月27日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author 谢家贵
 * @version 1.0
 */

public class MethodTimeAdviceInterceptor implements MethodBeforeAdvice, AfterReturningAdvice {
	private static final Logger logger = LoggerFactory.getLogger(MethodTimeAdviceInterceptor.class);
	long tt = 0;

	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		tt = System.currentTimeMillis();
	}

	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		long lastTime = System.currentTimeMillis() - tt;
		StringBuffer buf =
				new StringBuffer("EXECUTE:(").append(arg1.getName()).append(") BEGIN=").append(tt).append(", END=").append(System.currentTimeMillis())
						.append(", ELAPSE=").append(lastTime);
		if (logger.isDebugEnabled()) {
			logger.debug(buf.toString());
		}
	}
}
