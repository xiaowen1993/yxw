/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-20</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.utils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.common.thread
 * @ClassName: QueryHashTableCallAble
 * @Statement: <p>子线程查询hash子表</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-21
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class QueryHashTableCallable implements Callable<Object> {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(QueryHashTableCallable.class);

	/**
	 * 执行类的执行接口或者实现类
	 */
	private Class<?> springBeanClazz;

	/**
	 * 执行类的执行方法名
	 */
	private String methodName;

	/**
	 * 对应方法的参数
	 */
	private Object[] parameters;

	/**
	 * 对应方法参数的类型
	 */
	private Class<?>[] parameterTypes;

	public QueryHashTableCallable(Class<?> springBeanClazz, String methodName, Object[] parameters, Class<?>[] parameterTypes) {
		super();
		this.springBeanClazz = springBeanClazz;
		this.methodName = methodName;
		this.parameters = parameters;
		this.parameterTypes = parameterTypes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		Object springBean = SpringContextHolder.getBean(springBeanClazz);
		Method method = springBeanClazz.getMethod(methodName, parameterTypes);
		return method.invoke(springBean, parameters);
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Class<?> getSpringBeanClazz() {
		return springBeanClazz;
	}

	public void setSpringBeanClazz(Class<?> springBeanClazz) {
		this.springBeanClazz = springBeanClazz;
	}
}
