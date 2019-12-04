package com.yxw.interfaces.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.service.YxwService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;

public class YxwCommServiceImpl implements YxwCommService {

	private Map<String, Method> methods = new HashMap<String, Method>();

	private Logger logger = LoggerFactory.getLogger(YxwCommServiceImpl.class);

	@Override
	public Response invoke(Request request) {
		logger.info("请求参数：{}", JSONObject.toJSONString(request));

		String serviceId = request.getServiceId();

		Response response = null;

		try {
			YxwService yxwService = SpringContextHolder.getBean(serviceId);

			String methodName = request.getMethodName();
			String key =
					serviceId.concat(".").concat(methodName).concat("(").concat(request.getInnerRequest().getClass().getName()).concat(")");

			Method method = null;
			if (methods.containsKey(key)) {
				method = methods.get(key);
			} else {
				method = ReflectionUtils.findMethod(yxwService.getClass(), methodName, request.getInnerRequest().getClass());

				if (method != null) {
					methods.put(key, method);
				}
			}

			if (method != null) {
				response = (Response) ReflectionUtils.invokeMethod(method, yxwService, request.getInnerRequest());
				logger.info("response: {}", JSONObject.toJSONString(response));
			} else {
				logger.info("方法不存在：（{}）", methodName);
				response = new Response("4", "方法不存在：（" + methodName + "）", null);
			}

		} catch (Exception e) {
			logger.info("接口适配程序异常: {}", e);
			response = new Response("-3", "接口适配程序异常", null);
		}

		return response;
	}

}
