package com.yxw.insurance.interfaces.service;

import com.yxw.insurance.interfaces.dto.Request;
import com.yxw.insurance.interfaces.dto.Response;

public interface InsuranceLocalDataService {

	/**
	 * 通用查询本地商保数据接口
	 * @param request	
	 * @return
	 */
	public Response localService(Request request);

}
