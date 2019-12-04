/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-30</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke;

/**
 * @Package: com.yxw.mobileapp.invoke
 * @ClassName: InsideInvokeService
 * @Statement: <p>内部组件 业务调用接口</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-30
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface InsideInvokeService {

	/**
	 * 支付回调-所有业务统一调用接口（挂号、门诊、住院），通过订单号规则判断具体是什么业务
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月19日 
	 * @param payAsynResponse
	 */
	public void payMent(Object payAsynResponse);

}
