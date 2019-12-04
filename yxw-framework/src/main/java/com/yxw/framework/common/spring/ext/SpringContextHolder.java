package com.yxw.framework.common.spring.ext;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Package: com.yxw.framework.common.spring.ext
 * @ClassName: SpringContextHolder
 * @Statement: <p>
 *             提供 Spring的扩展工具类 可在任何层调用getBean得到Spring bean
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SpringContextHolder implements ApplicationContextAware {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SpringContextHolder.class);

	/**
	 * <p>
	 * 定义一个引用org.springframework.context.ApplicationContext的引用
	 * </p>
	 */
	private static ApplicationContext applicationContext = null;

	/*
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中. (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
	 * ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.info("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
		//XmlWebApplicationContext con = (XmlWebApplicationContext) applicationContext;
		//ConfigurableListableBeanFactory fc = con.getBeanFactory();
		//System.out.println(fc.toString());
	}

	public static void setApplicationContextTest(ApplicationContext applicationContext) {
		logger.info("注入ApplicationContext到SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR

	}

	/**
	 * 实现DisposableBean接口,在Context关闭时清理静态变量.
	 * 
	 * @throws Exception
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * <p>
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * </p>
	 * 
	 * @param name
	 *            springbean 的id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * <p>
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * </p>
	 * 
	 * @param requiredType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		Map<String, ?> beans = applicationContext.getBeansOfType(requiredType);

		T bean = null;
		if (beans != null) {
			bean = (T) beans.get(beans.keySet().iterator().next());
		}
		return bean;
	}

	/**
	 * <p>
	 * 从静态变量applicationContext中取得clazz类型bean的Map集合
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 */
	public static Map<String, ?> getBeansOfType(Class<?> clazz) {
		assertContextInjected();
		Map<String, ?> beans = applicationContext.getBeansOfType(clazz);
		return beans;
	}

	/**
	 * <p>
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 * </p>
	 */
	public static void clear() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * <p>
	 * 检查ApplicationContext不为空.为空抛出异常:IllegalStateException
	 * </p>
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在spring.xml中定义SpringContextHolder");
		}
	}

}
