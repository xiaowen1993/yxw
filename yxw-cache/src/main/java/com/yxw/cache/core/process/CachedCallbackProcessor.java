package com.yxw.cache.core.process;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yxw.cache.core.exception.CacheException;

public class CachedCallbackProcessor implements CallbackProcessor {
	private static final Logger logger = LoggerFactory.getLogger(CachedCallbackProcessor.class);
	/**
	 * Map<String, Method>
	 * {@link String} serviceName.methodName.parameterTypes...
	 * {@link Method}
	 */
	private static volatile Map<String, Method> methodCache = new ConcurrentHashMap<String, Method>();

	private static volatile Map<String, Object> serviceBeanMap = new ConcurrentHashMap<String, Object>();

	private CachedCallbackProcessor() {
	}

	public static void setServiceBeanMap(Map<String, Object> serviceBeanMap) {
		CachedCallbackProcessor.serviceBeanMap.putAll(serviceBeanMap);
	}

	public static void registerServiceBean(String serviceName, Object serviceBean) {
		serviceBeanMap.put(serviceName, serviceBean);
	}

	public static void unregisterServiceBean(String serviceName) {
		serviceBeanMap.remove(serviceName);
	}

	/** 
	 * @author Caiwq
	 * @date 2016年4月5日  
	 */
	@Override
	public Object call(String serviceName, String methodName, List<Object> parameters) {
		logger.info("request method [named '{}'] of service [named '{}'].", methodName, serviceName);

		Object result = null;

		Object serviceBean = serviceBeanMap.get(serviceName);
		if (serviceBean == null) {
			throw new CacheException(String.format("request service [named '%s'] not exist", serviceName));
		}

		Class<?> serviceBeanClz = serviceBean.getClass();

		Class<?>[] parameterTypes = new Class[parameters.size()];
		List<String> parameterTypeNames = new ArrayList<String>(parameters.size());
		for (int i = 0; i < parameters.size(); i++) {
			Class<?> clz = parameters.get(i).getClass();
			if (Map.class.isAssignableFrom(clz)) {
				clz = Map.class;
			} else if (List.class.isAssignableFrom(clz)) {
				clz = List.class;
			} else if (Set.class.isAssignableFrom(clz)) {
				clz = Set.class;
			} else if (Collection.class.isAssignableFrom(clz)) {
				clz = Collection.class;
			} else if (String[].class.isAssignableFrom(clz)) {
				clz = String[].class;
			}

			parameterTypes[i] = clz;
			parameterTypeNames.add(clz.getName());
		}

		String parameterTypeNameStr = parameterTypeNames.toString();
		if (parameterTypeNameStr.length() > 0) {
			parameterTypeNameStr = parameterTypeNameStr.substring(1, parameterTypeNameStr.length() - 1);
		}
		String declaredMethodName = serviceBeanClz.getName().concat(".").concat(methodName).concat("(").concat(parameterTypeNameStr).concat(")");
		Method method = methodCache.get(declaredMethodName);
		if (method == null) {
			method = BeanUtils.findDeclaredMethod(serviceBeanClz, methodName, parameterTypes);
			if (method == null) {
				throw new CacheException(String.format("request method [named '%s'] of service [named '%s'] not exist", methodName, serviceName));
			}

			methodCache.put(declaredMethodName, method);
		}

		try {
			result = method.invoke(serviceBean, parameters.toArray());
		} catch (Exception e) {
			logger.debug(String.format("%s invoke error", declaredMethodName), e);

			throw new CacheException(String.format("request method [named '%s'] of service [named '%s'] occured exception", methodName, serviceName));
		}

		return result;
	}
}
