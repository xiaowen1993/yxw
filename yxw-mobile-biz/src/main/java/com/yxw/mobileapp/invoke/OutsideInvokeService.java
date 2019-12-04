/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke;

import com.yxw.mobileapp.invoke.dto.Request;
import com.yxw.mobileapp.invoke.dto.Response;

/**
 * @Package: com.yxw.mobileapp.invoke
 * @ClassName: OutsideInvokeService
 * @Statement: <p>对外提供的接口服务声明</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface OutsideInvokeService {

	/**
	 * 通用接口
	 * @param request	
	 * @return
	 */
	public Response openService(Request request);
}
