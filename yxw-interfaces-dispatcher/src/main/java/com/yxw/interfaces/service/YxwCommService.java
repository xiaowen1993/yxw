package com.yxw.interfaces.service;

import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;

public interface YxwCommService {
	
	public abstract Response invoke(Request request);

}
