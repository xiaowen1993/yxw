/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年4月1日
 * @version 1.0
 */
package com.yxw.cache.core.serve;

import java.util.List;

import com.yxw.cache.core.exception.CacheException;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年4月1日  
 */

public interface ServeComm {

	/**
	 * 
	 * 描述: TODO 目前只是简单定义，后续可以增加验签等等
	 * @author Caiwq
	 * @date 2016年4月1日 
	 * @param request
	 * @return
	 * @throws CacheException
	 */

	public List<Object> get(String serviceName, String methodName, List<Object> parameters);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2016年4月5日 
	 * @param serviceName
	 * @param methodName
	 * @param parameters
	 * @param values
	 * @return
	 */
	public int set(String serviceName, String methodName, List<Object> values);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2016年4月5日 
	 * @param serviceName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	public int delete(String serviceName, String methodName, List<Object> parameters);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2016年4月5日 
	 * @param serviceName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	public Object pop(String serviceName, String methodName, List<Object> parameters);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2016年4月5日 
	 * @param serviceName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	public int count(String serviceName, String methodName, List<Object> parameters);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2016年4月14日 
	 * @param serviceName
	 * @param methodName
	 * @param parameters
	 * @return
	 */
	public Boolean pipeline(String serviceName, String methodName, List<Object> values);
}
