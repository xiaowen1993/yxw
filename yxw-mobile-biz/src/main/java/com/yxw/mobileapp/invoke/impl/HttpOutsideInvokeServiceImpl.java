package com.yxw.mobileapp.invoke.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.mobileapp.invoke.OutsideInvokeService;
import com.yxw.mobileapp.invoke.dto.Request;
import com.yxw.mobileapp.invoke.dto.Response;

public class HttpOutsideInvokeServiceImpl implements OutsideInvokeService {
	public static Logger logger = LoggerFactory.getLogger(HttpOutsideInvokeServiceImpl.class);

	@Override
	public Response openService(Request request) {
		if (StringUtils.isBlank(request.getMethodCode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "methodCode cannot be null");
		}

		if (StringUtils.isBlank(request.getParams())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "params cannot be null");
		}

		boolean responseType = true;
		if (StringUtils.isNotBlank(request.getResponseType()) && request.getResponseType().equals("1")) {
			responseType = false;
		}

		logger.info("methodCode: {} ,responseType:{} , params(): {}.", new Object[] { request.getMethodCode(),
				responseType ? "xml" : "json",
				request.getParams() });
		Response response = new Response();
		response = new Response(OutsideConstant.OUTSIDE_ERROR, "test response");
		return response;
	}

}
