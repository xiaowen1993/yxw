package com.yxw.cache.core.process;

import java.util.List;

public interface CallbackProcessor {

	public Object call(String serviceName, String methodName, List<Object> parameters);
	
}
